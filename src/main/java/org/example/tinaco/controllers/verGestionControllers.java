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
import org.example.tinaco.models.TablaGestion;
import org.example.tinaco.models.TablaTinacos;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;
import java.sql.*;

public class verGestionControllers {
    private ObtenerUsuarios usuariosVg;
    @FXML private Button botonAtrasVerGestion;
    @FXML private TableView<TablaGestion> tablaGestion;
    @FXML private TableColumn<TablaGestion,Integer> numeroGestion;
    @FXML private TableColumn<TablaGestion,String> nombreGestion;
    @FXML private TableColumn<TablaGestion,Integer> limMax;
    @FXML private TableColumn<TablaGestion,Integer> limMin;
    @FXML private TableColumn<TablaGestion,String> nombreTinaco;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosVg = usuarios;
    }
    //ATRAS --- // NO TOCAR
    @FXML
    public void atrasVerGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasVerGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosVg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosVg.getNombreU()+" )");
        stage.setScene(scene);
    }
    public void cargarDatos() throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";

        Class.forName("com.mysql.cj.jdbc.Driver");
        ObservableList<TablaGestion> listaGestion = FXCollections.observableArrayList();

        String sql = "select tg.id_gestion,tg.nombre_g,tg.nivelMaximo,tg.nivelMinimo,tg.nombre_t\n" +
                "from tabla_gestiones tg \n" +
                "where tg.id_usuario =?";

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuariosVg.getId_usuario());  // Asigna el ID del usuario
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idG = rs.getInt("id_gestion");
                String nomG = rs.getString("nombre_g");
                int lMax = rs.getInt("nivelMaximo");
                int lMin = rs.getInt("nivelMinimo");
                String nomT = rs.getString("nombre_t");
                listaGestion.add(new TablaGestion(idG,nomG,lMax,lMin,nomT));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numeroGestion.setCellValueFactory(new PropertyValueFactory<>("numeroG"));
        nombreGestion.setCellValueFactory(new PropertyValueFactory<>("nombreG"));
        limMax.setCellValueFactory(new PropertyValueFactory<>("limiteMax"));
        limMin.setCellValueFactory(new PropertyValueFactory<>("limiteMin"));
        nombreTinaco.setCellValueFactory(new PropertyValueFactory<>("nombreT"));
        tablaGestion.setItems(listaGestion);


    }
}
