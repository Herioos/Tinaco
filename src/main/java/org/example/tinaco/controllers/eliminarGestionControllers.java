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
import org.example.tinaco.models.TablaGestion;

import java.io.IOException;
import java.sql.*;

public class eliminarGestionControllers {
    private ObtenerUsuarios usuariosEg;
    @FXML private Button botonAtrasEliminarGestion;
    @FXML private Button botonEliminarGestion;
    @FXML private TextField textNumeroGestion;
    @FXML private TableView<TablaGestion> tablaGestion;
    @FXML private TableColumn<TablaGestion,Integer> numeroGestion;
    @FXML private TableColumn<TablaGestion,String> nombreGestion;
    //CONTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosEg = usuarios;
    }
    //ATRAS --- // NO TOCAR
    @FXML
    public void atrasEliminarGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasEliminarGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosEg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosEg.getNombreU()+" )");
        stage.setScene(scene);
    }
    //
    @FXML
    public void eliminarGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (textNumeroGestion.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto un número.");
            alert2.show();
            return;
        }
        try {
            int id_gestion = Integer.parseInt(textNumeroGestion.getText().trim());
            boolean eliminado = eliminarGestion(id_gestion);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso");

            if (eliminado) {
                System.out.println("Gestion eliminado exitosamente.");
                alert.setHeaderText("Acción Exitosa");
                alert.setContentText("Gestioneliminado exitosamente.");
            } else {
                System.out.println("No se pudo eliminar.");
                alert.setHeaderText("Acción Errónea");
                alert.setContentText("No se pudo eliminar.");
            }
            alert.show();

        } catch (NumberFormatException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ID no válido");
            alert.setContentText("Ingrese un número entero válido.");
            alert.show();
        }
        // ABRIR ECENA DE NEW FX
        Stage stage = (Stage) botonEliminarGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/eliminar_gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        eliminarGestionControllers eliminarG = fxmlLoader.getController();
        eliminarG.pasarUsuario(usuariosEg);
        eliminarG.cargarDatos();

        stage.setTitle("Gestion del agua ( "+usuariosEg.getNombreU()+" )");
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

            pstmt.setInt(1, usuariosEg.getId_usuario());  // Asigna el ID del usuario
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
        tablaGestion.setItems(listaGestion);

    }
    //ELIMINAR GESTION METODO
    public boolean eliminarGestion(int id_gestion) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver");

        String sql = "DELETE FROM tabla_gestiones WHERE id_gestion = ?";

        try(Connection conn = DriverManager.getConnection(url + bd, usuario, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_gestion);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
