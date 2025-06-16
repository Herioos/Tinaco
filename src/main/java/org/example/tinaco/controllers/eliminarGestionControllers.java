package org.example.tinaco.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;

public class eliminarGestionControllers {
    private ObtenerUsuarios usuariosEg;
    @FXML private Button botonAtrasEliminarGestion;
    @FXML private Button botonEliminarGestion;
    @FXML private TextField textNumeroGestion;
    //CONTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosEg = usuarios;
    }
    //ATRAS --- // NO TOCAR
    @FXML
    public void atrasEliminarGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasEliminarGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosEg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosEg.getNombreU()+" )");
        stage.setScene(scene);
    }
    //
    @FXML
    public void eliminarGestionClick(ActionEvent actionEvent) {
    }
}
