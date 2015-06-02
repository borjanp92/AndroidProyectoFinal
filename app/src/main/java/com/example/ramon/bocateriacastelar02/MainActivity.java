package com.example.ramon.bocateriacastelar02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import dao.Conexion;
import dao.SingletonMesa;


public class MainActivity extends ActionBarActivity {

    ImageButton btMesa1, btMesa2, btMesa3, btMesa4, btMesa5, btMesa6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        componentes();

    }

    private void componentes() {
        btMesa1 = (ImageButton)findViewById(R.id.btMesa1);
        btMesa2 = (ImageButton)findViewById(R.id.btMesa2);
        btMesa3 = (ImageButton)findViewById(R.id.btMesa3);
        btMesa4 = (ImageButton)findViewById(R.id.btMesa4);
        btMesa5 = (ImageButton)findViewById(R.id.btMesa5);
        btMesa6 = (ImageButton)findViewById(R.id.btMesa6);
    }

    public void onClick(View view) {
        ImageButton boton = (ImageButton) view;
        switch (boton.getId()) {
            case R.id.btMesa1:
                SingletonMesa.getInstance().setMesa(1);
                break;
            case R.id.btMesa2:
                SingletonMesa.getInstance().setMesa(2);
                break;
            case R.id.btMesa3:
                SingletonMesa.getInstance().setMesa(3);
                break;
            case R.id.btMesa4:
                SingletonMesa.getInstance().setMesa(4);
                break;
            case R.id.btMesa5:
                SingletonMesa.getInstance().setMesa(5);
                break;
            case R.id.btMesa6:
                SingletonMesa.getInstance().setMesa(6);
                break;
        }
        startActivity(new Intent(this,ActivityPedido.class));
    }

    /*public void clickSalir(View view){
        finish();
    }*/


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


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
