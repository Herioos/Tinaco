package org.example.tinaco.models;

public class Usuarios {
    private String nombreC;
    private String contrasena;
    private String recuperar;
    private String nombreU;
    private String apellidoP;
    private String telefono;

    public Usuarios(){
    }
    public Usuarios(String nombreC,String contrasena,String recuperar,String nombreU,
                    String apellidoP,String telefono){
        this.nombreC = nombreC;
        this.contrasena = contrasena;
        this.recuperar = recuperar;
        this.nombreU = nombreU;
        this.apellidoP = apellidoP;
        this.telefono = telefono;
    }
    //nombre usuario
    public String getNombreU() {
        return nombreU;
    }
    public void setNombre(String nombre) {
        this.nombreU = nombre;
    }
    //nombre cuenta
    public String getNombreC(){
        return  nombreC;
    }
    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }
    //apellido paterno
    public String getApellidoP() {
        return apellidoP;
    }
    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }
    //telefono
    public String getTelefono() {
        return telefono;
    }
    public void setTelefo(String telefo) {
        this.telefono = telefo;
    }
    //contraseña
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    //recuperar contraseña
    public String getRecuperar() {
        return recuperar;
    }
    public void setRecuperar(String recuperar) {
        this.recuperar = recuperar;
    }
}
