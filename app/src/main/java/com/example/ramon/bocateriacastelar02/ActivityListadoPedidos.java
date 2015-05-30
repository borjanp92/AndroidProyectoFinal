package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import dao.Conexion;
import dto.Pedido;
import dao.SingletonMesa;

import android.content.DialogInterface.OnClickListener;


/**
 * Created by RAMON on 24/01/2015.
 */
public class ActivityListadoPedidos extends Activity {
    private ListView listViewPedido;
    private TextView tvTotal;
    private Conexion conexion;
    private Pedido pedidoElegido;

    public Pedido getPedidoElegido() {
        return pedidoElegido;
    }

    public void setPedidoElegido(Pedido pedidoElegido) {
        this.pedidoElegido = pedidoElegido;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_pedido);
        conexion = new Conexion(this, "conexion", null, 1);
        listViewPedido = (ListView) findViewById(R.id.listViewPedido);
        tvTotal = (TextView)findViewById(R.id.tvTotal);

        listViewPedido.setAdapter(new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, conexion.listadoPedidoPorMesa(SingletonMesa.getInstance().getMesa())));
        listViewPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pedidoElegido = (Pedido) parent.getItemAtPosition(position);
                borrarPedido();
                recargarLista();
            }
        });


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

    public Dialog dialogoEliminarProducto(final Activity activity) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);

        builder.setMessage(activity.getString(R.string.confirmacionEliminacionProducto));
        builder.setTitle(activity.getString(R.string.TituloMensajeDialog));
        builder.setPositiveButton(activity.getString(R.string.aceptar), new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                  conexion.deleteProducto(pedidoElegido.getId());
                  recargarLista();



            }
        });

        // El listener lo ponemos a null debido a que no hace nada:
        builder.setNegativeButton(activity.getString(R.string.cancelar), null);

        return builder.create();
    }

    public void clickPagar (View view){
        List<Pedido> pedidos= conexion.listadoPedidoPorMesa(SingletonMesa.getInstance().getMesa());

        Iterator<Pedido> it =pedidos.iterator();
        Double total = null;
        while (it.hasNext()){
            total+=it.next().getPrecio();
        }

        if(total==null){
            Toast.makeText(this,"Esta mesa no tiene Pedidos",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"El total del pedido de la mesa "+ SingletonMesa.getInstance().getMesa() + "es : " + total.toString() +"€",Toast.LENGTH_LONG).show();
        }

    }

    public void borrarPedido(){

        dialogoEliminarProducto(this).show();
    }

    public void recargarLista(){
        listViewPedido.setAdapter(new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, conexion.listadoPedidoPorMesa(SingletonMesa.getInstance().getMesa())));
        listViewPedido.invalidateViews();
    }



}




