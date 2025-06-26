package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class registroControllers {
    @FXML private Button botonCancelarRegistro;
    @FXML private Button botonAceptarRegistro;
    @FXML private TextField textNombreU;
    @FXML private TextField textNombreC;
    @FXML private TextField textApellido;
    @FXML private TextField textContrasena;
    @FXML private TextField textTelefono;
    @FXML private TextField textNumeroR;
    @FXML
    public void cancelarRegistroCick(ActionEvent actionEvent) throws IOException {
        if(textNombreC.getText().isEmpty() || textNombreU.getText().isEmpty() ||
                textApellido.getText().isEmpty() || textContrasena.getText().isEmpty() ||
                textTelefono.getText().isEmpty() || textNumeroR.getText().isEmpty()) {
            // volver
            Stage stage = (Stage) botonCancelarRegistro.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
        } else {
            // alerta de salir
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("¿Estas seguro que quieres regresar?");
            alert2.setContentText("Se perderan todos los datos ingresados");

            Optional<ButtonType> resultado = alert2.showAndWait();

            if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)) {
                // volver
                Stage stage = (Stage) botonCancelarRegistro.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Login");
                stage.setScene(scene);
            } else {
                return;
            }
        }
    }
    @FXML
    public void aceptarRegistroClick(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        //verificar si estan llenos las casillas
        if (textNombreC.getText().isEmpty()
                || textNombreU.getText().isEmpty()
                || textApellido.getText().isEmpty()
                || textTelefono.getText().isEmpty()
                || textContrasena.getText().isEmpty()
                || textNumeroR.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Dato erroneo");
            alert.setContentText("verifique que esten llenas todas las casillas");
            alert.show();
            return;
        }
        String nombreUsuario = textNombreU.getText();
        if (!nombreUsuario.matches("[a-zA-Z]+") || nombreUsuario.length() >= 16) {
            Alert alertNombreU = new Alert(Alert.AlertType.ERROR);
            alertNombreU.setTitle("Alerta");
            alertNombreU.setHeaderText("Nombre de Usuario no válido");
            alertNombreU.setContentText("El nombre de usuario debe contener solo letras y tener menos de 16 caracteres.");
            alertNombreU.show();
            return;
        }
        // Verificar que textContrasena solo contenga números y sea menor a 16 caracteres
        String contrasenaText = textContrasena.getText();
        if (!contrasenaText.matches("[0-9]+") || contrasenaText.length() >= 16) {
            Alert alertContrasena = new Alert(Alert.AlertType.ERROR);
            alertContrasena.setTitle("Alerta");
            alertContrasena.setHeaderText("Contraseña no válida");
            alertContrasena.setContentText("La contraseña debe contener solo números y tener menos de 16 caracteres.");
            alertContrasena.show();
            return;
        }
        //verifica que el nombre de cuenta sea no mayor de 16
        if(textNombreC.getText().length()>16){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("El nombre de cuenta no es valido");
            alert2.setContentText("verifique que no pase de los 16 caracteres");
            alert2.show();
            return;
        }
        //verifica que el apellido sea solo letras y menor de 16 caracteres
        String apellido = textApellido.getText();
        if (!apellido.matches("[a-zA-Z]+") || apellido.length() >= 16) {
            Alert alertNombreU = new Alert(Alert.AlertType.ERROR);
            alertNombreU.setTitle("Alerta");
            alertNombreU.setHeaderText("Apellido del Usuario no válido");
            alertNombreU.setContentText("El apellido del usuario debe contener solo letras y tener menos de 16 caracteres.");
            alertNombreU.show();
            return;
        }
        //verificar si es numero
        boolean verificar_numero=containsSpecial(textTelefono.getText());
        if(verificar_numero==true){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("El numero telefonico no es valido");
            alert2.setContentText("verifique que sean numeros y no letras");
            alert2.show();
            return;
        }
        // Verificar que textNumeroR solo contenga números y sea menor a 16 caracteres
        String numeroRecuperacionText = textNumeroR.getText();
        if (!numeroRecuperacionText.matches("[0-9]+") || numeroRecuperacionText.length() >= 16) {
            Alert alertNumeroR = new Alert(Alert.AlertType.ERROR);
            alertNumeroR.setTitle("Alerta");
            alertNumeroR.setHeaderText("Número de Recuperación no válido");
            alertNumeroR.setContentText("El número de recuperación debe contener solo números y tener menos de 16 caracteres.");
            alertNumeroR.show();
            return;
        }
        //verifica si son 10 numeros
        if(textTelefono.getLength()!=10){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("El numero telefonico no es valido");
            alert2.setContentText("verifique que sean 10 digitos");
            alert2.show();
        }else {
            //confirmar registro
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("¿Desea guardar?");
            alert.setContentText("Nombre de cuenta : " + textNombreC.getText() + "\nContraseña : " + textContrasena.getText() +
                    "\nNumero de recuperacion : " + textNumeroR.getText() + "\nNombre : " + textNombreU.getText() + "\nApellido paterno : " + textApellido.getText() +
                    "\nTelefono : " + textTelefono.getText());
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)) {

                String nombreC = textNombreC.getText();
                String contrasena = textContrasena.getText();
                String recuperar = textNumeroR.getText();
                String nombreU = textNombreU.getText();
                String apellidoP = textApellido.getText();
                String telefono = textTelefono.getText();


                //insert de los datos en la tabla usuarios
                String url = "jdbc:mysql://localhost:3306/";
                String bd = "tinaco";
                String usuario = "root";
                String password = "";
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    Connection connection = DriverManager.getConnection(url + bd, usuario, password);
                    System.out.println("Conexion exitosa");
                    // Insertar
                    String tabla_u = ("INSERT INTO tabla_usuarios (nombreU_u, contrasena, recuperar, nombre_u, apellidoP_u, telefono_u) VALUES (?,?,?,?,?,?)");

                    PreparedStatement preparedStatement = connection.prepareStatement(tabla_u);
                    preparedStatement.setString(1, nombreC);
                    preparedStatement.setString(2, contrasena);
                    preparedStatement.setString(3, recuperar);
                    preparedStatement.setString(4, nombreU);
                    preparedStatement.setString(5, apellidoP);
                    preparedStatement.setString(6, telefono);
                    preparedStatement.executeUpdate();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }// termina el insert
                //avisar que salio exitoso

                //alerta de exito
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Aviso");
                alert2.setHeaderText("Los datos se han guardado correctamente");
                alert2.setContentText("Tarea exitosa");
                alert2.show();
                //volver a login
                Stage stage = (Stage) botonAceptarRegistro.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Login");
                stage.setScene(scene);
            }
        }
    }
    public boolean containsSpecial(String numero) {
        if (numero.matches("^[0-9]+$")) {
            return false;
        } else {
            return true;
        }
    }
}