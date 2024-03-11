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
import prog.pbl.model.estoque.Livro;
import prog.pbl.util.Data;

import java.net.URL;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;

public class LoanCreateController implements Initializable{
    private Stage stage;
    private Livro livro;

    @FXML
    private TextField IDdoemprestimo;

    @FXML
    private Label labelLivro;
    @FXML
    private Label labelLeitor;

    @FXML
    private TextField Renovacoes;


    @FXML
    private TextField dataEmprestimo;


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

    public static LoanCreateController loanCreateController;

    @FXML
    void voltarButtonAction(ActionEvent event) {
        switch (Sistema.currentLogMember()){
            case 0 -> admHomeController.refreshScreen();
            case 1 -> libarianHomeController.refreshScreen();
            case 2 -> readerHomeController.refreshScreen();
        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loanCreateController = this;
        this.setStage(stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        loanCreateController = this;
    }

    public void setEmprestimo(Livro livro) {
        Data dataHoje = new Data();
        Data prazo = dataHoje;
        prazo.setDia(dataHoje.getDia()+7);
        this.livro = livro;
        livroEmprestado.setText(livro.getIsbn());

        dataEmprestimo.setText(dataHoje.toString());
        prazoFinal.setText(prazo.toString());
        Renovacoes.setText("2");
        editBtn.setOnAction(actionEvent ->  saveFunction());

    }




    @FXML
    private void saveFunction() {
        Alert alert;

        String idLeitor = leitorenvolvido.getText();
        String idLivro = livroEmprestado.getText();




        try{
            Emprestimo emprestimo = new Emprestimo(MasterDao.getLeitorDao().findById(idLeitor), MasterDao.getLivroDao().findById(idLivro));

            MasterDao.getEmprestimoDao().save(emprestimo);

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Concluido");
            alert.setContentText("Emprestimo criado com sucesso");
            libarianHomeController.callToShowInRight("/prog/pbl/InfoLoanPage.fxml");
            InfoLoanController.infoLoanController.setEmprestimo(emprestimo);
            alert.show();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


}
