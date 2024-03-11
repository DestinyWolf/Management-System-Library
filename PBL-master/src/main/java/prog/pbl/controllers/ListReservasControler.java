package prog.pbl.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import prog.pbl.dao.FileManeger;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.emprestimo.Emprestimo;
import prog.pbl.model.emprestimo.FilaDeReserva;
import prog.pbl.model.usuarios.Leitor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmTela01.admTela01;
import static prog.pbl.controllers.AnaliseEmprestimo.analiseEmprestimo;
import static prog.pbl.controllers.BibliotecarioTela01.bibliotecarioTela01;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.LeitorTela01.leitorTela01;


public class ListReservasControler implements Initializable {

    static ListReservasControler listReservasControler;

    @FXML
    private ListView<String> listReservas;

    public static final ObservableList data =
            FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listReservasControler = this;
        data.clear();
        this.setListView();
    }



    @FXML
    void setListView() {

        listReservas.setPrefSize(260, 360);
        listReservas.setEditable(false);

        List<FilaDeReserva> listaReservas =  MasterDao.getFiladeReservaDao().findAll();
        ObservableList<Emprestimo> EmprestimoData = FXCollections.observableArrayList(MasterDao.getEmprestimoDao().findAll());

        data.add("Reservas: ");
        try {
            for (FilaDeReserva filaDeReserva : listaReservas) {
                for(Leitor leitor:filaDeReserva.getReservas()) {
                    data.add("Leitor: " + leitor.getNome() + " | Livro: " + MasterDao.getLivroDao().findById(filaDeReserva.getIsbn()).getNome() + "| id: `" + filaDeReserva.getIsbn());
                }
            }
        } catch (Exception e) {
            //todo
        }

        listReservas.setItems(data);
        listReservas.setCellFactory(TextFieldListCell.forListView());

        listReservas.setOnMouseClicked(mouseEvent -> {
            String text = listReservas.getSelectionModel().getSelectedItem();
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
