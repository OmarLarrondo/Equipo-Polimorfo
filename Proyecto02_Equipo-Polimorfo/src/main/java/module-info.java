module pong.evolved {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;

    exports main.java.controlador;
    exports main.java.vista;
    exports main.java.modelo;
    exports main.java.util;

    opens main.java.controlador to javafx.fxml;
    opens main.java.vista to javafx.fxml;
}
