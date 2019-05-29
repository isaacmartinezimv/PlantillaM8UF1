package com.example.isaac.plantillam8uf1;

public class HacerReservaModel {
    private String fecha;
    private String comensales;
    private String nombre;
    private String telefono;
    private String comentarios;

    public HacerReservaModel() {
    }

    public HacerReservaModel(String fecha, String comensales, String nombre, String telefono, String comentarios) {
        this.fecha = fecha;
        this.comensales = comensales;
        this.nombre = nombre;
        this.telefono = telefono;
        this.comentarios = comentarios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComensales() {
        return comensales;
    }

    public void setComensales(String comensales) {
        this.comensales = comensales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
