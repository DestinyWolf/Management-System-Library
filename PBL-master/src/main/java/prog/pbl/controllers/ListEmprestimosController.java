package prog.pbl.controllers;

import com.sun.javafx.stage.EmbeddedWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.estoque.Livro;
import prog.pbl.model.usuarios.Administrador;
import prog.pbl.model.usuarios.Pessoa;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.AnaliseEmprestimo.analiseEmprestimo;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;
import static prog.pbl.controllers.MainWindow.mainWindow;
import static prog.pbl.controllers.MainWindow.openPage;

import javafx.scene.control.ListView;


public class ListEmprestimosController implements Initializable {

    static ListEmprestimosController listEmprestimosController;

    @FXML
    private ListView<String> listEmprestimos;

    public static final ObservableList data =
            FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listEmprestimosController = this;
        data.clear();
        this.setListView();
    }



    @FXML
    void setListView() {



        List<Emprestimo> listaEmprestimos =  MasterDao.getEmprestimoDao().findAll();
        ObservableList<Emprestimo> EmprestimoData = FXCollections.observableArrayList(MasterDao.getEmprestimoDao().findAll());


        data.add("Emprestiomos: ");
        for (Emprestimo emprestimo:listaEmprestimos) {
            data.add("Leitor: " + emprestimo.getLeitor().getNome() + " | Livro: " + emprestimo.getLivro().getNome() + " | Status: " + emprestimo.isDevolvido() + " | id :`" + emprestimo.getId());
        }

        listEmprestimos.setItems(data);
        listEmprestimos.setCellFactory(TextFieldListCell.forListView());

        listEmprestimos.setOnMouseClicked(mouseEvent -> {
            String text = listEmprestimos.getSelectionModel().getSelectedItem();
            if(text != data.getFirst()){
            String id = text.substring(text.indexOf("`")+1);
            try {
                Emprestimo emprestimo = MasterDao.getEmprestimoDao().findById(id);
                String url = "/prog/pbl/AnaliseEmprestimo.fxml";

                switch (Sistema.currentLogMember()){
                    case 0 -> admTela01.callToShowInRight(url);
                    case 1 -> bibliotecarioTela01.callToShowInRight(url);
                    case 2 -> leitorTela01.callToShowInRight(url);
                    case 3 -> guestHomeController.callToShowInRight(url);
                }
                analiseEmprestimo.setEmprestimo(emprestimo);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }}


        });




    }

}
