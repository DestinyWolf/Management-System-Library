package prog.pbl.controllers;

import prog.pbl.dao.FileManeger;
import prog.pbl.model.Sistema;
import prog.pbl.Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainWindow implements Initializable {
    public static MainWindow mainWindow;

    @FXML
    private BorderPane  mainBorderPane;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindow = this;

        try{
            FileManeger.generateCache();
        } catch (Exception e){
            //todo
        }

        Sistema.atualizaMultas();



        try {
            callLoginScreen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void callLoginScreen(){
        this.refreshMainWindow("/prog/pbl/hello-view.fxml");
    }

    @FXML
    public void callAdministratorHomeScreen(){
        this.refreshMainWindow("/prog/pbl/AdmTela01.fxml");
    }

    @FXML
    public void callLibrarianHomeScreen() {this.refreshMainWindow("/prog/pbl/LibTela01.fxml");}

    @FXML
    public void callReaderHomeScreen() {this.refreshMainWindow("/prog/pbl/LeitorTela01.fxml");}

    @FXML
    public void callGuestHomeScreen() {this.refreshMainWindow("/prog/pbl/GuestHomePage.fxml");}

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

    public void refreshMainWindow(String url){
        this.mainBorderPane.setRight(null);
        this.mainBorderPane.setLeft(null);
        this.mainBorderPane.setCenter(openPage(url));
    }

    public static Parent openPage(String url){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(url)));
        } catch(Exception e){
            e.printStackTrace();
        }
        return root;
    }


}

