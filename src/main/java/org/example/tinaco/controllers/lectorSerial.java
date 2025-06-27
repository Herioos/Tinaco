package org.example.tinaco.controllers; // o donde tengas tu paquete de conexión

import com.fazecast.jSerialComm.SerialPort;
import java.sql.*;
import java.util.Scanner;

public class lectorSerial {

    public static void main(String[] args) {
        SerialPort puerto = SerialPort.getCommPort(""); // Ajusta el puerto----------------------
        puerto.setBaudRate(9600);
        if (!puerto.openPort()) {
            System.out.println("No se pudo abrir el puerto.");
            return;
        }

        Scanner scanner = new Scanner(puerto.getInputStream());

        String url = "jdbc:mysql://localhost:3306/tinaco";
        String user = "root";
        String pass = ""; // Cambia esto

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Conectado a la base de datos.");

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split(",");

                if (datos.length == 5) {
                    int nivel = Integer.parseInt(datos[0]);
                    float abast = Float.parseFloat(datos[1]);
                    float cocina = Float.parseFloat(datos[2]);
                    float jardin = Float.parseFloat(datos[3]);
                    float bano = Float.parseFloat(datos[4]);

                    guardarEnBD(conn, nivel, abast, cocina, jardin, bano);
                    System.out.println("Insertado: " + linea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
        puerto.closePort();
    }

    public static void guardarEnBD(Connection conn, int nivel, float abast, float cocina, float jardin, float bano) {
        String sql = "INSERT INTO lecturas_completas (fecha, hora, nivel_cm, flujo_abastecimiento, flujo_cocina, flujo_jardin, flujo_bano, id_usuario, id_tinaco) "
                + "VALUES (CURDATE(), CURTIME(), ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nivel);
            ps.setFloat(2, abast);
            ps.setFloat(3, cocina);
            ps.setFloat(4, jardin);
            ps.setFloat(5, bano);
            ps.setInt(6, 1); // id_usuario
            ps.setInt(7, 6); // id_tinaco
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
