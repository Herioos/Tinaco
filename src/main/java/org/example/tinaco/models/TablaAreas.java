package org.example.tinaco.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaAreas {
    private final SimpleIntegerProperty num_a;
    private final SimpleStringProperty nom_a;
    private final SimpleStringProperty nom_s;

    public TablaAreas(SimpleIntegerProperty numA, SimpleStringProperty nomA, SimpleStringProperty nomS) {
        num_a = numA;
        nom_a = nomA;
        nom_s = nomS;
    }

    public int getNum_a() {
        return num_a.get();
    }
    public String getNom_a() {
        return nom_a.get();
    }
    public String getNom_s() {
        return nom_s.get();
    }
    //
    public void setNum_a(int num_a){
        this.num_a.set(num_a);
    }
    public void setNom_a(String nom_a){
        this.nom_a.set(nom_a);
    }
    public void setNom_s(String nom_s){
        this.nom_s.set(nom_s);
    }
}
