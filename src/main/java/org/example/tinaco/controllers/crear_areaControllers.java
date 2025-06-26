package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.*;
import java.io.IOException;


public class crear_areaControllers {

    private ObtenerUsuarios usuariosCa = new ObtenerUsuarios();

    @FXML private Button botonAtrasArea;
    @FXML private Button botonAceptarArea;


    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosCa = usuarios;
    }
    //ATRAS
    @FXML
    public void atrasAreaClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasArea.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosCa);
        gestion.cargarDatos(); // Make sure this method exists and works in gestionControllers

        stage.setTitle("Menu de " + usuariosCa.getNombreU());
        stage.setScene(scene);
    }
    @FXML
    public void agregarAreaClick(ActionEvent actionEvent) {
    }
}
