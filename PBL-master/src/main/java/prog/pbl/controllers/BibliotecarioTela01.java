package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static prog.pbl.controllers.ListLivrosController.listLivrosController;
import static prog.pbl.controllers.MainWindow.mainWindow;
import static prog.pbl.controllers.MainWindow.openPage;

public class BibliotecarioTela01 implements Initializable{

    static BibliotecarioTela01 bibliotecarioTela01;

    @FXML
    private BorderPane secondBorderPane;
    @FXML
    private Button searchBtn;

    @FXML
    private TextField searchInputText;

    @FXML
    private ChoiceBox<String> searchTypeChoiceBox;

    @FXML
    private BorderPane mainBorderPane;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        bibliotecarioTela01 = this;
        this.setScreen();
    }

    @FXML
    void setScreen() {


        mainBorderPane.setLeft(openPage("/prog/pbl/userAreaPage.fxml"));
        secondBorderPane.setTop(openPage("/prog/pbl/ListLivrosPage.fxml"));
        secondBorderPane.setBottom(openPage("/prog/pbl/ListReservasPage.fxml"));

        listLivrosController.setListView(MasterDao.getLivroDao().findAll());

        searchTypeChoiceBox.getItems().addAll("Isbn","Categoria","Autor", "Nome", "Mais populares");
        searchTypeChoiceBox.setValue("Isbn");
        searchBtn.setOnAction(actionEvent -> searchFunction());
        searchTypeChoiceBox.setOnAction(actionEvent -> {
            if(Objects.equals(searchTypeChoiceBox.getValue(), "Mais populares")){
                searchFunction();
            }
        });
    }

    @FXML
    public void callToShowInLeft(String url) {
        this.mainBorderPane.setLeft(openPage(url));
    }

    @FXML
    public void callToShowInRight(String url) {
        this.mainBorderPane.setRight(openPage(url));
    }

    @FXML
    public void callToShowInCenter(String url){
        this.mainBorderPane.setCenter(openPage(url));
    }

    @FXML
    public void refreshScreen() {
        mainWindow.refreshMainWindow("/prog/pbl/LibTela01.fxml");

    }

    @FXML
    private void searchFunction() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro ao Buscar");
        String type = searchTypeChoiceBox.getValue();
        String text = searchInputText.getText();
        String url = "/prog/pbl/ListLivrosPage.fxml";
        this.callToShowInCenter(url);

        switch (type) {
            case "Isbn" -> {
                try {
                    List<Livro> livro = new LinkedList<>();
                    livro.add(MasterDao.getLivroDao().findById(text));
                    listLivrosController.setListView(livro);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }

            }
            case "Autor"-> {
                try {
                    listLivrosController.setListView(MasterDao.getLivroDao().findByAutor(text));
                } catch (Exception e){
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case "Categoria" -> {
                try {
                    listLivrosController.setListView(MasterDao.getLivroDao().findByCategoria(text));
                } catch (Exception e){
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case "Nome" -> {
                try {
                    listLivrosController.setListView(MasterDao.getLivroDao().findByNome(text));
                } catch (Exception e){
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case "Mais populares" ->{
                listLivrosController.setListView(MasterDao.getLivroDao().findLivrosMaisPesquisados());
            }
        }
    }
}
