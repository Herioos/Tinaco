package org.example.tinaco.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.TablaTinacos;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class eliminarTinacoControllers {
    private ObtenerUsuarios usuariosEt;
    @FXML private Button botonAtrasEliminarTinaco;
    @FXML private TextField textEliminarTinaco;
    @FXML private TableView<TablaTinacos> tablaTinacos;
    @FXML private TableColumn<TablaTinacos,Integer> colNumeroTinaco;
    @FXML private TableColumn<TablaTinacos,String> colNombreTinaco;
    //COSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosEt = usuarios;
    }
    //ATRAS
    @FXML
    public void atrasEliminarTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasEliminarTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosEt);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosEt.getNombreU()+" )");
        stage.setScene(scene);
    }
    //ELIMINAR TINACO
    /// CHAMBA DE HERI
    @FXML
    public boolean eliminarTinacoClick(ActionEvent actionEvent) throws ClassNotFoundException {
        // 1. Validación básica
        if (textEliminarTinaco.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Verifique si colocó la ID",
                    "Necesitas una ID para eliminar");
            return false;
        }

        int idTinaco;
        try {
            idTinaco = Integer.parseInt(textEliminarTinaco.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "ID inválido",
                    "Debes ingresar un número válido como ID");
            return false;
        }

        // 2. Confirmación con el usuario
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "ID del tinaco: " + idTinaco, ButtonType.OK, ButtonType.CANCEL);
        confirm.setTitle("Confirmación");
        confirm.setHeaderText("¿Deseas eliminar este tinaco y su sensor?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return false;   // canceló
        }

        // 3. Conexión y transacción
        String url = "jdbc:mysql://localhost:3306/tinaco";
        String usuario = "root";
        String password = "";

        String consultaSensor =
                "SELECT id_sensor FROM tabla_tinacos WHERE id_tinaco = ?";
        String borrarTinaco =
                "DELETE FROM tabla_tinacos WHERE id_tinaco = ?";
        String borrarSensor =
                "DELETE FROM tabla_sensores WHERE id_sensor = ? " +
                        "AND NOT EXISTS (SELECT 1 FROM tabla_tinacos WHERE id_sensor = ?)";

        try (Connection conn = DriverManager.getConnection(url, usuario, password)) {
            conn.setAutoCommit(false);           // --- INICIA TRANSACCIÓN ---

            // 3a. Obtener id_sensor asociado
            int idSensor = -1;
            try (PreparedStatement ps = conn.prepareStatement(consultaSensor)) {
                ps.setInt(1, idTinaco);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        idSensor = rs.getInt(1);
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "Tinaco no encontrado",
                                "Verifica que el ID sea correcto.");
                        conn.rollback();
                        return false;
                    }
                }
            }

            // 3b. Borrar el tinaco (hijo)
            try (PreparedStatement psDelTinaco = conn.prepareStatement(borrarTinaco)) {
                psDelTinaco.setInt(1, idTinaco);
                psDelTinaco.executeUpdate();
            }

            // 3c. Borrar el sensor (padre) solo si ya no lo usa ningún tinaco
            try (PreparedStatement psDelSensor = conn.prepareStatement(borrarSensor)) {
                psDelSensor.setInt(1, idSensor);
                psDelSensor.setInt(2, idSensor);
                psDelSensor.executeUpdate();
            }

            conn.commit();                       // --- TODO OK ---
            mostrarAlerta(Alert.AlertType.INFORMATION, null,
                    "El tinaco (y su sensor) fueron eliminados correctamente.");
            cargarDatos();                       // refresca la tabla
            textEliminarTinaco.setText("");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos",
                    "No se pudo completar la operación.");
            return false;
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String encabezado, String cuerpo) {
        Alert a = new Alert(tipo);
        a.setTitle("Aviso");
        a.setHeaderText(encabezado);
        a.setContentText(cuerpo);
        a.show();
    }


    //TABLA CON LOS TINACOS
    public void cargarDatos() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");

        ObservableList<TablaTinacos> listaTinacos = FXCollections.observableArrayList();

        String sql ="SELECT tt.id_tinaco,tt.nombre_t,tt.capacidad,tt.id_sensor,ts.nombre_s,tt.id_usuario\n" +
                "FROM tabla_tinacos tt, tabla_sensores ts\n" +
                "WHERE tt.id_sensor = ts.id_sensor\n" +
                "AND tt.id_usuario = ?;";

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuariosEt.getId_usuario());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int numeroT = rs.getInt("id_tinaco");
                String nombreT = rs.getString("nombre_t");
                int capacidadT = rs.getInt("capacidad");
                int id_s = rs.getInt("id_sensor");
                String nombreS = rs.getString("nombre_s");

                listaTinacos.add(new TablaTinacos(numeroT, nombreT,capacidadT,id_s,nombreS));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        colNumeroTinaco.setCellValueFactory(new PropertyValueFactory<>("numeroTinaco"));
        colNombreTinaco.setCellValueFactory(new PropertyValueFactory<>("nombreTinaco"));
        tablaTinacos.setItems(listaTinacos);
    }
}
