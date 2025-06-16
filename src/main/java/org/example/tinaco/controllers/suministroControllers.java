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

public class suministroControllers {
    private ObtenerUsuarios usuariosS;
    @FXML private Button botonAtrasSuministro;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosS = usuarios;
    }
    //ATRAS
    @FXML
    public void atrasSuministroClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasSuministro.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        menuControllers menu = fxmlLoader.getController();
        menu.pasarUsuario(usuariosS);

        stage.setTitle("Menu de "+usuariosS.getNombreU());
        stage.setScene(scene);
    }
}
