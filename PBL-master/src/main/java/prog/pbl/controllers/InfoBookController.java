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
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.estoque.Livro;

import java.net.URL;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.LoanCreateController.loanCreateController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;
import static prog.pbl.controllers.MainWindow.mainWindow;

public class InfoBookController implements Initializable{
    private Stage stage;
    private Livro livro;
    @FXML
    private TextField editoraText;

    @FXML
    private Label labelDataDevolucao;
    @FXML
    private Label labelLivro;
    @FXML
    private Label labelLeitor;

    @FXML
    private TextField nomeText;

    @FXML
    private TextField autorText;

    @FXML
    private TextField anoText;

    @FXML
    private TextField qntText;

    @FXML
    private TextField categoriaText;

    @FXML
    private TextField idText;

    @FXML
    private TextField localizacaoText;
    @FXML
    private TextField qntBuscasText;

    @FXML
    private Button voltarButton;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;

    public static InfoBookController infoBookController;

    @FXML
    void voltarButtonAction(ActionEvent event) {
        switch (Sistema.currentLogMember()){
            case 0 -> admHomeController.refreshScreen();
            case 1 -> libarianHomeController.refreshScreen();
            case 2 -> readerHomeController.refreshScreen();
            case 3 -> guestHomeController.refreshScreen();
        }


    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoBookController = this;
        this.setStage(stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        infoBookController = this;
    }

    public void setLivro(Livro livro) {

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
                editBtn.setText("Emprestar");
                editBtn.setOnAction(event -> {
                    this.emprestarFunction();
                });
            }
            case 2 -> {
                deleteBtn.setVisible(false);
                deleteBtn.setDisable(true);
                editBtn.setText("Reservar");
                editBtn.setOnAction(event -> {
                    this.reservarFunction();
                });
            }
            case 3 -> {
                deleteBtn.setVisible(false);
                deleteBtn.setDisable(true);
                editBtn.setText("Logar");
                editBtn.setOnAction(actionEvent -> {
                    mainWindow.callLoginScreen();
                });
            }

        }
        this.livro = livro;

        nomeText.setText(livro.getNome());
        autorText.setText(livro.getAutor());
        categoriaText.setText(livro.getCategoria());
        anoText.setText(livro.getAnoDePublicacao().toString());
        qntText.setText(livro.getQuantidade().toString());
        idText.setText(livro.getIsbn());
        localizacaoText.setText(livro.getEnderecoLivro());
        qntBuscasText.setText(livro.getQntDeBuscas().toString());
        editoraText.setText(livro.getEditora());

        deleteBtn.setOnAction(actionEvent -> {
            this.deleteFunction();
        });

    }

    @FXML
    private void emprestarFunction() {
        Emprestimo emprestimo;
        Alert alert;

        try{
            libarianHomeController.callToShowInRight("/prog/pbl/LoanCreatePage.fxml");
            loanCreateController.setEmprestimo(livro);
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
            MasterDao.getLivroDao().delete(MasterDao.getLivroDao().findById(livro.getIsbn()));
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Emprestimo Deletado");
            alert.setTitle("Sucesso!");
            alert.show();
            admHomeController.refreshScreen();
        } catch (Exception e) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Deu ruim meu chapa");
            alert.show();
        }
    }

    @FXML
    private void reservarFunction() {
        Alert alert;
        FilaDeReserva filaDeReserva;
        try{
            filaDeReserva = MasterDao.getFiladeReservaDao().findById(livro.getIsbn());

        } catch (Exception e){
            filaDeReserva = new FilaDeReserva(livro.getIsbn());
        }
        try {
            filaDeReserva.addOnReservas(Sistema.getSessaoAtualLeitor());
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Renovação realizada com sucesso");
            alert.setTitle("Concluido");
            alert.show();
            readerHomeController.refreshScreen();
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setTitle("Erro");
            alert.show();
        }
    }

    @FXML
    private void editFunction() {

        categoriaText.setEditable(true);
        qntText.setEditable(true);
        idText.setEditable(true);
        anoText.setEditable(true);
        localizacaoText.setEditable(true);
        autorText.setEditable(true);
        nomeText.setEditable(true);
        editoraText.setEditable(true);


        editBtn.setText("Save");

        editBtn.setOnAction(event -> {
            saveFunction();
        });

    }

    @FXML
    private void saveFunction() {
        Alert alert;
        Livro livro1 = this.livro;


        String nome = nomeText.getText();
        String isbn  = idText.getText();
        String auto = autorText.getText();
        String editora = editoraText.getText();
        String qnt  = qntText.getText();
        String categoria = categoriaText.getText();
        String ano = anoText.getText();
        String localizacao  = localizacaoText.getText();


        try{

            livro1.setEnderecoLivro(localizacao);
            livro1.setAutor(auto);
            livro1.setAnoDePublicacao(Integer.parseInt(ano));
            livro1.setIsbn(isbn);
            livro1.setNome(nome);
            livro1.setEditora(editora);
            livro1.setQuantidade(Integer.parseInt(qnt));
            livro1.setCategoria(categoria);
            MasterDao.getLivroDao().Update(livro1, this.livro);


            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Concluido");
            alert.setContentText("Livro atualizado com sucesso");
            editBtn.setText("Edit");
            admHomeController.callToShowInRight("/prog/pbl/InfoBookPage.fxml");
            infoBookController.setLivro(livro1);
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
