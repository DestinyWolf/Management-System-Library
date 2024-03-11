package prog.pbl.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import prog.pbl.model.relatorio.Relatorios;

public class Relatorio {

    @FXML
    private Label QntLivros;


    @FXML
    private Label QntLivrosAtrasados;

    @FXML
    private Label QntLivrosEmprestado;
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
        this.stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
