package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prog.pbl.LibraryException.usersexcepitions.LeitorException;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Bibliotecario;
import prog.pbl.model.usuarios.Leitor;
import prog.pbl.model.usuarios.Pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.InfoUserController.infoUserController;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;
import static prog.pbl.controllers.MainWindow.mainWindow;
import static prog.pbl.controllers.MainWindow.openPage;

public class UserCreateController implements Initializable{

    private Stage stage;
    static UserCreateController userCreateController;
    private int currentMember;

    @FXML
    private Button editBtn;
    @FXML
    private Button closeBtn;

    @FXML
    private Label firtsNameLabel;
    @FXML
    private Label telLabel;
    @FXML
    private Label enderecoLabel;

    @FXML
    private TextField nameBox;
    @FXML
    private TextField idBox;
    @FXML
    private TextField telBox;
    @FXML
    private TextField enderecoBox;
    @FXML
    private TextField passWordBox;
    @FXML
    private ChoiceBox<String> cargoBox;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        userCreateController = this;
        this.setUser();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        userCreateController = this;
    }


    @FXML
    void setUser() {
        cargoBox.getItems().addAll("Administrador", "Bibliotecario", "Leitor");
        cargoBox.setValue("Administrador");

        enderecoLabel.setVisible(false);
        enderecoBox.setVisible(false);
        telLabel.setVisible(false);
        telBox.setVisible(false);
        enderecoBox.setDisable(true);
        telBox.setDisable(true);

        editBtn.setOnAction(actionEvent -> saveAlterations());
        cargoBox.setOnAction(actionEvent -> setCamps());
        closeBtn.setOnAction(actionEvent -> closePage());


    }

    public void closePage() {
        switch (Sistema.currentLogMember()){
            case 0 -> mainWindow.callAdministratorHomeScreen();
            case 1 -> mainWindow.callLibrarianHomeScreen();
            case 2 -> mainWindow.callReaderHomeScreen();
        }
    }

    @FXML
    private void setCamps(){
        String cargo = cargoBox.getValue();
        switch (cargo){
            case "Administrador", "Bibliotecario" -> {
                enderecoLabel.setVisible(false);
                enderecoBox.setVisible(false);
                telLabel.setVisible(false);
                telBox.setVisible(false);
                enderecoBox.setDisable(true);
                telBox.setDisable(true);

            }
            case "Leitor" -> {
                enderecoLabel.setVisible(true);
                enderecoBox.setVisible(true);
                telLabel.setVisible(true);
                telBox.setVisible(true);
                enderecoBox.setDisable(false);
                telBox.setDisable(false);
            }
        }

    }

    @FXML
    private void saveAlterations() {

        String cargo = cargoBox.getValue();
        String name = nameBox.getText();
        String id = idBox.getText();
        String tel = telBox.getText();
        String endereco = enderecoBox.getText();
        String passWord = passWordBox.getText();


        if(Objects.equals(cargo, "Leitor")){
            currentMember = 2;
        } else if (Objects.equals(cargo, "Bibliotecario")) {
            currentMember = 1;
        } else if (Objects.equals(cargo, "Administrador")){
            currentMember = 0;
        }

        Alert alert;

        String url = "/prog/pbl/InfoUserPage.fxml";

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");


        switch (currentMember){
            case 0 -> {
                try {
                    Administrador administrador = new Administrador(passWord, name, cargo, id);
                    MasterDao.getAdministradorDao().save(administrador);



                    admTela01.refreshScreen();
                    admTela01.callToShowInRight(url);
                    infoUserController.setUser(administrador);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case 1 -> {
                try {
                    Bibliotecario bibliotecario = new Bibliotecario(name, passWord, id, cargo);
                    MasterDao.getBibliotecarioDao().save(bibliotecario);
                    admTela01.refreshScreen();
                    admTela01.callToShowInRight(url);
                    infoUserController.setUser(bibliotecario);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case 2 -> {
                try {
                    Leitor leitor = new Leitor(name,passWord,id,endereco,tel);
                    MasterDao.getLeitorDao().save(leitor);




                    admTela01.refreshScreen();
                    admTela01.callToShowInRight(url);
                    infoUserController.setUser(leitor);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        }

    }
}