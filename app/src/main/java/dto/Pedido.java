package dto;

/**
 * Created by RAMON on 05/01/2015.
 */
public class Pedido {

    private Integer id;
    private Integer mesa;
    private String nombreProducto;
    private Double precio;
    private Double totalPedido;

    public Pedido() {
    }

    public Pedido(Integer mesa, String nombreProducto, Double precio, Double totalPedido) {
        this.mesa = mesa;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.totalPedido = totalPedido;
    }

    public Pedido(Integer id, Integer mesa, String nombreProducto, Double precio, Double totalPedido) {
        this.id = id;
        this.mesa = mesa;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.totalPedido = totalPedido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", mesa=" + mesa +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precio=" + precio +
                ", totalPedido=" + totalPedido +
                '}';
    }
}
