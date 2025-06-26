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
import javafx.scene.chart.XYChart;


public class ver_historialControllers {

    @FXML private ComboBox<Integer> comboTinaco;
    @FXML private LineChart<String,Number> lineChart;

    private ObtenerUsuarios usuariosVi = new ObtenerUsuarios();
    @FXML private Button botonAtrasVerH;


    // Cargar tinacos del usuario logueado
    @FXML
    private void cargarTinacosDelUsuario() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tinaco", "root", "")) {
            String sql = "SELECT DISTINCT tl.id_tinaco \n" +
                    "FROM tabla_lecturas tl  \n" +
                    "WHERE tl.id_usuario = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, usuariosVi.getId_usuario());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                comboTinaco.getItems().add(rs.getInt("id_tinaco"));
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
        Integer idTinaco = comboTinaco.getValue();
        if (idTinaco == null) return;

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
