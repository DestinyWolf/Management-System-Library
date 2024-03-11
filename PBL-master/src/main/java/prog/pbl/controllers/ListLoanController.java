package prog.pbl.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.emprestimo.Emprestimo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.InfoLoanController.infoLoanController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;

import javafx.scene.control.ListView;


public class ListLoanController implements Initializable {

    static ListLoanController listLoanController;

    @FXML
    private ListView<String> listEmprestimos;

    public static final ObservableList data =
            FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listLoanController = this;
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
                String url = "/prog/pbl/InfoLoanPage.fxml";

                switch (Sistema.currentLogMember()){
                    case 0 -> admHomeController.callToShowInRight(url);
                    case 1 -> libarianHomeController.callToShowInRight(url);
                    case 2 -> readerHomeController.callToShowInRight(url);
                    case 3 -> guestHomeController.callToShowInRight(url);
                }
                infoLoanController.setEmprestimo(emprestimo);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }}


        });




    }

}
