package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import java.io.IOException;
import java.util.Optional;

import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;

public class menuControllers {
    private ObtenerUsuarios usuariosM = new ObtenerUsuarios();
    @FXML private Button botonCerrarSesion;
    @FXML private Button botonPerfil;
    @FXML private Button botonHistorial;
    @FXML private Button botonGestionar;
    @FXML private Button botonSuministro;
    //CONSTRUCTORES
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosM = usuarios;
    }
    //
    @FXML
    public void verHistorialClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonHistorial.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/ver_historial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ver_historialControllers verH = fxmlLoader.getController();
        verH.pasarUsuario(usuariosM);

        stage.setTitle("Ver historial de consumo de ( "+usuariosM.getNombreU()+" ) ");
        stage.setScene(scene);
    }
    //NO TOCAR --- //gestion
    @FXML
    public void gestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {

        Stage stage = (Stage) botonGestionar.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosM);
        gestion.cargarDatos();

        stage.setTitle("Gestion de agua ( " + usuariosM.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- //ver suministros
    @FXML
    public void verSuministroClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonSuministro.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/suministro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        suministroControllers suministro = fxmlLoader.getController();
        suministro.pasarUsuario(usuariosM);

        stage.setTitle("Suministro de agua de agua ( "+usuariosM.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR ---
    @FXML
    public void cerrarSesionClick(ActionEvent actionEvent) throws IOException {
        // alerta de salir
        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("Aviso");
        alert2.setHeaderText("¿Estas seguro que quieres salir?");
        alert2.setContentText("Al darle si, se cerrara la sesión");

        Optional<ButtonType> resultado = alert2.showAndWait(); // esta línea es suficiente

        if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)) {
            // volver
            Stage stage = (Stage) botonCerrarSesion.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
        } else {
            return;
        }
    }
    //NO TOCAR --- //ver perfil
    @FXML
    public void verPerfilClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonPerfil.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/perfil.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        perfilControllers perfil = fxmlLoader.getController();
        perfil.pasarUsuario(usuariosM);

        stage.setTitle("Perfil de "+usuariosM.getNombreU());
        stage.setScene(scene);

    }
}
