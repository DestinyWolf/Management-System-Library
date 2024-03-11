package prog.pbl.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import prog.pbl.model.Sistema;

import java.net.URL;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.BookCreateController.bookCreateController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.InfoUserController.infoUserController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;
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
            libarianHomeController.callToShowInRight(url);
            infoUserController.setUser(Sistema.getSessaoAtualBibliotecario());
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            readerHomeController.callToShowInRight(url);
            infoUserController.setUser(Sistema.getSessaoAtualLeitor());
        } else {
            admHomeController.callToShowInRight(url);
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
                        admHomeController.callToShowInRight("/prog/pbl/BookCreatePage.fxml");
                        bookCreateController.setLivro();
                    }
                    case "Usuario" -> {
                        admHomeController.callToShowInRight("/prog/pbl/UserCreatePage.fxml");
                        userCreateController.setUser();
                    }
                }
            }
            case 1 -> {
                String text = createBox.getValue();

                if (text.equals("Livro")) {
                    libarianHomeController.callToShowInRight("/prog/pbl/BookCreatePage.fxml");
                    bookCreateController.setLivro();
                }
            }
            case 2 -> readerHomeController.refreshScreen();
            case 3 -> guestHomeController.refreshScreen();
        }



    }
    public void showRelatorio(){
        admHomeController.callToShowInRight("/prog/pbl/LogPage.fxml");
    }

    public void showEmprestimo() {
        String url = "/prog/pbl/ListLoanPage.fxml";
        if(Sistema.getSessaoAtualBibliotecario() != null) {
            libarianHomeController.callToShowInCenter(url);
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            readerHomeController.callToShowInCenter(url);
        } else {
            admHomeController.callToShowInCenter(url);
        }
    }

    public void showReserva() {
        String url = "/prog/pbl/ListReservasPage.fxml";
        if(Sistema.getSessaoAtualBibliotecario() != null) {
            libarianHomeController.callToShowInCenter(url);
        } else if (Sistema.getSessaoAtualLeitor() != null) {
            readerHomeController.callToShowInCenter(url);
        } else {
            admHomeController.callToShowInCenter(url);
        }
    }


}
