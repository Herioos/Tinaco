module org.example.tinaco {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.fazecast.jSerialComm;


    opens org.example.tinaco to javafx.fxml;
    opens org.example.tinaco.models to javafx.base;

    exports org.example.tinaco;
    exports org.example.tinaco.controllers;
    opens org.example.tinaco.controllers to javafx.fxml;
}