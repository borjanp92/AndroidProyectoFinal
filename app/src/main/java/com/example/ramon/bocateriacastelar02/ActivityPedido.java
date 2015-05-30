package com.example.ramon.bocateriacastelar02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;


public class ActivityPedido extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);



    }

    public void clickSeleccionarBebida(View view){

        startActivity(new Intent(this,ActivityBebidas.class));
    }

    public void clickSeleccionarComida(View view){

        startActivity(new Intent(this,ActivityComidas.class));
    }

    public void clickSeleccionarPostre(View view){

        startActivity(new Intent(this,ActivityPostres.class));
    }

    public void clicVolver(View view){

        startActivity(new Intent(this,MainActivity.class));

    }

    public void clickVerPedido(View view){

        startActivity(new Intent(this,ActivityListadoPedidos.class));

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
}
