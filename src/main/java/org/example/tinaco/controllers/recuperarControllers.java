package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;
import java.sql.*;

public class recuperarControllers {
    @FXML private Button botonAtrasRecuperar;
    @FXML private Button botonBuscarRecuperar;
    @FXML private TextField textRecuperar;
    @FXML private TextField textRecuperarContrasena;
    @FXML
    public void atrasRecuperarClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasRecuperar.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);

    }
    @FXML
    public void buscarRecuperarClick(ActionEvent actionEvent) {
        String numeroRecuperacion = textRecuperar.getText();

        if (!numeroRecuperacion.isEmpty()) {
            Usuarios usuario = buscarUsuarioPorRecuperacion(numeroRecuperacion);
            if (usuario != null) {
                textRecuperarContrasena.setText(usuario.getContrasena()); // Mostrar la contraseña
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("No encontrado");
                alerta.setHeaderText("Numero de recuperación no encontrado");
                alerta.setContentText("Verifica que el número esté correcto.");
                alerta.show();
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campo vacío");
            alerta.setHeaderText("Falta el número de recuperación");
            alerta.setContentText("Ingresa el número de recuperación.");
            alerta.show();
        }
    }
    private Usuarios buscarUsuarioPorRecuperacion(String numeroRecuperacion) {
        String url = "jdbc:mysql://localhost:3306/tinaco";
        String usuario = "root";
        String password = "";

        Usuarios UsuarioEncontrado = null;

        try (Connection connection = DriverManager.getConnection(url, usuario, password)) {
            String query = "SELECT * FROM tabla_usuarios WHERE recuperar = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, numeroRecuperacion);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nombrec = resultSet.getString("nombreU_u");
                String contras = resultSet.getString("contrasena");
                String recu = resultSet.getString("recuperar");
                String nombreu = resultSet.getString("nombre_u");
                String apellidop = resultSet.getString("apellidoP_u");
                String telef = resultSet.getString("telefono_u");

                UsuarioEncontrado = new Usuarios(nombrec, contras, recu, nombreu, apellidop, telef);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UsuarioEncontrado;
    }
}
