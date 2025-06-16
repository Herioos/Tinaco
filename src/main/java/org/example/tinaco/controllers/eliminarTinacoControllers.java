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

public class eliminarTinacoControllers {
    private ObtenerUsuarios usuariosEt;
    @FXML private Button botonAtrasEliminarTinaco;
    @FXML private TextField textEliminarTinaco;
    //COSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosEt = usuarios;
    }
    //ATRAS
    @FXML
    public void atrasEliminarTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasEliminarTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosEt);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosEt.getNombreU()+" )");
        stage.setScene(scene);
    }
    //ELIMINAR TINACO
    public void eliminarTinacoClick(ActionEvent actionEvent) {
    }
}
