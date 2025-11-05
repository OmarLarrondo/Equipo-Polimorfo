module pong.evolved {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires java.desktop;

    exports app;
    exports controlador;
    exports vista;
    exports modelo;
    exports util;

    opens controlador to javafx.fxml;
    opens vista to javafx.fxml;
}
