package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.ObtenerUsuarios;
import org.example.tinaco.models.Usuarios;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class eliminarTinacoControllers {
    private ObtenerUsuarios usuariosEt;
    @FXML private Button botonAtrasEliminarTinaco;
    @FXML private TextField textEliminarTinaco;
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
    public void eliminarTinacoClick(ActionEvent actionEvent) {
        String idSensorToDelete = textEliminarTinaco.getText();
        Connection conn = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtDelete = null;

        String url = "jdbc:mysql://localhost:3306/tinaco";
        String user = "root";
        String password = "";

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            // Disable autocommit
            conn.setAutoCommit(false);

            // Prepare UPDATE statement
            String updateSql = "UPDATE tabla_tinacos SET id_sensor = NULL WHERE id_sensor = ?";
            pstmtUpdate = conn.prepareStatement(updateSql);
            pstmtUpdate.setString(1, idSensorToDelete);
            int affectedRowsUpdate = pstmtUpdate.executeUpdate();

            // Prepare DELETE statement
            String deleteSql = "DELETE FROM tabla_sensores WHERE id_sensor = ?";
            pstmtDelete = conn.prepareStatement(deleteSql);
            pstmtDelete.setString(1, idSensorToDelete);
            int affectedRowsDelete = pstmtDelete.executeUpdate();

            // Commit transaction
            conn.commit();
            System.out.println("Tinaco eliminado con éxito. Filas afectadas (tabla_tinacos): " + affectedRowsUpdate + ", Filas afectadas (tabla_sensores): " + affectedRowsDelete);

        } catch (SQLException e) {
            System.err.println("Error de SQL al eliminar tinaco: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transacción revertida.");
                } catch (SQLException ex) {
                    System.err.println("Error al revertir la transacción: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar tinaco: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmtUpdate != null) {
                    pstmtUpdate.close();
                }
                if (pstmtDelete != null) {
                    pstmtDelete.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
