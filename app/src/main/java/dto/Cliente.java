package dto;

/**
 * Created by RAMON on 30/05/2015.
 */
public class Cliente {

    private String nif;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String codUsuario;
    private String clave;
    private Integer activo;

    public Cliente (){

    }
    public Cliente(String nif, String nombre, String apellidos, String telefono, String codUsuario, String clave, Integer activo) {
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.codUsuario = codUsuario;
        this.clave = clave;
        this.activo = activo;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
