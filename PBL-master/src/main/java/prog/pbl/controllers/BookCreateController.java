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
import prog.pbl.model.estoque.Livro;

import java.net.URL;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.InfoBookController.infoBookController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;

public class BookCreateController implements Initializable{
    private Stage stage;
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
    private Button voltarButton;
    @FXML
    private Button editBtn;

    public static BookCreateController bookCreateController;

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
        bookCreateController = this;
        this.setStage(stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        bookCreateController = this;
    }

    public void setLivro() {
        nomeText.setText("Nome do Livro");


        editBtn.setOnAction(actionEvent -> {
            saveFunction();
        });

    }

    @FXML
    private void saveFunction() {
        Alert alert;



        String nome = nomeText.getText();
        String isbn  = idText.getText();
        String autor = autorText.getText();
        String editora = editoraText.getText();
        String qnt  = qntText.getText();
        String categoria = categoriaText.getText();
        String ano = anoText.getText();
        String localizacao  = localizacaoText.getText();

        Livro livro = new Livro(isbn, autor, categoria, localizacao, editora, Integer.parseInt(ano), nome, Integer.parseInt(qnt));


        try {
            MasterDao.getLivroDao().save(livro);

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Concluido");
            alert.setContentText("Livro adicionado com sucesso");
            switch (Sistema.currentLogMember()){
                case 0 -> admHomeController.callToShowInRight("/prog/pbl/InfoBookPage.fxml");
                case 1 -> libarianHomeController.callToShowInRight("/prog/pbl/AnalizeLivroPage.fxml");
            }

            infoBookController.setLivro(livro);
            alert.show();
            System.out.println("chegou aqui");
        }
        catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
