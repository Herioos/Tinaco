package org.example.tinaco.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class perfilControllers implements Initializable {

    private ObtenerUsuarios usuariosP; // No inicializar aquí
    @FXML private Button botonAtrasPerfil;
    @FXML private TextField textUsuario;
    @FXML private TextField textNombre;
    @FXML private TextField textApellidoP;
    @FXML private TextField textTelefono;
    //para que se ejecute al iniciar
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    // metodo para recibir el usuario y cargar los datos
    public void pasarUsuario(ObtenerUsuarios usuarios) {
        this.usuariosP = usuarios;
        cargarDatosUsuario();
    }
    // metodo para cargar los datos en los campos
    private void cargarDatosUsuario() {
        if (usuariosP != null) {
            textUsuario.setText(usuariosP.getNombreC());
            textApellidoP.setText(usuariosP.getApellidoP());
            textNombre.setText(usuariosP.getNombreU());
            textTelefono.setText(usuariosP.getTelefono());
        }
    }
    //atras
    @FXML
    public void atrasPerfilClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasPerfil.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        menuControllers menu = fxmlLoader.getController();
        menu.pasarUsuario(usuariosP);

        stage.setTitle("Menu de " + usuariosP.getNombreU());
        stage.setScene(scene);
    }
}