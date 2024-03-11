package prog.pbl.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;

import java.net.URL;
import java.util.ResourceBundle;

import static prog.pbl.controllers.MainWindow.mainWindow;

public class LoginController implements Initializable{

    static LoginController loginController;



    @FXML
    private Button loginBtn;

    @FXML
    private Button loginGuestBtn;

    @FXML
    private TextField idInputText;

    @FXML
    private TextField passWordInputText;

    @FXML
    private ChoiceBox<String> profileSelectBox;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginController = this;
        this.setScreen();
    }

    @FXML
    void setScreen() {
       profileSelectBox.getItems().addAll("Administrador", "Bibliotecario", "Leitor");
       profileSelectBox.setValue("Administrador");

        loginBtn.setOnAction(event -> loginFunction());
        loginGuestBtn.setOnAction(event -> mainWindow.callGuestHomeScreen());

    }



    private void loginFunction() {
        String option = profileSelectBox.getValue();

        try {
            switch (option) {
                case "Administrador" -> {

                    Sistema.setSessaoAtualAdministrador(MasterDao.getAdministradorDao().findLogin(idInputText.getText(), passWordInputText.getText()));
                    mainWindow.callAdministratorHomeScreen();
                }
                case "Bibliotecario" -> {
                    Sistema.setSessaoAtualBibliotecario(MasterDao.getBibliotecarioDao().findLogin(idInputText.getText(), passWordInputText.getText()));
                    mainWindow.callLibrarianHomeScreen();
                }
                case "Leitor" -> {
                    Sistema.setSessaoAtualLeitor(MasterDao.getLeitorDao().findLogin(idInputText.getText(), passWordInputText.getText()));
                    mainWindow.callReaderHomeScreen();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("deu ruim meu chapa");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }
}
