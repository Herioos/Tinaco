package org.example.tinaco.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tinaco.MainApplication;
import org.example.tinaco.models.*;
import org.example.tinaco.models.TinacoDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class crearGestionControllers implements Initializable {
    private ObtenerUsuarios usuariosCg;
    @FXML private Button botonAtrasCrearGestion;
    @FXML private Button botonAgregarGestion;
    @FXML private Button botonMenosMaximo;
    @FXML private Button botonMasMaximo;
    @FXML private Button botonMenosMinimo;
    @FXML private Button botonMasMinimo;
    @FXML private TextField textNombreGestion;
    @FXML private TextField textNivelMaximo;
    @FXML private TextField textNivelMinimo;
    @FXML private ComboBox<ObtenerTinacos> comboTinaco;
    //CONSTRUCTOR
    public void pasarUsuario(ObtenerUsuarios usuarios){
        this.usuariosCg = usuarios;
        // Llenar comboSensor con sensores del usuario
        TinacoDAO dao = new TinacoDAO();
        try {
            List<ObtenerTinacos> tinacos = dao.listaTinacoPorUsuario(usuarios.getId_usuario());///-----------------
            comboTinaco.getItems().clear(); // Por si se llama más de una vez
            comboTinaco.getItems().addAll(tinacos);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //ATRAS --- NO TOCAR
    @FXML
    public void atrasCrearGestionClick(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Stage stage = (Stage) botonAtrasCrearGestion.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/gestion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        gestionControllers gestion = fxmlLoader.getController();
        gestion.pasarUsuario(usuariosCg);
        gestion.cargarDatos();
        stage.setTitle("Gestion de agua ( "+usuariosCg.getNombreU()+" )");
        stage.setScene(scene);
    }
    //
    @FXML
    public void agregarGestionClick(ActionEvent actionEvent) throws ClassNotFoundException, IOException {

        if(textNombreGestion.getText().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto el nombre de la gestion");
            alert2.show();
            return;
        }
        if(textNombreGestion.getText().length() >=16){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR en nombre de gestion");
            alert2.setContentText("Verifique que no exceda la cantidad max de caracteres");
            alert2.show();
            return;
        }
        if(comboTinaco.getItems().isEmpty()){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("ERROR");
            alert2.setContentText("Verifique que haya puesto el tinaco");
            alert2.show();
            return;
        }
        //confirmar registro
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("¿Desea guardar?");
        alert.setContentText("Nombre de la gestion : "+textNombreGestion.getText()+"\nNombre del tinaco : "+comboTinaco.getValue().getNombreTinaco()+
                "\nCapacidad maxima : "+textNivelMaximo.getText()+"\nCapacidad minima : "+textNivelMinimo);
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get().equals(ButtonType.OK)){
            //obtener valores de la gestion
            String nombreGestion = textNombreGestion.getText();
            ObtenerTinacos obtenerTinacos = comboTinaco.getValue();
            String nombreTinaco = obtenerTinacos.getNombreTinaco();
            int cantidadMax = Integer.parseInt(textNivelMaximo.getId());
            int cantidadMin = Integer.parseInt(textNivelMinimo.getId());

            //CONEXION
            //insert de los datos en la tabla sensores
            String url = "jdbc:mysql://localhost:3306/";
            String bd = "tinaco";
            String usuario = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection connection = DriverManager.getConnection(url + bd, usuario, password);
                System.out.println("Conexion exitosa");
                // Insertar
                String tabla_u = ("INSERT INTO tabla_gestiones (nombre_g,nivelMaximo,nivelMinimo,nombre_t,id_usuario) \n" +
                        "VALUES (?,?,?,?,?)");
                PreparedStatement preparedStatement = connection.prepareStatement(tabla_u);
                preparedStatement.setString(1, nombreGestion);
                preparedStatement.setInt(2,cantidadMax);
                preparedStatement.setInt(3,cantidadMin);
                preparedStatement.setString(4,nombreTinaco);
                preparedStatement.setInt(5,usuariosCg.getId_usuario());
                preparedStatement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }// termina el insert
            System.out.println("gestion añadida \n");

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Aviso");
            alert2.setHeaderText("Se añadio correctamente");
            alert2.setContentText("... ");
            alert2.show();
            // ABRIR ECENA DE NEW FX
            Stage stage = (Stage) botonAgregarGestion.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/crear_gestion.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

        }

    }
    @FXML
    public void comboTinacoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void menosMaximoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void masMaximoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void masMinimoClick(ActionEvent actionEvent) {
    }
    @FXML
    public void menosMinimoClick(ActionEvent actionEvent) {
    }
    //INICIANDO EL PROGRAMA
    public void initialize(URL url, ResourceBundle resourceBundle){
        TinacoDAO dao = new TinacoDAO();
        try {
            List<ObtenerTinacos> tinacos = dao.listaTinacoPorUsuario(1);
            comboTinaco.getItems().addAll(tinacos); // la agregamos al ComboBox
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
