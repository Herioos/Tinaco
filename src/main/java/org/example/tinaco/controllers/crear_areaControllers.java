package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class crear_areaControllers {

    private ObtenerUsuarios usuariosCa = new ObtenerUsuarios();
    private SensorDAO sensorDAO = new SensorDAO();
    private ObservableList<TablaSensores> sensoresList = FXCollections.observableArrayList();
    private ObservableList<TablaAreas> areasList = FXCollections.observableArrayList();
    private List<ObtenerSensores> obtenerSensoresList; // To store full sensor objects for ID lookup

    @FXML private Button botonAtrasArea;
    @FXML private Button botonAceptarArea;

    @FXML private TableView<TablaSensores> tablaSensores;
    @FXML private TableColumn<TablaSensores, Integer> numero_sensor;
    @FXML private TableColumn<TablaSensores, String> nombre_sensor_s;

    @FXML private ComboBox<String> comboArea;
    @FXML private ComboBox<String> comboSensor;

    @FXML private TableView<TablaAreas> tablaAreas;
    @FXML private TableColumn<TablaAreas, Integer> numero_area;
    @FXML private TableColumn<TablaAreas, String> nombre_area;
    @FXML private TableColumn<TablaAreas, String> nombre_sensor_a; // Assuming this is the correct ID for FXML

    @FXML private TextField cantidadTextField; // Assuming a TextField for "cantidad_ll"

    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosCa = usuarios;
        initializeViewData(); // Call initialization after user is set
    }
    private void initializeViewData() {
        // Populate tablaSensores
        numero_sensor.setCellValueFactory(new PropertyValueFactory<>("numero"));
        nombre_sensor_s.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cargarSensores();

        // Populate comboArea
        comboArea.setItems(FXCollections.observableArrayList(
                "Jardin", "Regadera", "WC", "Lava Manos", "Lava Trastes", "Lavadora"
        ));

        // Populate comboSensor
        // This will be populated by cargarSensores -> actualizarComboSensores

        // Populate tablaAreas
        numero_area.setCellValueFactory(new PropertyValueFactory<>("num_a"));
        nombre_area.setCellValueFactory(new PropertyValueFactory<>("nom_a"));
        nombre_sensor_a.setCellValueFactory(new PropertyValueFactory<>("nom_s"));
        cargarAreas();
    }
    private void cargarSensores() {
        sensoresList.clear();
        if (usuariosCa != null && usuariosCa.getId_usuario() > 0) {
            try {
                obtenerSensoresList = sensorDAO.listaSensorPorUsuario(usuariosCa.getId_usuario());
                for (ObtenerSensores os : obtenerSensoresList) {
                    sensoresList.add(new TablaSensores(os.getId_sensor(), os.getNombreSensor()));
                }
                tablaSensores.setItems(sensoresList);
                actualizarComboSensores();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al cargar los sensores: " + e.getMessage());
            }
        }
    }
    private void actualizarComboSensores() {
        if (obtenerSensoresList != null) {
            comboSensor.setItems(FXCollections.observableArrayList(
                    obtenerSensoresList.stream()
                            .map(ObtenerSensores::getNombreSensor)
                            .collect(Collectors.toList())
            ));
        }
    }


    //ATRAS
    @FXML
    public void atrasAreaClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasArea.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosCa);
        gestion.cargarDatos(); // Make sure this method exists and works in gestionControllers

        stage.setTitle("Menu de " + usuariosCa.getNombreU());
        stage.setScene(scene);
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void cargarAreas() {
        areasList.clear();
        if (usuariosCa == null || usuariosCa.getId_usuario() <= 0) {
            return; // No user, no areas to load
        }

        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuarioDB = "root";
        String passwordDB = "";

        // Query to join tabla_areas with tabla_sensores to get sensor name
        String query = "SELECT ta.id_area, ta.nombre_area, ts.nombre_s " +
                "FROM tabla_areas ta " +
                "JOIN tabla_sensores ts ON ta.id_sensor = ts.id_sensor " +
                "WHERE ta.id_usuario = ?";

        try (Connection conn = DriverManager.getConnection(url + bd, usuarioDB, passwordDB);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            pstmt.setInt(1, usuariosCa.getId_usuario());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // TablaAreas expects SimpleIntegerProperty and SimpleStringProperty
                // but the constructor in the provided TablaAreas.java is incorrect.
                // Assuming direct creation for now, might need to adjust TablaAreas model
                areasList.add(new TablaAreas(
                        new javafx.beans.property.SimpleIntegerProperty(rs.getInt("id_area")),
                        new javafx.beans.property.SimpleStringProperty(rs.getString("nombre_area")),
                        new javafx.beans.property.SimpleStringProperty(rs.getString("nombre_s"))
                ));
            }
            tablaAreas.setItems(areasList);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Base de Datos", "Error al cargar las áreas: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Driver", "No se encontró el driver de la base de datos: " + e.getMessage());
        }
    }
    @FXML
    public void agregarAreaClick(ActionEvent actionEvent) {
        String areaSeleccionada = comboArea.getValue();
        String sensorSeleccionado = comboSensor.getValue();
        String cantidadStr = cantidadTextField.getText(); // Get quantity from TextField

        if (areaSeleccionada == null || areaSeleccionada.isEmpty()) {
            mostrarAlerta("Error de validación", "Debe seleccionar un área.");
            return;
        }
        if (sensorSeleccionado == null || sensorSeleccionado.isEmpty()) {
            mostrarAlerta("Error de validación", "Debe seleccionar un sensor.");
            return;
        }
        if (cantidadStr == null || cantidadStr.isEmpty()) {
            mostrarAlerta("Error de validación", "Debe ingresar la cantidad.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                mostrarAlerta("Error de validación", "La cantidad debe ser un número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de validación", "La cantidad debe ser un número válido.");
            return;
        }


        Optional<ObtenerSensores> sensorOpt = obtenerSensoresList.stream()
                .filter(s -> s.getNombreSensor().equals(sensorSeleccionado))
                .findFirst();

        if (!sensorOpt.isPresent()) {
            mostrarAlerta("Error", "No se pudo encontrar el ID del sensor seleccionado.");
            return;
        }
        int idSensor = sensorOpt.get().getId_sensor();
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaActual = LocalTime.now();

        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco"; // Make sure this is your DB name
        String usuarioDB = "root"; // Your DB username
        String passwordDB = ""; // Your DB password

        String query = "INSERT INTO tabla_areas (fecha_ll, hora_ll, cantidad_ll, id_sensor, id_usuario, nombre_area) VALUES (?, ?, ?, ?, ?, ?)";


        try (Connection conn = DriverManager.getConnection(url + bd, usuarioDB, passwordDB);
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            pstmt.setDate(1, Date.valueOf(fechaActual));
            pstmt.setTime(2, Time.valueOf(horaActual));
            pstmt.setInt(3, cantidad);
            pstmt.setInt(4, idSensor);
            pstmt.setInt(5, usuariosCa.getId_usuario());
            pstmt.setString(6, areaSeleccionada); // Storing area name

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                mostrarAlerta("Éxito", "Área guardada correctamente.");
                cargarAreas(); // Refresh the areas table
                // Optionally clear selections
                comboArea.getSelectionModel().clearSelection();
                comboSensor.getSelectionModel().clearSelection();
                cantidadTextField.clear();
            } else {
                mostrarAlerta("Error", "No se pudo guardar el área.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Base de Datos", "Error al guardar el área: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Driver", "No se encontró el driver de la base de datos: " + e.getMessage());
        }
    }
}
