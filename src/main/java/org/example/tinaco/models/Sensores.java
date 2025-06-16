package org.example.tinaco.models;

public class Sensores {
    private String nombreSensor;
    private int id_usuario;

    public Sensores(){}
    public Sensores(String nombreSensor,int id_usuario){
        this.nombreSensor = nombreSensor;
        this.id_usuario = id_usuario;
    }

    // obtener nombre de sensor
    public String getNombreSensor() {
        return nombreSensor;
    }
    public void setNombreSensor(String nombreSensor) {
        this.nombreSensor = nombreSensor;
    }
    // obtener id usuario
    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
