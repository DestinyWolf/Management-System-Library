package prog.pbl.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import prog.pbl.dao.MasterDao;
import prog.pbl.model.Sistema;
import prog.pbl.model.estoque.Livro;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static prog.pbl.controllers.AdmHomeController.admHomeController;
import static prog.pbl.controllers.InfoBookController.infoBookController;
import static prog.pbl.controllers.LibarianHomeController.libarianHomeController;
import static prog.pbl.controllers.GuestHomeController.guestHomeController;
import static prog.pbl.controllers.ReaderHomeController.readerHomeController;


public class ListLivrosController implements Initializable {

    private Stage stage;

    static ListLivrosController listLivrosController;

    @FXML
    private ListView<String> listLivros;
    private List<Livro> listaLivro;

    public static final ObservableList data =
            FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listLivrosController = this;
        data.clear();
        this.setStage(stage);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
        listLivrosController = this;
    }
    @FXML
    void setListView(List<Livro> livros) {
        listLivros.setEditable(false);


        ObservableList<Livro> listaLivrosData = FXCollections.observableArrayList(MasterDao.getLivroDao().findAll());


        data.add("Livros: ");
        for (Livro livro:livros) {
            data.add("Titulo: " + livro.getNome() + " | Autor: " + livro.getAutor() + " | Categoria: " + livro.getCategoria() +" | Editora: " + livro.getEditora()+ " | Endereço: " +
                    livro.getEnderecoLivro() + "| Isbn :`" + livro.getIsbn());
        }

        listLivros.setItems(data);
        listLivros.setCellFactory(TextFieldListCell.forListView());

        listLivros.setOnMouseClicked(mouseEvent -> {
            String text = listLivros.getSelectionModel().getSelectedItem();
            if(text != data.getFirst()){

            String id = text.substring(text.indexOf("`")+1);
            try {
                Livro livro = MasterDao.getLivroDao().findById(id);
                String url = "/prog/pbl/InfoBookPage.fxml";

                switch (Sistema.currentLogMember()){
                    case 0 -> admHomeController.callToShowInRight(url);
                    case 1 -> libarianHomeController.callToShowInRight(url);
                    case 2 -> readerHomeController.callToShowInRight(url);
                    case 3 -> guestHomeController.callToShowInRight(url);
                }


                infoBookController.setLivro(livro);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }}


        });

    }

    public void setListaLivro(List<Livro> lista) {
        this.listaLivro = lista;
    }

}
