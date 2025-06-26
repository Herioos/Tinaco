package org.example.tinaco.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.chart.XYChart;


public class ver_historialControllers {

    @FXML private ComboBox<String> comboTinaco; // Changed to String
    @FXML private LineChart<String,Number> lineChart;

    private ObtenerUsuarios usuariosVi = new ObtenerUsuarios();
    @FXML private Button botonAtrasVerH;

    private Map<String, Integer> tinacoNameToIdMap = new HashMap<>(); // Map to store tinaco name to ID

    // Cargar tinacos del usuario logueado
    @FXML
    private void cargarTinacosDelUsuario() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tinaco", "root", "")) {
            // Modified SQL to select tinaco name and ID
            String sql = "SELECT DISTINCT tt.id_tinaco, tt.nombre_t " +
                    "FROM tabla_tinacos tt " +
                    "JOIN tabla_lecturas tl ON tt.id_tinaco = tl.id_tinaco " +
                    "WHERE tl.id_usuario = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, usuariosVi.getId_usuario());
            ResultSet rs = pst.executeQuery();
            tinacoNameToIdMap.clear(); // Clear previous mappings
            comboTinaco.getItems().clear(); // Clear previous items
            while (rs.next()) {
                String nombreTinaco = rs.getString("nombre_t");
                int idTinaco = rs.getInt("id_tinaco");
                comboTinaco.getItems().add(nombreTinaco);
                tinacoNameToIdMap.put(nombreTinaco, idTinaco); // Store mapping
            }
            // Cuando se selecciona un tinaco, cargar la gráfica
            comboTinaco.setOnAction(e -> cargarGrafica());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Mostrar historial del tinaco seleccionado
    @FXML
    public void cargarGrafica() {
        String nombreTinacoSeleccionado = comboTinaco.getValue(); // Get selected tinaco name
        if (nombreTinacoSeleccionado == null) return;

        Integer idTinaco = tinacoNameToIdMap.get(nombreTinacoSeleccionado); // Get ID from map
        if (idTinaco == null) return; // Should not happen if map is populated correctly

        lineChart.getData().clear(); // limpia grafica anterior

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Historial de consumo");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tinaco", "root", "")) {
            String sql = "SELECT fecha_l, hora_l, cantidad_l FROM tabla_lecturas " +
                    "WHERE id_usuario = ? AND id_tinaco = ? ORDER BY fecha_l, hora_l";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, usuariosVi.getId_usuario());
            pst.setInt(2, idTinaco);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String fechaHora = rs.getDate("fecha_l") + " " + rs.getTime("hora_l").toString();
                int cantidad = rs.getInt("cantidad_l");

                // agregar dato a la serie
                serie.getData().add(new XYChart.Data<>(fechaHora, cantidad));
            }
            lineChart.getData().add(serie); // mostrar en la grafica
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //CONSTRUCTORES-------------------------------------------------
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosVi = usuarios;
        cargarTinacosDelUsuario();
    }
    //volver atras
    public void atrasVerHClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) botonAtrasVerH.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        menuControllers menu = fxmlLoader.getController();
        menu.pasarUsuario(usuariosVi);

        stage.setTitle("Menu de " + usuariosVi.getNombreU());
        stage.setScene(scene);
    }
    //--------------------------------------------------------------
}
