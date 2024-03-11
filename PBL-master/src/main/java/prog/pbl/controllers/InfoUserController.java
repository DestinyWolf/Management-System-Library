package prog.pbl.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Bibliotecario;
import prog.pbl.model.usuarios.Leitor;

import java.net.URL;
import java.util.ResourceBundle;


import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;
import static prog.pbl.controllers.MainWindow.mainWindow;

public class InfoUserController implements Initializable{

    private Stage stage;
    static InfoUserController infoUserController;
    private int currentMember;

    private Object obj;

    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button closeBtn;

    @FXML
    private Label firtsNameLabel;
    @FXML
    private Label telLabel;
    @FXML
    private Label enderecoLabel;
    @FXML
    private Label qntEmprestimoLabel;

    @FXML
    private TextField nameBox;
    @FXML
    private TextField idBox;
    @FXML
    private TextField telBox;
    @FXML
    private TextField enderecoBox;
    @FXML
    private TextField qntEmprestimosBox;
    @FXML
    private TextField passWordBox;
    @FXML
    private TextField cargoBox;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        infoUserController = this;
        this.setStage(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        infoUserController = this;
    }


    @FXML
    void setUser(Object obj) {
        Leitor currentReader = null;
        Administrador currentAdm = null;
        Bibliotecario currentLib = null;
        this.obj = obj;

        if(obj instanceof Leitor) {
            currentReader = (Leitor) obj;
        } else if(obj instanceof Bibliotecario) {
            currentLib = (Bibliotecario) obj;
        } else if (obj instanceof Administrador) {
            currentAdm = (Administrador) obj;
        }

        String name = "";
        String id = "";
        String tel = "(99) 9 9999-9999";
        String endereco = "Terra do nunca";
        String passWord = "xxxxxxx";
        String qntEmprestimos = "0";
        String cargo = "";


        switch (Sistema.currentLogMember()){
            case 1 ->{
                deleteBtn.setDisable(true);
            }
            case 2-> {
                deleteBtn.setDisable(true);
                editBtn.setDisable(true);
                deleteBtn.setVisible(false);
                editBtn.setVisible(false);
            }
        }


        if (currentReader != null) {
            name = currentReader.getNome();
            id = currentReader.getId();
            tel = currentReader.getTelefone();
            endereco = currentReader.getEndereco();
            passWord = currentReader.getSenha();
            cargo = "Leitor";

            qntEmprestimos = String.valueOf(2 - currentReader.getNumEmprestimos());


        }
        else if (currentLib != null){
            name = currentLib.getNome();
            id = currentLib.getId();
            passWord = currentLib.getSenha();
            cargo = currentLib.getCargo();


            telBox.setVisible(false);
            enderecoBox.setVisible(false);
            qntEmprestimosBox.setVisible(false);
            telLabel.setVisible(false);
            enderecoLabel.setVisible(false);
            qntEmprestimoLabel.setVisible(false);


        }
        else{
            name = currentAdm.getNome();
            id = currentAdm.getId();
            passWord = currentAdm.getSenha();
            cargo = currentAdm.getCargo();

            telBox.setVisible(false);
            enderecoBox.setVisible(false);
            qntEmprestimosBox.setVisible(false);
            telLabel.setVisible(false);
            enderecoLabel.setVisible(false);
            qntEmprestimoLabel.setVisible(false);
        }

        int index = (name.indexOf(' ') != -1 ) ? name.indexOf(' ') + 1 : name.length();

        firtsNameLabel.setText("@" + name.substring(0, index));
        nameBox.setText(name);
        idBox.setText(id);
        telBox.setText(tel);
        enderecoBox.setText(endereco);
        qntEmprestimosBox.setText(qntEmprestimos);
        passWordBox.setText(passWord);
        cargoBox.setText(cargo);

        editBtn.setOnAction(actionEvent -> editFunction());
        deleteBtn.setOnAction(actionEvent -> deleteFunction());
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
    public void deleteFunction(){
        Alert alert;

        if(this.obj instanceof Leitor){
            currentMember = 2;
        } else if (this.obj instanceof Bibliotecario) {
            currentMember = 1;
        } else {
            currentMember = 0;
        }

        switch (currentMember) {
            case 0 -> {
                try {
                    MasterDao.getAdministradorDao().delete((Administrador) obj);
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Concluido");
                    alert.setContentText("Usuario " + ((Administrador) obj).getNome() + " deletado com sucesso");
                    alert.showAndWait();
                    admHomeController.refreshScreen();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case 1 -> {
                try {
                    MasterDao.getBibliotecarioDao().delete((Bibliotecario) obj);
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Concluido");
                    alert.setContentText("Usuario " + ((Bibliotecario) obj).getNome() + " deletado com sucesso");
                    alert.showAndWait();
                    admHomeController.refreshScreen();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("vixe, deu ruim");
                    alert.setContentText(e.getMessage());
                    alert.show();
                    admHomeController.refreshScreen();
                }
            }
            case 2 -> {
                try {
                    MasterDao.getLeitorDao().delete((Leitor) obj);
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Concluido");
                    alert.setContentText("Usuario " + ((Leitor) obj).getNome() + " deletado com sucesso");
                    alert.showAndWait();
                } catch (Exception e) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        }
    }
    @FXML
    public void editFunction(){
        editBtn.setText("Save");
        cargoBox.setEditable(true);
        telBox.setEditable(true);
        enderecoBox.setEditable(true);
        idBox.setEditable(true);
        passWordBox.setEditable(true);
        nameBox.setEditable(true);

        editBtn.setOnAction(actionEvent -> saveAlterations());
    }

    @FXML
    private void saveAlterations() {
        if(this.obj instanceof Leitor){
            currentMember = 2;
        } else if (this.obj instanceof Bibliotecario) {
            currentMember = 1;
        } else {
            currentMember = 0;
        }
        String name = "";
        String id = "";
        String tel = "(99) 9 9999-9999";
        String endereco = "Terra do nunca";
        String passWord = "xxxxxxx";
        String cargo = "";
        Alert alert;

        String url = "/prog/pbl/InfoUserPage.fxml";

        name = nameBox.getText();
        id = idBox.getText();
        tel = telBox.getText();
        endereco = enderecoBox.getText();
        passWord = passWordBox.getText();
        cargo = cargoBox.getText();

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");


        switch (currentMember){
            case 0 -> {
                try {
                    Administrador administrador = new Administrador(passWord, name, cargo, id);
                    MasterDao.getAdministradorDao().Update(administrador, (Administrador) obj);

                        if((Administrador) obj == Sistema.getSessaoAtualAdministrador()){
                            Sistema.setSessaoAtualAdministrador(administrador);
                        }



                    admHomeController.refreshScreen();
                    admHomeController.callToShowInRight(url);
                    infoUserController.setUser(administrador);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case 1 -> {
                try {
                    Bibliotecario bibliotecario = new Bibliotecario(name, passWord, id, cargo);
                    MasterDao.getBibliotecarioDao().Update(bibliotecario, (Bibliotecario) obj);

                    if((Bibliotecario)obj == Sistema.getSessaoAtualBibliotecario()){
                        Sistema.setSessaoAtualBibliotecario(bibliotecario);
                        libarianHomeController.refreshScreen();
                        libarianHomeController.callToShowInRight(url);
                    } else {
                        admHomeController.refreshScreen();
                        admHomeController.callToShowInRight(url);
                    }
                    infoUserController.setUser(bibliotecario);
                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
            case 2 -> {
                try {
                    Leitor leitor = new Leitor(name,passWord,id,endereco,tel);
                    MasterDao.getLeitorDao().Update(leitor, (Leitor) obj);


                    if((Leitor)obj == Sistema.getSessaoAtualLeitor()){
                        Sistema.setSessaoAtualLeitor(leitor);
                        readerHomeController.refreshScreen();
                        readerHomeController.callToShowInRight(url);
                    }else{
                        admHomeController.refreshScreen();
                        admHomeController.callToShowInRight(url);
                    }
                    infoUserController.setUser(leitor);

                } catch (Exception e) {
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        }

    }
}