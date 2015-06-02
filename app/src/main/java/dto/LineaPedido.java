package dto;

/**
 * Created by Borja on 02/06/2015.
 */
public class LineaPedido {

    private Integer unidades;
    private Double total;
    private String nombre;

    public LineaPedido() {
    }

    public LineaPedido(Integer unidades, Double total) {
        this.unidades = unidades;
        this.total = total;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getUnidades() {
        return this.unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }
    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return nombre+":"+unidades+":"+total;
    }
}
