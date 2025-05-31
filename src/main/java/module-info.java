module tutor.dsafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens tutor.dsafx to javafx.fxml;
    opens tutor.dsafx.controllers to javafx.fxml;
    exports tutor.dsafx.controllers to javafx.fxml;

    exports tutor.dsafx;
}