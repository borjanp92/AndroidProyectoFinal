package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

    public List<Pedido> listadoPedidoPorMesa (Integer mesa){
        SQLiteDatabase bd = getReadableDatabase();
        List<Pedido> listaPedidos = new ArrayList<Pedido>();
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
