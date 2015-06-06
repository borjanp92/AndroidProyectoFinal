package com.example.ramon.bocateriacastelar02;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dao.SingletonCodUsuario;
import dto.Cliente;
import dao.Conexion;


public class Registro extends ActionBarActivity {

    private EditText etNombre, etApellidos, etNif, etTelefono, etIdUsuario, etClave;
    private Conexion conexion;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        inicializarComponentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes(){
        etNombre = (EditText)findViewById(R.id.editTextNombre);
        etApellidos =(EditText)findViewById(R.id.editTextApellidos);
        etNif = (EditText)findViewById(R.id.editTextNif);
        etTelefono = (EditText)findViewById(R.id.editTextTelefono);
        etIdUsuario = (EditText)findViewById(R.id.editTextIdUsuario);
        etClave = (EditText)findViewById(R.id.editTextClave);
        conexion = new Conexion(this,"conexion",null,1);
        cliente = new Cliente();
    }

    public void clickRegistrar(View view){
        if(conexion.getClientePorNif(etNif.getText().toString()) != null){
            Toast.makeText(getApplicationContext(),"El usuario con nif : " + etNif.getText().toString() + " ya existe", Toast.LENGTH_SHORT).show();
        }else if(conexion.getClientePorIdUsuario(etIdUsuario.getText().toString()) !=null) {
            Toast.makeText(getApplicationContext(),"Ese Id usuario ya existe", Toast.LENGTH_SHORT).show();
        }else{
            cliente.setNombre(etNombre.getText().toString());
            cliente.setApellidos(etApellidos.getText().toString());
            cliente.setNif(etNif.getText().toString());
            cliente.setTelefono(etTelefono.getText().toString());
            cliente.setCodUsuario(etIdUsuario.getText().toString());
            cliente.setClave(etClave.getText().toString());
            cliente.setActivo(1);
            conexion.addCliente(cliente);
            SingletonCodUsuario.getInstance().setCodUsuario(cliente.getCodUsuario());
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

    }
}
