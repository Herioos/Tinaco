package org.example.tinaco.models;

public class Tinacos {
    private String nomTinaco;
    private String cap;
    private int id_s;
    private int id_u;

    public Tinacos(){}

    public Tinacos(String nomTinaco,String cap,int id_s,int id_u){
        this.nomTinaco = nomTinaco;
        this.cap = cap;
        this.id_s = id_s;
        this.id_u = id_u;
    }

    //nombre de tinaco
    public String getNomTinaco() {
        return nomTinaco;
    }
    public void setNomTinaco(String nomTinaco) {
        this.nomTinaco = nomTinaco;
    }
    //capacidad
    public String getCap() {
        return cap;
    }
    public void setCap(String cap) {
        this.cap = cap;
    }
    //id sensores
    public int getId_s() {
        return id_s;
    }
    public void setId_s(int id_s) {
        this.id_s = id_s;
    }
    //id usuarios
    public int getId_u() {
        return id_u;
    }
    public void setId_u(int id_u) {
        this.id_u = id_u;
    }
}
