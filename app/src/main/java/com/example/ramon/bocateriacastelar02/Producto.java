package com.example.ramon.bocateriacastelar02;

/**
 * Created by RAMON on 12/01/2015.
 */
public class Producto {
    private long id;
    private String descripcion;
    private String precio;
    private String nombreFoto;

    public Producto() {
    }

    public Producto(long id, String descripcion, String precio, String nombreFoto) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombreFoto = nombreFoto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", nombreFoto='" + nombreFoto + '\'' +
                '}';
    }
}
