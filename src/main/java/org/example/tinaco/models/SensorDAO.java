package org.example.tinaco.models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SensorDAO {
    public List<ObtenerSensores> listaSensorPorUsuario(int id_usuario) throws ClassNotFoundException {
        List<ObtenerSensores> lista = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password)) {
            String query = "SELECT * FROM tabla_sensores WHERE id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_usuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ObtenerSensores sensor = new ObtenerSensores();
                sensor.setId_sensor(rs.getInt("id_sensor"));
                sensor.setNombreSensor(rs.getString("nombre_s"));
                // Agrega otros campos si tienes más
                lista.add(sensor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}

