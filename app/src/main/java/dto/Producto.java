package dto;

/**
 * Created by Borja on 30/05/2015.
 */
public class Producto {
    private Integer id;
    private String codArticulo;
    private String nombre;
    private Double pvp;
    private String nombreFoto;

    public Producto() {
        this(null, null, null, null, null);
    }

    public Producto(Integer id, String codArticulo, String nombre, Double pvp, String nombreFoto) {
        this.id = id;
        this.codArticulo = codArticulo;
        this.nombre = nombre;
        this.pvp = pvp;
        this.nombreFoto = nombreFoto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPvp() {
        return pvp;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    @Override
    public String toString() {
        return
                codArticulo + ":" + nombre + ":" + pvp;
    }
}
