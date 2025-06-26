package org.example.tinaco.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerSensores;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.TablaSensores;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class gestionControllers implements Initializable {
    private ObtenerSensores obtenerSensores;
    private ObtenerUsuarios usuariosG = new ObtenerUsuarios();
    @FXML private TableView<TablaSensores> tablaSensores;
    @FXML private TableColumn<TablaSensores, Integer> colNumero;
    @FXML private TableColumn<TablaSensores, String> colNombre;
    @FXML private Button botonAtrasGestion;
    @FXML private Button botonCrearGestion;
    @FXML private Button botonVerGestion;
    @FXML private Button botonEliminarGestion;
    @FXML private Button botonAnadirTinaco;
    @FXML private Button botonAnadirSensor;
    @FXML private Button botonVerTinaco;
    @FXML private Button botonEliminarTinaco;
    @FXML private TextField textNombreSensor;
    @FXML private TextField textIdSensor;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosG = usuarios;
    }
    //NO TOCAR
    @FXML
    public void atrasGestionClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        menuControllers menu = fxmlLoader.getController();
        menu.pasarUsuario(usuariosG);

        stage.setTitle("Menu de " + usuariosG.getNombreU());
        stage.setScene(scene);
    }
    //NO TOCAR --- // CREAR GESTION
    @FXML
    public void crearGestionClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonCrearGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/crear_gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        crearGestionControllers crearGestion = fxmlLoader.getController();
        crearGestion.pasarUsuario(usuariosG);

        stage.setTitle("Crear un nuevo gestion ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // VER GESTIONES
    @FXML
    public void verGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonVerGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/ver_gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        verGestionControllers verGestion = fxmlLoader.getController();
        verGestion.pasarUsuario(usuariosG);
        verGestion.cargarDatos();

        stage.setTitle("Ver geationes de tinacos ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // ELIMINAR GESTION
    @FXML
    public void eliminarGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonEliminarGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/eliminar_gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        eliminarGestionControllers eliminarGestion = fxmlLoader.getController();
        eliminarGestion.pasarUsuario(usuariosG);
        eliminarGestion.cargarDatos();

        stage.setTitle("Eliminar gestión ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // CREAR TINACO
    @FXML
    public void anadirTinacoClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAnadirTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/crear_tinaco.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        crearTinacoControllers crearTinaco = fxmlLoader.getController();
        crearTinaco.pasarUsuario(usuariosG);

        stage.setTitle("Añadir tinaco ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // VER TINACOS
    @FXML
    public void verTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonVerTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/ver_tinaco.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        verTinacoControllers verTinaco = fxmlLoader.getController();
        verTinaco.pasarUsuario(usuariosG);
        verTinaco.cargarDatosTinacos();

        stage.setTitle("Visualizacón de tinacos ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // ELIMINAR TINACOS
    @FXML
    public void eliminarTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonEliminarTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/eliminar_tinaco.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        eliminarTinacoControllers eliminarTinaco = fxmlLoader.getController();
        eliminarTinaco.pasarUsuario(usuariosG);
        eliminarTinaco.cargarDatos();

        stage.setTitle("Eliminar tinacos ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // ELIMINAR SENSORES
    @FXML
    public void eliminarSensorClick(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        if (textIdSensor.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto un número.");
            alert2.show();
            return;
        }
        try {
            int id_sensor = Integer.parseInt(textIdSensor.getText().trim());
            boolean eliminado = eliminarSensor(id_sensor);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");

            if (eliminado) {
                System.out.println("Sensor eliminado exitosamente.");
                alert.setHeaderText("Acción Exitosa");
                alert.setContentText("Sensor eliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar el sensor.");
                alert.setHeaderText("Acción Errónea");
                alert.setContentText("No se pudo eliminar el sensor.");
            }
            alert.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID no válido");
            alert.setContentText("Ingrese un número entero válido.");
            alert.show();
        }
        // ABRIR ECENA DE NEW FX
        Stage stage = (Stage) botonAnadirSensor.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosG);

        stage.setTitle("Gestion del agua ( "+usuariosG.getNombreU()+" )");
        stage.setScene(scene);
    }
    //NO TOCAR --- // AÑADIR SENSOR
    @FXML
    public void anadirSensorClick(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        if(textNombreSensor.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("OPSSS .... ");
            alert.setContentText("verifique que esten llenas todas las casillas");
            alert.show();
            return;
        }
        if(textNombreSensor.getText().length()>=16){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("OPSSS .... ");
            alert.setContentText("verifique no exceda el limite de caracteres");
            alert.show();
            return;
        }
        //confirmar registro
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¿Desea guardar?");
        alert.setContentText("Nombre : "+textNombreSensor.getText());
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)){
            //obtenert nombre del textfile
            String nombreSensor = textNombreSensor.getText();
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
                String tabla_u = ("INSERT INTO tabla_sensores (nombre_s,id_usuario) VALUES (?,?)");
                PreparedStatement preparedStatement = connection.prepareStatement(tabla_u);
                preparedStatement.setString(1, nombreSensor);
                preparedStatement.setInt(2,usuariosG.getId_usuario());
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }// termina el insert
            //avisar que salio exitoso

            System.out.println("sensor añadido \n");
            //limpiar el textfile
            textNombreSensor.setText("");
            //
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("Se añadio correctamente");
            alert2.setContentText("... ");
            alert2.show();
            // ABRIR ECENA DE NEW FX
            Stage stage = (Stage) botonAnadirSensor.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            gestionControllers gestion = fxmlLoader.getController();
            gestion.pasarUsuario(usuariosG);
            gestion.cargarDatos();
            stage.setTitle("Gestion del agua ( "+usuariosG.getNombreU()+" )");
            stage.setScene(scene);
        }
    }
    //CARGAR DATOS DE SENSORES
    public void cargarDatos() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");

        ObservableList<TablaSensores> listaSensores = FXCollections.observableArrayList();

        // Suponiendo que hay una columna id_usuario en la tabla
        String sql = "SELECT id_sensor, nombre_s FROM tabla_sensores WHERE id_usuario = ?";

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuariosG.getId_usuario());  // Asigna el ID del usuario
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int numero = rs.getInt("id_sensor");
                String nombre = rs.getString("nombre_s");
                listaSensores.add(new TablaSensores(numero, nombre));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tablaSensores.setItems(listaSensores);
    }
    //METODO DE COSAS QUE SE INICIAN
    @Override
    public void initialize(URL location, ResourceBundle resources){
    }
    //ELIMINAR SNESOR METODO
    public boolean eliminarSensor(int id_sensor) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver");

        String sql = "DELETE FROM tabla_sensores WHERE id_sensor = ?";

        try(Connection conn = DriverManager.getConnection(url + bd, usuario, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_sensor);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

