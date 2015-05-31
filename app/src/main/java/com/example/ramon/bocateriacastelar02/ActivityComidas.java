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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dao.SingletonMesa;
import dto.Producto;

public class ActivityComidas extends Activity {

    final static String COD_ENVIO="NEWPROD";
    final static String LOGCAT = "ALTA";
    private String ipServidor;
    private int puerto;
    private Socket socket;
    private ComidaTask comidaTask;
    private DataInputStream dis;
    private DataOutputStream dos;
    private List<Producto> productos;

    private void inicializa() {
        ipServidor = "192.168.60.10";
        puerto = 40001;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos);
        inicializa();
        comidaTask = new ComidaTask();
        comidaTask.execute();
        crearAdapterComidas();
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

    private List<Producto> obtenerComidas() {

        String[] codComidas = getResources().getStringArray(R.array.codProductoComida);
        String[] nombres = getResources().getStringArray(R.array.comidasDescripcion);
        String[] precios = getResources().getStringArray(R.array.comidasPrecio);
        String[] fotos = getResources().getStringArray(R.array.comidasNombreFoto);
        productos = new ArrayList<Producto>();

        for (int i = 0; i < nombres.length; i++) {
            productos.add(new Producto(i, codComidas[i], nombres[i], Double.parseDouble(precios[i]), fotos[i]));

        }
        return productos;
    }

    private void crearAdapterComidas() {
        ListView lv = (ListView) findViewById(R.id.lvListaProductos);
        productos = obtenerComidas();
        ProductoAdapter adapter = new ProductoAdapter(this, productos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto productoElegido = (Producto) parent.getItemAtPosition(position);
                String texto = "Has elegido: " + productoElegido.getNombre();
                Toast toast = Toast.makeText(ActivityComidas.this, texto, Toast.LENGTH_SHORT);
                toast.show();

                String sendText=COD_ENVIO+":"+SingletonMesa.getInstance().getMesa()+":"+productoElegido.toString();
                try {
                    dos.writeUTF(sendText);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
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
                while (puerto != 1) {
                    String msg = dis.readUTF();
                    publishProgress(msg);
                }
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
