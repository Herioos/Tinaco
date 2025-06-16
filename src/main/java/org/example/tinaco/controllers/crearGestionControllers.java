package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;

public class crearGestionControllers {
    private ObtenerUsuarios usuariosCg;
    @FXML private Button botonAtrasCrearGestion;
    @FXML private Button botonAgregarGestion;
    @FXML private Button botonMenosMaximo;
    @FXML private Button botonMasMaximo;
    @FXML private Button botonMenosMinimo;
    @FXML private Button botonMasMinimo;
    @FXML private TextField textNombreGestion;
    @FXML private TextField textNivelMaximo;
    @FXML private TextField textNivelMinimo;
    @FXML private ComboBox comboTinaco;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosCg = usuarios;
    }
    //ATRAS --- NO TOCAR
    @FXML
    public void atrasCrearGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasCrearGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosCg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosCg.getNombreU()+" )");
        stage.setScene(scene);
    }
    //
    @FXML
    public void agregarGestionClick(ActionEvent actionEvent) {
    }
    @FXML
    public void comboTinacoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void menosMaximoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void masMaximoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void masMinimoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void menosMinimoClick(ActionEvent actionEvent) {
    }
}
