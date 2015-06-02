package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import dto.Cliente;
import dto.Pedido;

/**
 * Created by RAMON on 05/01/2015.
 */
public class Conexion extends SQLiteOpenHelper{

    private final static String NOMBRE_BD= "bocateria_castelar.db";
    private final static Integer VERSION_BD=1;

    public Conexion(Context contexto,String nombre,SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, NOMBRE_BD, factory, VERSION_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE IF NOT EXISTS Pedidos (id INTEGER PRIMARY KEY AUTOINCREMENT, mesa INTEGER, nombre VARCHAR(30), precio DOUBLE, totalPedido DOUBLE);";
        db.execSQL(sql);
        /*sql = "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, cod-usuario VARCHAR(10), clave VARCHAR(50), activo boolean);";
        db.execSQL(sql);*/
        sql = "CREATE TABLE IF NOT EXISTS clientes (id INTEGER PRIMARY KEY AUTOINCREMENT, nif VARCHAR(10), nombre VARCHAR(20), apellidos VARCHAR(60), telefono VARCHAR(15), id_usuario VARCHAR(20), clave VARCHAR(20), activo INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public boolean addPedido(Pedido pedido){
        boolean aniadido = true;
        SQLiteDatabase db = getWritableDatabase();

        String sql ="INSERT INTO Pedidos (mesa, nombre, precio) VALUES ( " +
                  pedido.getMesa() + ",'" + pedido.getNombreProducto() + "'," + pedido.getPrecio() + ");";

        try {
            db.execSQL(sql);
        } catch (Exception ex){
            aniadido = false;
        }

        db.close();

        return aniadido;
    }

    public boolean addCliente(Cliente cliente){
        boolean aniadido = true;
        SQLiteDatabase db = getWritableDatabase();

        String sql ="INSERT INTO clientes (nif, nombre, apellidos, telefono, id_usuario, clave, activo) VALUES ( '" +
                cliente.getNif() + "','" + cliente.getNombre() + "','" + cliente.getApellidos() + "','" + cliente.getTelefono() + "','"
                + cliente.getIdUsuario() +"','" + cliente.getClave() + "'," + cliente.getActivo() + ");";

        try {
            db.execSQL(sql);
        } catch (Exception ex){
            aniadido = false;
        }

        db.close();

        return aniadido;
    }

    public Cliente getCliente(String idUsuario, String clave){
        SQLiteDatabase bd = getReadableDatabase();
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id_usuario ='" + idUsuario + "' AND clave='" + clave + "';";
        Cursor cursor = bd.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cliente = new Cliente();
            cliente.setNif(cursor.getString(cursor.getColumnIndex("nif")));
            cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            cliente.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
            cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
            cliente.setIdUsuario(cursor.getString(cursor.getColumnIndex("id_usuario")));
            cliente.setClave(cursor.getString(cursor.getColumnIndex("clave")));
            cliente.setActivo(cursor.getInt(cursor.getColumnIndex("activo")));
        }
        bd.close();
        return cliente;
    }

    public Cliente getClientePorNif(String nif){
        SQLiteDatabase bd = getReadableDatabase();
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE nif ='" + nif + "';";
        Cursor cursor = bd.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cliente = new Cliente();
            cliente.setNif(cursor.getString(cursor.getColumnIndex("nif")));
            cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            cliente.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
            cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
            cliente.setIdUsuario(cursor.getString(cursor.getColumnIndex("id_usuario")));
            cliente.setClave(cursor.getString(cursor.getColumnIndex("clave")));
            cliente.setActivo(cursor.getInt(cursor.getColumnIndex("activo")));
        }
        bd.close();
        return cliente;
    }

    public Cliente getClientePorIdUsuario(String idUsuario){
        SQLiteDatabase bd = getReadableDatabase();
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id_usuario ='" + idUsuario + "';";
        Cursor cursor = bd.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cliente = new Cliente();
            cliente.setNif(cursor.getString(cursor.getColumnIndex("nif")));
            cliente.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
            cliente.setApellidos(cursor.getString(cursor.getColumnIndex("apellidos")));
            cliente.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
            cliente.setIdUsuario(cursor.getString(cursor.getColumnIndex("id_usuario")));
            cliente.setClave(cursor.getString(cursor.getColumnIndex("clave")));
            cliente.setActivo(cursor.getInt(cursor.getColumnIndex("activo")));
        }
        bd.close();
        return cliente;
    }

    public List<Pedido> listadoPedidoPorMesa (Integer mesa){
        SQLiteDatabase bd = getReadableDatabase();
        List<Pedido> listaPedidos = new ArrayList();
        Double total = 0.00;
        String sql = "SELECT id,nombre,precio FROM Pedidos WHERE mesa =" + mesa;

        Cursor cursor = bd.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                total+= cursor.getDouble(2);
                listaPedidos.add(new Pedido(cursor.getInt(0),mesa,cursor.getString(1),cursor.getDouble(2), total));
            }while(cursor.moveToNext());
        }
        bd.close();

        return listaPedidos;
    }

   /* public boolean updatePedido(Pedido pedido){
        boolean actualizado = true;
        SQLiteDatabase db = getWritableDatabase();

        String sql = "UPDATE pedidos set cantidad = '"+ pedido.getCantidad() + "' WHERE nombre = '"+ pedido.getNombre() + "' " +
                "AND mesa = '" + pedido.getMesa() + "';";

        try {
            db.execSQL(sql);
        } catch (Exception ex){
            actualizado = false;
        }

        db.close();


        return actualizado;
    }*/

    public boolean delete(Integer mesa ){
        SQLiteDatabase bd=getWritableDatabase();
        int num= bd.delete("Pedidos","mesa='"+mesa+"'",null);

        bd.close();

        return (num>0);
    }

    public boolean deleteProducto(Integer id){
        SQLiteDatabase bd=getWritableDatabase();
        int num= bd.delete("Pedidos","id="+id,null);
        bd.close();

        return (num>0);


    }

//    public Double getTotalPedido(Integer mesa){
//        Double totalPedido = null;
//        SQLiteDatabase bd = getReadableDatabase();
//        String sql = "SELECT totalPedido FROM Pedidos WHERE mesa ='" + mesa +"';";
//        totalPedido = Double.valueOf(sql);
//        return totalPedido;
//    }






}
