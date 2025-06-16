package org.example.tinaco.models;

public class ObtenerSensores {
    private int id_sensor;
    private String nombreSensor;

    public ObtenerSensores(int id_sensor, String nombreSensor){
        this.id_sensor = id_sensor;
        this.nombreSensor = nombreSensor;
    }
    public ObtenerSensores(){
        //CONSTRUCTOR
    }

    public int getId_sensor() {
        return id_sensor;
    }
    public void setId_sensor(int id_sensor) {
        this.id_sensor = id_sensor;
    }
    //
    public String getNombreSensor() {
        return nombreSensor;
    }
    public void setNombreSensor(String nombreSensor) {
        this.nombreSensor = nombreSensor;
    }
    @Override
    public String toString() {
        return nombreSensor; // Lo que se mostrara en el ComboBox
    }
}
