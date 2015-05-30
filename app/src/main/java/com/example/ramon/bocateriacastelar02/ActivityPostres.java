package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import dao.Conexion;
import dto.Pedido;
import dao.SingletonMesa;
import dto.Producto;

/**
 * Created by RAMON on 19/01/2015.
 */
public class ActivityPostres extends Activity {
    private Conexion conexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos);
        conexion = new Conexion(this,"conexion",null,1);

        crearAdapterPostres();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Producto> obtenerPostres(){
        String[] descripciones=getResources().getStringArray(R.array.postresDescripcion);
        String[] precios=getResources().getStringArray(R.array.postresPrecio);
        String [] fotos=getResources().getStringArray(R.array.postresNombreFoto);
        ArrayList<Producto> productos=new ArrayList<Producto>();

        for(int i=0;i<descripciones.length;i++){
            productos.add(new Producto(i,descripciones[i],precios[i], fotos[i]));

        }
        return productos;
    }

    private void crearAdapterPostres(){
        ListView lv=(ListView) findViewById(R.id.lvListaProductos);
        ArrayList<Producto> productos=obtenerPostres();
        ProductoAdapter adapter=new ProductoAdapter(this,productos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto productoElegido = (Producto)parent.getItemAtPosition(position);
                String texto = "Has elegido: "+productoElegido.getDescripcion();
                Toast toast = Toast.makeText(ActivityPostres.this, texto, Toast.LENGTH_LONG);
                toast.show();

                Pedido pedido = new Pedido();
                pedido.setMesa(SingletonMesa.getInstance().getMesa());
                pedido.setNombreProducto(productoElegido.getDescripcion());
                pedido.setPrecio(Double.valueOf(productoElegido.getPrecio()));
                conexion.addPedido(pedido);

            }
        });
    }
}
