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
    public boolean eliminarTinacoClick(ActionEvent actionEvent) throws ClassNotFoundException {
        if (textEliminarTinaco.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Alerta");
            alerta.setHeaderText("Verifique si colocó la ID");
            alerta.setContentText("Necesitas una ID para eliminar");
            alerta.show();
            return false;
        }
        //confirmar registro
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¿Deseas eliminar?");
        alert.setContentText("ID del tinaco : "+textEliminarTinaco.getId());
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)) {
            int idSensor = Integer.parseInt(textEliminarTinaco.getText());
            //caso si le da aceptar
        }
        //caso contrarios
        return false;
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
