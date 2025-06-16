package org.example.tinaco.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerSensores;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.SensorDAO;
import org.example.tinaco.models.Usuarios;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class crearTinacoControllers implements Initializable {
    private ObtenerUsuarios usuariosT;
    @FXML private TextField textNombreTinaco;
    @FXML private TextField textCapacidad;
    @FXML private Button botonAtrasCrearTinaco;
    @FXML private Button botonAgregarTinaco;
    @FXML private ComboBox<ObtenerSensores> comboSensor;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosT = usuarios;
        // Llenar comboSensor con sensores del usuario
        SensorDAO dao = new SensorDAO();
        try {
            List<ObtenerSensores> sensores = dao.listaSensorPorUsuario(usuarios.getId_usuario());///-----------------
            comboSensor.getItems().clear(); // Por si se llama más de una vez
            comboSensor.getItems().addAll(sensores);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //ATRAS --- //NO TOCAR
    @FXML
    public void atrasCrearTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasCrearTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosT);
        gestion.cargarDatos();

        stage.setTitle("Gestion de agua ( "+usuariosT.getNombreU()+" )");
        stage.setScene(scene);
    }
    //
    public void comboSensorClick(ActionEvent actionEvent) {
    }
    //AGREGAR TINACO --- NO TOCARS
    public void agregarTinacoClick(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        if(textNombreTinaco.getText().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto el nombre del tinaco");
            alert2.show();
            return;
        }
        if(textCapacidad.getId().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto la capacidad.");
            alert2.show();
            return;
        }
        if(comboSensor.getItems().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto un sensor");
            alert2.show();
            return;
        }
        //confirmar registro
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¿Desea guardar?");
        alert.setContentText("Nombre del tinaco : "+textNombreTinaco.getText()+
                "\nCapacidad : "+textCapacidad.getText()+"\nSensor a utilizar : "+
                comboSensor.getValue().getNombreSensor());
        Optional<ButtonType> resultado = alert.showAndWait();
        //
        if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)){
            //obtener valores del tinaco
            String nombreTinaco = textNombreTinaco.getText();
            int capacidadTinaco = Integer.parseInt(textCapacidad.getText());
            ObtenerSensores sensorSeleccionado = comboSensor.getValue();
            int id_sensor = sensorSeleccionado.getId_sensor();
            //CONEXION
            //insert de los datos en la tabla sensores
            String url = "jdbc:mysql://localhost:3306/";
            String bd = "tinaco";
            String usuario = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection connection = DriverManager.getConnection(url + bd, usuario, password);
                System.out.println("Conexion exitosa");
                // Insertar
                String tabla_u = ("INSERT INTO tabla_tinacos (nombre_t,capacidad,id_sensor,id_usuario) VALUES (?,?,?,?)");
                PreparedStatement preparedStatement = connection.prepareStatement(tabla_u);
                preparedStatement.setString(1, nombreTinaco);
                preparedStatement.setInt(2,capacidadTinaco);
                preparedStatement.setInt(3,id_sensor);
                preparedStatement.setInt(4,usuariosT.getId_usuario());
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }// termina el insert
            System.out.println("tinaco añadido \n");
            //limpiar el textfile

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("Se añadio correctamente");
            alert2.setContentText("... ");
            alert2.show();
            // ABRIR ECENA DE NEW FX
            Stage stage = (Stage) botonAgregarTinaco.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            gestionControllers gestion = fxmlLoader.getController();
            gestion.pasarUsuario(usuariosT);

            stage.setTitle("Gestion del agua ( "+usuariosT.getNombreU()+" )");
            stage.setScene(scene);
        }
    }
    //INICIANDO EL PROGRAMA
    public void initialize(URL url, ResourceBundle resourceBundle){
        SensorDAO dao = new SensorDAO();
        try {
            List<ObtenerSensores> sensores = dao.listaSensorPorUsuario(1);//----------------------s
            comboSensor.getItems().addAll(sensores); // la agregamos al ComboBox
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
