package dto;

/**
 * Created by Borja on 30/05/2015.
 */
public class Factura {
    
    private Integer mesa;
    private Double total;

    public Factura(){
        this(null,null,null);
    }

    public Factura(Integer id, Integer mesa, Double total) {
        this.mesa = mesa;
        this.total = total;
    }



    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
