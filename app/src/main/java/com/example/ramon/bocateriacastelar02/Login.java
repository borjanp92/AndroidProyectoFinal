package com.example.ramon.bocateriacastelar02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dto.Cliente;
import dao.Conexion;


public class Login extends ActionBarActivity {

    private EditText etIdUsuario, etClave;
    private Conexion conexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarComponentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    public void clickRegistrarse(View view){
        startActivity(new Intent(this,Registro.class));
    }

    public void clickIniciar (View view){
        Cliente cliente = conexion.getCliente(etIdUsuario.getText().toString(),etClave.getText().toString());
        if (cliente != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Usuario incorrecto",Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarComponentes(){
        etIdUsuario = (EditText)findViewById(R.id.etIdUsuarioLogin);
        etClave = (EditText)findViewById(R.id.etPasswordLogin);
        conexion = new Conexion(this,"conexion",null,1);
    }
}
