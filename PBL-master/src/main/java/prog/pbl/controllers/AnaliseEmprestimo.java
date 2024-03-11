package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.util.Data;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;

public class AnaliseEmprestimo implements Initializable{
    private Stage stage;
    private Emprestimo emprestimo;

    @FXML
    private TextField IDdoemprestimo;

    @FXML
    private Label labelDataDevolucao;
    @FXML
    private Label labelLivro;
    @FXML
    private Label labelLeitor;

    @FXML
    private TextField Renovacoes;

    @FXML
    private TextField dataDevolucao;

    @FXML
    private TextField dataEmprestimo;

    @FXML
    private TextField foiDevolvido;

    @FXML
    private TextField leitorenvolvido;

    @FXML
    private TextField livroEmprestado;

    @FXML
    private TextField prazoFinal;

    @FXML
    private Button voltarButton;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    public static AnaliseEmprestimo analiseEmprestimo;

    @FXML
    void voltarButtonAction(ActionEvent event) {
        switch (Sistema.currentLogMember()){
            case 0 -> admTela01.refreshScreen();
            case 1 -> bibliotecarioTela01.refreshScreen();
            case 2 -> leitorTela01.refreshScreen();
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        analiseEmprestimo = this;
        this.setStage(stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        analiseEmprestimo = this;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        String status = "Sim";

        switch (Sistema.currentLogMember()) {
            case 0 -> {
                deleteBtn.setDisable(false);
                editBtn.setOnAction(event -> {
                    this.editFunction();
                });

            }
            case 1 -> {
                deleteBtn.setVisible(false);
                deleteBtn.setDisable(true);
                editBtn.setText("Devolver");
                editBtn.setOnAction(event -> {
                    this.devolverFunction();
                });
            }
            case 2 -> {
                deleteBtn.setVisible(false);
                deleteBtn.setDisable(true);
                editBtn.setText("Renovar");
                editBtn.setOnAction(event -> {
                    this.renovarFunction();
                });
            }

        }

        if (!emprestimo.isDevolvido()) {
            dataDevolucao.setVisible(false);
            labelDataDevolucao.setVisible(false);
            status = "Não";

        }
        this.emprestimo = emprestimo;
        leitorenvolvido.setText(emprestimo.getLeitor().getNome());
        livroEmprestado.setText(emprestimo.getLivro().getNome());
        dataEmprestimo.setText(emprestimo.getDataEmprestimo().toString().substring(new Data().toString().indexOf(":") + 1));
        prazoFinal.setText(emprestimo.getPrazoFinal().toString().substring(new Data().toString().indexOf(":") + 1));
        foiDevolvido.setText(status);
        IDdoemprestimo.setText(emprestimo.getId());
        Renovacoes.setText(emprestimo.getRenovacoes().toString());
        dataDevolucao.setText(emprestimo.getDataDevolucao().toString().substring(new Data().toString().indexOf(":") + 1));
        deleteBtn.setOnAction(actionEvent -> {
            this.deleteFunction();
        });

    }

    @FXML
    private void devolverFunction() {
        Emprestimo emprestimo1 = this.emprestimo;
        Alert alert;
        try{
            emprestimo1.setDevolvido(true);
            MasterDao.getEmprestimoDao().Update(emprestimo1, emprestimo);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Livro devolvido com sucesso");
            alert.setTitle("Sucesso!");
            alert.show();
            bibliotecarioTela01.refreshScreen();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Deu ruim meu chapa");
            alert.show();
        }
    }

    @FXML
    private void deleteFunction() {

        Alert alert;

        try{
            MasterDao.getEmprestimoDao().delete(MasterDao.getEmprestimoDao().findById(emprestimo.getId()));
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Emprestimo Deletado");
            alert.setTitle("Sucesso!");
            alert.show();
            admTela01.refreshScreen();
        } catch (Exception e) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Deu ruim meu chapa");
            alert.show();
        }
    }

    @FXML
    private void renovarFunction() {
        Alert alert;
        Emprestimo emprestimo1 = this.emprestimo;
        try {
            emprestimo1.renovacaoEmprestimo(emprestimo.getLivro().getIsbn(), emprestimo.getLeitor().getId());
            MasterDao.getEmprestimoDao().Update(emprestimo1, emprestimo);
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Renovação realizada com sucesso");
            alert.setTitle("Concluido");
            alert.show();
            leitorTela01.refreshScreen();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Erro");
            alert.show();
        }
    }

    @FXML
    private void editFunction() {

        leitorenvolvido.setEditable(true);
        livroEmprestado.setEditable(true);
        foiDevolvido.setEditable(true);
        editBtn.setText("Save");
        labelLeitor.setText("Id do Leitor");
        labelLivro.setText("Id do Livro");

        editBtn.setOnAction(event -> {
            saveFunction();
        });

    }

    @FXML
    private void saveFunction() {
        Alert alert;
        Emprestimo emprestimo1 = this.emprestimo;


        String idLeitor = leitorenvolvido.getText();
        String idLivro  = livroEmprestado.getText();
        String devolvido = foiDevolvido.getText();



        switch (devolvido.toLowerCase()) {
            case "sim" -> {
                try {
                    emprestimo1.setDevolvido(true);
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    this.editFunction();
                }
            }
            case "não" -> {
                try {
                    emprestimo1.setDevolvido(false);
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    this.editFunction();
                }
            }
        }

        try{
            if (!Objects.equals(idLivro, emprestimo1.getLivro().getNome())){

                emprestimo1.setLivro(MasterDao.getLivroDao().findById(idLivro));
            } else {
                idLivro = emprestimo1.getLivro().getIsbn();
            }
            if (!Objects.equals(idLeitor, emprestimo1.getLeitor().getNome())) {

                emprestimo1.setLeitor(MasterDao.getLeitorDao().findById(idLeitor));
            } else {
                idLeitor = emprestimo1.getLeitor().getId();
            }

            emprestimo1.setId(MasterDao.getLivroDao().findById(idLivro).getIsbn() + emprestimo1.getDataEmprestimo().getDia()  + MasterDao.getLeitorDao().findById(idLeitor).getId());

            MasterDao.getEmprestimoDao().Update(emprestimo1,this.emprestimo);

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Concluido");
            alert.setContentText("Emprestimo atualizado com sucesso");
            editBtn.setText("Edit");
            admTela01.callToShowInRight("/prog/pbl/AnaliseEmprestimo.fxml");
            AnaliseEmprestimo.analiseEmprestimo.setEmprestimo(emprestimo1);
            alert.show();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            this.editFunction();
        }
    }


}
