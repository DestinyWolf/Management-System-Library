package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prog.pbl.LibraryException.estoqueExceptions.LivroException;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.estoque.Livro;
import prog.pbl.util.Data;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.AnaliseLivro.analiseLivro;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;
import static prog.pbl.controllers.MainWindow.mainWindow;

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
            case 0 -> admTela01.refreshScreen();
            case 1 -> bibliotecarioTela01.refreshScreen();
            case 2 -> leitorTela01.refreshScreen();
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
                case 0 -> admTela01.callToShowInRight("/prog/pbl/AnaliseLivroPage.fxml");
                case 1 -> bibliotecarioTela01.callToShowInRight("/prog/pbl/AnalizeLivroPage.fxml");
            }

            analiseLivro.setLivro(livro);
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
