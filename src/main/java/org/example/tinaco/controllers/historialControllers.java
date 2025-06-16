package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import java.io.IOException;

public class historialControllers {
    @FXML private Button botonAtrasHistorial;
    @FXML
    public void atrasHistorialClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasHistorial.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu");
        stage.setScene(scene);
    }
}
