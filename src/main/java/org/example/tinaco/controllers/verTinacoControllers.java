package org.example.tinaco.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.TablaTinacos;

import java.io.IOException;
import java.sql.*;

public class verTinacoControllers {
    private ObtenerUsuarios usuariosVt;
    @FXML private TableView<TablaTinacos> tablaTinacos;
    @FXML private TableColumn<TablaTinacos,Integer> colNumeroTinaco;
    @FXML private TableColumn<TablaTinacos,String> colNombreTinaco;
    @FXML private TableColumn<TablaTinacos,Integer> colCapacidad;
    @FXML private TableColumn<TablaTinacos,Integer> colNumeroSensor;
    @FXML private TableColumn<TablaTinacos,Integer> colNombreSensor;

    @FXML private Button botonAtrasVerTinaco;

    //COSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosVt = usuarios;
    }
    //ATRAS
    @FXML
    public void atrasVerTinacoClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasVerTinaco.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosVt);
        gestion.cargarDatos();

        stage.setTitle("Gestion de agua ( "+usuariosVt.getNombreU()+" )");
        stage.setScene(scene);
    }
    public void cargarDatosTinacos() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");

        ObservableList<TablaTinacos> listaTinacos = FXCollections.observableArrayList();

        // Suponiendo que hay una columna id_usuario en la tabla
        String sql = "select tt.id_tinaco,tt.nombre_t,tt.capacidad,tt.id_sensor,tt.nombre_s\n" +
                "from tabla_tinacos tt \n" +
                "where tt.id_usuario =?";

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuariosVt.getId_usuario());  // Asigna el ID del usuario
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int numeroT = rs.getInt("id_tinaco");
                String nombreT = rs.getString("nombre_t");
                int capacidadT = rs.getInt("capacidad");
                int numeroS = rs.getInt("id_sensor");
                String nombreS = rs.getString("nombre_s");  // <-- CAMBIADO
                listaTinacos.add(new TablaTinacos(numeroT, nombreT, capacidadT,numeroS, nombreS));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        colNumeroTinaco.setCellValueFactory(new PropertyValueFactory<>("numeroTinaco"));
        colNombreTinaco.setCellValueFactory(new PropertyValueFactory<>("nombreTinaco"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadTinaco"));
        colNumeroSensor.setCellValueFactory(new PropertyValueFactory<>("numeroSensor"));
        colNombreSensor.setCellValueFactory(new PropertyValueFactory<>("nombreSensor"));
        tablaTinacos.setItems(listaTinacos);
    }
}
