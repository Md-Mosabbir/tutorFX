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
    requires jbcrypt;

    requires com.google.gson;

    // Add this exports line
    exports tutor.dsafx.auth to com.google.gson;


    opens tutor.dsafx.auth to com.google.gson;

    opens tutor.dsafx to javafx.fxml;
    opens tutor.dsafx.controllers to javafx.fxml;
    exports tutor.dsafx.controllers to javafx.fxml;
    exports tutor.dsafx;




}