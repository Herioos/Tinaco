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
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;
import java.io.IOException;
import java.sql.*;

public class loginControllers {

    @FXML private Button botonNuevaCuenta;
    @FXML private Button botonIniciarSesion;
    @FXML private Button botonRecuperar;
    @FXML private TextField textUsuario;
    @FXML private TextField textContrasena;
    //NO TOCAR
    @FXML
    public void nuevaCuentaCick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonNuevaCuenta.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro");
        stage.setScene(scene);
    }
    //NO TOCAR
    @FXML
    public void iniciarSesionClick(ActionEvent actionEvent) throws IOException {
        //obtener datos del usuarios
        String usuarioIngresado = textUsuario.getText();
        String contrasenaIngresada = textContrasena.getText();

        if (!usuarioIngresado.isEmpty() && !contrasenaIngresada.isEmpty()) {

            //se obtiene el usuario de la bd
            ObtenerUsuarios usuarios = obtenerUsuarioLogin(usuarioIngresado,contrasenaIngresada);

            Alert alerta;
            if (usuarios == null) {
                //usuario no se encontro
                alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Alerta");
                alerta.setHeaderText("Usuario NO Encontrado");
                alerta.setContentText("No se encontro el usuario");
                alerta.show();
            } else {
                //el usuario se encontro y nos devuelve al menú
                Stage stage = (Stage) botonIniciarSesion.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                menuControllers menu = fxmlLoader.getController();
                menu.pasarUsuario(usuarios);

                stage.setTitle("Menú de " + usuarios.getNombreU());
                stage.setScene(scene);
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Datos necesarios");
            alerta.setContentText("Por favor ingrese su nombre de usuario y contraseña.");
            alerta.show();
        }

    }
    //NO TOCAR
    @FXML
    public void recuperarCick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonRecuperar.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/recuperar.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro");
        stage.setScene(scene);
    }
    //obtener usuario --- NO TOCAR
    public ObtenerUsuarios obtenerUsuarioLogin(String nombreU, String contrasena) {
        //atributos de la conexion
        String url = "jdbc:mysql://localhost:3306/tinaco";
        String usuario = "root";
        String password = "";

        //almacena el usuario en el objeto
        ObtenerUsuarios UsuarioEncontrado = null;

        //conexion con la consulta para obtener el usuario por nombreU
        try (Connection connection = DriverManager.getConnection(url, usuario, password)) {
            String query = "SELECT * FROM tabla_usuarios WHERE nombreU_u = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombreU);

            //ejecucion de la consulta
            ResultSet resultSet = statement.executeQuery();

            //si el usuario existe va obtener los datos del usaurio
            if (resultSet.next()) {
                int id_usuario = resultSet.getInt("id_usuario");
                String nombrec = resultSet.getString("nombreU_u");
                String contras = resultSet.getString("contrasena");
                String recu = resultSet.getString("recuperar");
                String nombreu = resultSet.getString("nombre_u");
                String apellidop = resultSet.getString("apellidoP_u");
                String telef= resultSet.getString("telefono_u");

                // verificacion si la contrasena coincide del usuario
                if (contras.equals(contrasena)) {
                    UsuarioEncontrado = new ObtenerUsuarios(id_usuario,nombrec,contras,recu,nombreu,apellidop,telef);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UsuarioEncontrado;
    }
}
