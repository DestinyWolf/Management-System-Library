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
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.BookCreateController.bookCreateController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.InfoUserController.infoUserController;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;
import static prog.pbl.controllers.MainWindow.mainWindow;
import static prog.pbl.controllers.UserCreateController.userCreateController;

public class UserAreaController implements Initializable {

    @FXML
    private Pane mainPane;

    @FXML
    Button reservaBtn;

    @FXML
    Button emprestimoBtn;

    @FXML
    Button relatorioBtn;

    @FXML
    Button gerenciamentoBtn;

    @FXML
    Button logOutBtn;

    @FXML
    ImageView profileBtn;

    @FXML
    ChoiceBox<String> createBox;

    static UserAreaController userAreaController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        userAreaController = this;
        this.setScreen();
    }

    @FXML
    void setScreen(){

        switch (Sistema.currentLogMember()){
            case  0 -> {
                createBox.getItems().addAll("Livro", "Usuario");
            }
            case 1 ->{
                relatorioBtn.setDisable(true);
                relatorioBtn.setVisible(false);
                createBox.getItems().add("Livro");
            }
            case 2 -> {
                relatorioBtn.setDisable(true);
                relatorioBtn.setVisible(false);
                gerenciamentoBtn.setDisable(true);
                gerenciamentoBtn.setVisible(false);
                createBox.setDisable(true);
                createBox.setVisible(false);

            }
        }

        createBox.setValue("Livro");
        profileBtn.setOnMouseClicked(mouseEvent -> showInformations());
        logOutBtn.setOnAction(actionEvent -> logOut());
        gerenciamentoBtn.setOnAction(actionEvent -> showGerenciamento());
        relatorioBtn.setOnAction(actionEvent -> showRelatorio());
        emprestimoBtn.setOnAction(actionEvent -> showEmprestimo());
        reservaBtn.setOnAction(actionEvent -> showReserva());
    }

    private void showInformations() {
        String url = "/prog/pbl/InfoUserPage.fxml";
        if(Sistema.getSessaoAtualBibliotecario() != null) {
            bibliotecarioTela01.callToShowInRight(url);
            infoUserController.setUser(Sistema.getSessaoAtualBibliotecario());
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            leitorTela01.callToShowInRight(url);
            infoUserController.setUser(Sistema.getSessaoAtualLeitor());
        } else {
            admTela01.callToShowInRight(url);
            infoUserController.setUser(Sistema.getSessaoAtualAdministrador());
        }

    }
    public void logOut() {
        Sistema.setSessaoAtualLeitor(null);
        Sistema.setSessaoAtualAdministrador(null);
        Sistema.setSessaoAtualBibliotecario(null);
        mainWindow.callLoginScreen();
    }
    public void showGerenciamento(){

        switch (Sistema.currentLogMember()) {



            case 0 -> {
                String text = createBox.getValue();

                switch (text) {
                    case "Livro" -> {
                        admTela01.callToShowInRight("/prog/pbl/BookCreatePage.fxml");
                        bookCreateController.setLivro();
                    }
                    case "Usuario" -> {
                        admTela01.callToShowInRight("/prog/pbl/UserCreatePage.fxml");
                        userCreateController.setUser();
                    }
                }
            }
            case 1 -> {
                String text = createBox.getValue();

                if (text.equals("Livro")) {
                    bibliotecarioTela01.callToShowInRight("/prog/pbl/BookCreatePage.fxml");
                    bookCreateController.setLivro();
                }
            }
            case 2 -> leitorTela01.refreshScreen();
            case 3 -> guestHomeController.refreshScreen();
        }



    }
    public void showRelatorio(){
        admTela01.callToShowInRight("/prog/pbl/Relatorio.fxml");
    }

    public void showEmprestimo() {
        String url = "/prog/pbl/ListEmprestimosPage.fxml";
        if(Sistema.getSessaoAtualBibliotecario() != null) {
            bibliotecarioTela01.callToShowInCenter(url);
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            leitorTela01.callToShowInCenter(url);
        } else {
            admTela01.callToShowInCenter(url);
        }
    }

    public void showReserva() {
        String url = "/prog/pbl/ListReservasPage.fxml";
        if(Sistema.getSessaoAtualBibliotecario() != null) {
            bibliotecarioTela01.callToShowInCenter(url);
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            leitorTela01.callToShowInCenter(url);
        } else {
            admTela01.callToShowInCenter(url);
        }
    }


}
