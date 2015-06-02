package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dao.Conexion;
import dto.*;
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
    private Double total;
    final static String COD_ENVIO_PAG="PAGA";
    final static String COD_ENVIO_DEL="DEL";
    final static String COD_ENVIO="LISTPED";
    final static String LOGCAT = "ALTA";
    private String ipServidor;
    private int puerto;
    private Socket socket;
    private ComidaTask listadoPedidosTask;
    private DataInputStream dis;
    private DataOutputStream dos;
    private List<LineaPedido> lineasPedidos;
    private LineaPedido lineaPedido;

    private void inicializa() {
        ipServidor = "192.168.60.10";
        puerto = 40001;
    }
    private void enviarDato(){
        try {
            dos.writeUTF(COD_ENVIO+":"+SingletonMesa.getInstance().getMesa()+":x");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
        inicializa();
        listadoPedidosTask = new ComidaTask();
        listadoPedidosTask.execute();
        conexion = new Conexion(this, "conexion", null, 1);
        total = 0.0;
        listViewPedido = (ListView) findViewById(R.id.listViewPedido);
        tvTotal = (TextView)findViewById(R.id.tvTotal);

        listViewPedido.setAdapter(new ArrayAdapter<LineaPedido>(getApplicationContext(), android.R.layout.simple_list_item_1, lineasPedidos));
        listViewPedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pedidoElegido = (Pedido) parent.getItemAtPosition(position);
                borrarPedido();
                recargarLista();
            }
        });

        tvTotal.setText("Total del Pedido : " + calcularTotalPedido() + " €");


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
                try {
                    dos.writeUTF(COD_ENVIO+":"+SingletonMesa.getInstance().getMesa()+":IDCLIENTE"+":"+pedidoElegido.getNombreProducto());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                conexion.deleteProducto(pedidoElegido.getId());
                tvTotal.setText("Total del Pedido : " + calcularTotalPedido() + " €");
                recargarLista();



            }
        });

        // El listener lo ponemos a null debido a que no hace nada:
        builder.setNegativeButton(activity.getString(R.string.cancelar), null);

        return builder.create();
    }

    public Dialog dialogoPedirCuenta(final Activity activity) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);

        builder.setMessage(activity.getString(R.string.confirmarPedirCuenta));
        builder.setTitle(activity.getString(R.string.TituloMensajeDialog));
        builder.setPositiveButton(activity.getString(R.string.aceptar), new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getApplicationContext(),"El total de pedido es :" + calcularTotalPedido() + "€", Toast.LENGTH_SHORT).show();
                conexion.delete(SingletonMesa.getInstance().getMesa());
                tvTotal.setText("Total del Pedido : " + calcularTotalPedido() + " €");
                try {
                    dos.writeUTF(COD_ENVIO_PAG + ":" + SingletonMesa.getInstance().getMesa() + ":IDCLIENTE" + ":"+total);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                recargarLista();



            }
        });

        // El listener lo ponemos a null debido a que no hace nada:
        builder.setNegativeButton(activity.getString(R.string.cancelar), null);

        return builder.create();
    }

    private Double calcularTotalPedido(){
        total = 0.0;


        Iterator<LineaPedido> it =lineasPedidos.iterator();
        while (it.hasNext()){
            total+=it.next().getTotal();
        }

        return total;
    }

    public void clickPagar (View view){
        dialogoPedirCuenta(this).show();
    }

    public void borrarPedido(){

        dialogoEliminarProducto(this).show();

    }

    public void recargarLista() {
        listViewPedido.setAdapter(new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, conexion.listadoPedidoPorMesa(SingletonMesa.getInstance().getMesa())));
        listViewPedido.invalidateViews();

    }

    public class ComidaTask extends AsyncTask<Void, String, Void> {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(ipServidor, puerto);
                publishProgress("Conectado con el servidor");
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                enviarDato();
                while (puerto != 1) {
                    String msg = dis.readUTF();
                    //publishProgress(msg);
                    String cadenaDatos[] = msg.split(":");
                    while (cadenaDatos[0].equalsIgnoreCase("3")) {
                        lineaPedido=new LineaPedido();
                        lineasPedidos.add(lineaPedido);
                        msg = dis.readUTF();
                        cadenaDatos= msg.split(":");
                    }

                }
                recargarLista();
                dis.close();
                dos.flush();
                dos.close();
                socket.close();
            } catch (IOException e) {
                Log.e(LOGCAT, "No se pudo conectar");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }



}




