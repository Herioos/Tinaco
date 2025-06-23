package org.example.tinaco.models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TinacoDAO {
    public List<ObtenerTinacos> listaTinacoPorUsuario(int id_usuario) throws ClassNotFoundException {
        List<ObtenerTinacos> lista = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/";
        String bd = "tinaco";
        String usuario = "root";
        String password = "";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(url + bd, usuario, password)) {
            String query = "SELECT * FROM tabla_tinacos WHERE id_usuario = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_usuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ObtenerTinacos tinaco = new ObtenerTinacos();
                tinaco.setId_tinaco(rs.getInt("id_tinaco"));
                tinaco.setNombreTinaco(rs.getString("nombre_t"));
                tinaco.setCapacidadT(rs.getInt("capacidad"));
                tinaco.setId_s(rs.getInt("id_sensor"));
                tinaco.setNombre_s(rs.getString("nombre_s"));
                tinaco.setId_usuario(rs.getInt("id_usuario"));
                // Agrega otros campos si tienes más
                lista.add(tinaco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
