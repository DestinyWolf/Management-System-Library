module prog.pbl {
    requires javafx.controls;
    requires javafx.fxml;


    opens prog.pbl to javafx.fxml;
    opens prog.pbl.model.usuarios;
    opens prog.pbl.model.estoque;
    exports prog.pbl;
    exports prog.pbl.controllers;
    opens prog.pbl.controllers to javafx.fxml;
}