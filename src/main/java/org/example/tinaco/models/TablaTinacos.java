package org.example.tinaco.models;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaTinacos {
    private final SimpleIntegerProperty numeroTinaco;
    private final SimpleStringProperty nombreTinaco;
    private final SimpleIntegerProperty capacidadTinaco;
    private final SimpleIntegerProperty numeroSensor;
    private final SimpleStringProperty nombreSensor;

    public TablaTinacos(int numeroTinaco, String nombreTinaco,int capacidadTinaco,int numeroSensor, String nombreSensor) {
        this.numeroTinaco = new SimpleIntegerProperty(numeroTinaco);
        this.nombreTinaco = new SimpleStringProperty(nombreTinaco);
        this.capacidadTinaco = new SimpleIntegerProperty(capacidadTinaco);
        this.numeroSensor = new SimpleIntegerProperty(numeroSensor);
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
    // numero sensor
    public int getNumeroSensor() {
        return numeroSensor.get();
    }
    public void setNumeroSensor(int numeroSensor){
        this.numeroSensor.set(numeroSensor);
    }
    //nombre sensor
    public String getNombreSensor() {
        return nombreSensor.get();
    }
    public void setNombreSensor(String nombreSensor){
        this.nombreSensor.set(nombreSensor);
    }
}
