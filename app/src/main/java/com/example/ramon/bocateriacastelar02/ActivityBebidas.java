package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dao.SingletonMesa;
import dto.Producto;

public class ActivityBebidas extends Activity {

    final static String COD_ENVIO="NEWPROD";
    final static String LOGCAT = "ALTA";
    private List<Producto> productos;
    private String ipServidor;
    private int puerto;
    private Socket socket;
    private BebidaTask bebidaTask;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private void inicializa() {
        ipServidor = "192.168.60.10";
        puerto = 40001;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos);
        inicializa();
        bebidaTask = new BebidaTask();
        bebidaTask.execute();

        crearAdapterBebidas();
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

    private List<Producto> obtenerBebidas(){
        String[] codsArticulos=getResources().getStringArray(R.array.codProductoBebidas);
        String[] nombres=getResources().getStringArray(R.array.bebidasDescripcion);
        String[] precios=getResources().getStringArray(R.array.bebidasPrecio);
        String [] fotos=getResources().getStringArray(R.array.bebidasNombreFoto);
        productos=new ArrayList<Producto>();

        for(int i=0;i<nombres.length;i++){
            productos.add(new Producto(i,codsArticulos[i],nombres[i],Double.parseDouble(precios[i]), fotos[i]));

        }
        return productos;
    }

    private void crearAdapterBebidas(){
        ListView lv=(ListView) findViewById(R.id.lvListaProductos);
        productos=obtenerBebidas();
        ProductoAdapter adapter=new ProductoAdapter(this,productos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto productoElegido = (Producto)parent.getItemAtPosition(position);
                String texto = "Has elegido: "+productoElegido.getNombre();
                Toast toast = Toast.makeText(ActivityBebidas.this, texto, Toast.LENGTH_SHORT);
                toast.show();

                String sendText= COD_ENVIO+":"+SingletonMesa.getInstance().getMesa()+":"+productoElegido.toString();
                try {
                    oos.writeObject(sendText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public class BebidaTask extends AsyncTask<Void, String, Void> {
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);


        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new Socket(ipServidor, puerto);
                publishProgress("Conectado con el servidor");
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                while (puerto != 1) {
                    String msg = null;
                    try {
                        msg = (String) ois.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    publishProgress(msg);
                }
                ois.close();
                oos.flush();
                oos.close();
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
