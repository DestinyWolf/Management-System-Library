package prog.pbl.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Bibliotecario;
import prog.pbl.model.usuarios.Leitor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.AnaliseEmprestimo.analiseEmprestimo;
import static prog.pbl.controllers.InfoUserController.infoUserController;


public class ListUsuariosController implements Initializable {

    static ListUsuariosController listLivrosController;

    @FXML
    private ListView<String> listUsuarios;

    public static final ObservableList data =
            FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listLivrosController = this;
        data.clear();
        this.setListView();
    }



    @FXML
    void setListView() {

        listUsuarios.setPrefSize(260, 360);
        listUsuarios.setEditable(false);

        List<Administrador> listAdministrador =  MasterDao.getAdministradorDao().findAll();
        List<Leitor> listLeitor = MasterDao.getLeitorDao().findAll();
        List<Bibliotecario> listBibliotecario= MasterDao.getBibliotecarioDao().findAll();
        ObservableList<Livro> listaLivrosData = FXCollections.observableArrayList(MasterDao.getLivroDao().findAll());


        data.add("Usuarios: ");
        for (Administrador administrador:listAdministrador) {
            data.add("Nome: " + administrador.getNome() +"| Cargo: "+ administrador.getCargo()+  "| Id: `" + administrador.getId());
        }
        for (Bibliotecario bibliotecario: listBibliotecario){
            data.add("Nome: " + bibliotecario.getNome() +"| Cargo: "+ bibliotecario.getCargo()+  "| Id: `" + bibliotecario.getId());
        }
        for (Leitor leitor: listLeitor){
            data.add("Nome: " + leitor.getNome() +"| Cargo: Leitor"+ " | Tel: "+leitor.getTelefone()+" | Endereço: "+leitor.getEndereco()+" | Bloqueio: "+leitor.isBloqueio() +"| Id: `" + leitor.getId());
        }

        listUsuarios.setItems(data);
        listUsuarios.setCellFactory(TextFieldListCell.forListView());

        listUsuarios.setOnMouseClicked(mouseEvent -> {
            String text = listUsuarios.getSelectionModel().getSelectedItem();
            if(text != data.getFirst()){
            String id = text.substring(text.indexOf("`")+1);
            String text2;
            try {
                Object obj = null;
                try{
                    obj = MasterDao.getAdministradorDao().findById(id);
                    text2 = "Nome: " + ((Administrador)obj).getNome() +"| Cargo: "+ ((Administrador)obj).getCargo()+  "| Id: `" + ((Administrador)obj).getId();
                    if(!text2.equals(text)){
                        throw new Exception();
                    }
                } catch (Exception e) {
                    try {
                        obj = MasterDao.getBibliotecarioDao().findById(id);
                        text2 = "Nome: " + ((Bibliotecario)obj).getNome() +"| Cargo: "+ ((Bibliotecario)obj).getCargo()+  "| Id: `" + ((Bibliotecario)obj).getId();
                        if(!text2.equals(text)){
                            throw new Exception();
                        }
                    }catch (Exception e1) {
                        try {
                            obj = MasterDao.getLeitorDao().findById(id);
                            text2 = "Nome: " + ((Leitor)obj).getNome() +"| Cargo: Leitor"+ " | Tel: "+((Leitor)obj).getTelefone()+" | Endereço: "+((Leitor)obj).getEndereco()+" | Bloqueio: "+((Leitor)obj).isBloqueio() +"| Id: `" + ((Leitor)obj).getId();

                            if(!text2.equals(text)){
                                throw new Exception();
                            }
                        }catch (Exception e2) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText(e.getMessage());
                            alert.show();
                        }
                    }
                }


                admTela01.callToShowInRight("/prog/pbl/InfoUserPage.fxml");
                infoUserController.setUser(obj);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }}


        });




    }

}
