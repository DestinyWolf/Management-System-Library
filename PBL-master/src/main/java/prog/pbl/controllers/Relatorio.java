package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import prog.pbl.model.relatorio.Relatorios;

import static prog.pbl.controllers.AdmTela01.admTela01;

public class Relatorio {

    @FXML
    private TextField QntLivros;


    @FXML
    private TextField QntLivrosAtrasados;

    @FXML
    private TextField QntLivrosEmprestado;
    private Stage stage;
    @FXML
    private Button voltarButton;

    private Relatorios relatorio;

    @FXML
    private Button AtualizarButton;

    @FXML
    void AtualizarButtonAction(ActionEvent event) {
        this.Atualizar();
    }


    @FXML
    void initialize() {
        Atualizar();
    }
    public void Atualizar(){
        relatorio = new Relatorios();
        this.QntLivros.setText(relatorio.getQntLivros());
        this.QntLivrosAtrasados.setText(relatorio.getQntLivrosAtrasados());
        this.QntLivrosEmprestado.setText(relatorio.getQntLivroEmprestados());
    }

    @FXML
    void voltarButtonAction(ActionEvent event) {
        admTela01.refreshScreen();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
