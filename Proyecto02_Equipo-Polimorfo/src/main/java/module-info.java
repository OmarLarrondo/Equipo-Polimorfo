module pong.evolved {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    exports app;
    exports controlador;
    exports vista;
    exports modelo;
    exports util;

    opens controlador to javafx.fxml;
    opens vista to javafx.fxml;
}
