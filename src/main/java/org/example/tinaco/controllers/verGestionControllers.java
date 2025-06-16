package org.example.tinaco.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;

public class verGestionControllers {
    private ObtenerUsuarios usuariosVg;
    @FXML private Button botonAtrasVerGestion;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosVg = usuarios;
    }
    //ATRAS --- // NO TOCAR
    @FXML
    public void atrasVerGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasVerGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosVg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosVg.getNombreU()+" )");
        stage.setScene(scene);
    }
}
