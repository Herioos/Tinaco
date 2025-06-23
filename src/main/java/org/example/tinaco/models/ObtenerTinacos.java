package org.example.tinaco.models;

public class ObtenerTinacos {
    private int id_tinaco;
    private String nombreTinaco;
    private int capacidadT;
    private int id_s;
    private String nombre_s;
    private int id_usuario;

    public ObtenerTinacos(int id_tinaco,String nombreTinaco,int capacidadT,int id_s,String nombre_s,int id_usuario){
        this.id_tinaco = id_tinaco;
        this.nombreTinaco = nombreTinaco;
        this.capacidadT = capacidadT;
        this.id_s = id_s;
        this.nombre_s = nombre_s;
        this.id_usuario = id_usuario;
    }
    public ObtenerTinacos(){
        //CONSTRUCTOR
    }
    //getter y setters
    public int getId_tinaco() {
        return id_tinaco;
    }
    public void setId_tinaco(int id_tinaco) {
        this.id_tinaco = id_tinaco;
    }
    // NOMBRE DEL TINACO
    public String getNombreTinaco() {
        return nombreTinaco;
    }
    public void setNombreTinaco(String nombreTinaco) {
        this.nombreTinaco = nombreTinaco;
    }
    // CAPACIDAD
    public int getCapacidadT() {
        return capacidadT;
    }
    public void setCapacidadT(int capacidadT) {
        this.capacidadT = capacidadT;
    }
    // ID SENSOR
    public int getId_s() {
        return id_s;
    }
    public void setId_s(int id_s) {
        this.id_s = id_s;
    }
    // NOMBRE DEL SENSOR
    public String getNombre_s() {
        return nombre_s;
    }
    public void setNombre_s(String nombre_s) {
        this.nombre_s = nombre_s;
    }
    // ID DEL USUARIO
    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @Override
    public String toString() {
        return nombreTinaco; // Lo que se mostrara en el ComboBox
    }
}
