package org.example.tinaco.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaTinacos {
    private final SimpleIntegerProperty numeroTinaco;
    private final SimpleStringProperty nombreTinaco;
    private final SimpleIntegerProperty capacidadTinaco;
    private final SimpleStringProperty nombreSensor;

    public TablaTinacos(int numeroTinaco, String nombreTinaco,int capacidadTinaco, String nombreSensor) {
        this.numeroTinaco = new SimpleIntegerProperty(numeroTinaco);
        this.nombreTinaco = new SimpleStringProperty(nombreTinaco);
        this.capacidadTinaco = new SimpleIntegerProperty(capacidadTinaco);
        this.nombreSensor = new SimpleStringProperty(nombreSensor);
    }
    //numero tinaco
    public int getNumeroTinaco() {
        return numeroTinaco.get();
    }
    public void setNumeroTinaco(int numeroTinaco){
        this.numeroTinaco.set(numeroTinaco);
    }
    //nombre tinaco
    public String getNombreTinaco() {
        return nombreTinaco.get();
    }
    public void setNombreTinaco(String nombreTinaco){
        this.nombreTinaco.set(nombreTinaco);
    }
    // capacidad
    public int getCapacidadTinaco() {
        return capacidadTinaco.get();
    }
    public void setCapacidadTinaco(int capacidadTinaco){
        this.capacidadTinaco.set(capacidadTinaco);
    }
    //nombre sensor
    public String getNombreSensor() {
        return nombreSensor.get();
    }
    public void setNombreSensor(String nombreSensor){
        this.nombreSensor.set(nombreSensor);
    }
}
