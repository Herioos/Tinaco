package org.example.tinaco.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaGestion {
    private final SimpleIntegerProperty numeroG;
    private final SimpleStringProperty nombreG;
    private final SimpleIntegerProperty limiteMax;
    private final SimpleIntegerProperty limiteMin;
    private final SimpleStringProperty nombreT;

    public TablaGestion(int numeroG,String nombreG,int limiteMax,int limiteMin,String nombreT){
        this.numeroG = new SimpleIntegerProperty(numeroG);
        this.nombreG = new SimpleStringProperty(nombreG);
        this.limiteMax = new SimpleIntegerProperty(limiteMax);
        this.limiteMin = new SimpleIntegerProperty(limiteMin);
        this.nombreT = new SimpleStringProperty(nombreT);
    }

    public int getNumeroG() {
        return numeroG.get();
    }
    public String getNombreG() {
        return nombreG.get();
    }
    public int getLimiteMax() {
        return limiteMax.get();
    }
    public int getLimiteMin() {
        return limiteMin.get();
    }
    public String getNombreT() {
        return nombreT.get();
    }
    //
    public  void setNumeroG(int numeroG){
        this.numeroG.set(numeroG);
    }
    public void setNombreG(String nombreG){
        this.nombreG.set(nombreG);
    }
    public void setLimiteMax(int limiteMax){
        this.limiteMax.set(limiteMax);
    }
    public void  setLimiteMin(int limiteMin){
        this.limiteMin.set(limiteMin);
    }
    public void setNombreT(String nombreT){
        this.nombreT.set(nombreT);
    }
}
