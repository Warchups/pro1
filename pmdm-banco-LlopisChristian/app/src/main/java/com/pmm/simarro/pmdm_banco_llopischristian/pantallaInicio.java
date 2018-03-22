package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;

public class pantallaInicio extends AppCompatActivity {

    private TextView bienvenido;
    private ImageButton cerrarSesion;
    private ImageButton transferencias;
    private ImageButton cajerosCercanos;
    private Cliente cliente = new Cliente();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);////////////

        bienvenido = (TextView) findViewById(R.id.bienvenido);
        cerrarSesion = (ImageButton) findViewById(R.id.cerrarSesion);
        transferencias = (ImageButton) findViewById(R.id.transferencias);
        cajerosCercanos = (ImageButton) findViewById(R.id.cajerosCercanos);

        cliente = (Cliente) this.getIntent().getExtras().getSerializable("CLIENTE");

        prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        String saludo = (String) prefs.getString("saludo", "");

        if (saludo.length() > 0) {
            bienvenido.setText(saludo);
        }else {
            bienvenido.setText("Bienvenido "+cliente.getNombre());
        }

    }

    public void cerrarSesion(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Exit me", true);
        startActivity(intent);
    }

    public void cambiarClave(View v) {
        Intent intent = new Intent(pantallaInicio.this, CambiarClave.class);

        intent.putExtra("CLIENTE", cliente);

        startActivity(intent);
    }

    public void transferencias(View v) {
        Intent intent = new Intent(this, com.pmm.simarro.pmdm_banco_llopischristian.transferencias.class);

        intent.putExtra("CLIENTE", cliente);

        startActivity(intent);
    }

    public void posicionGlobal(View v) {
        Intent intent = new Intent(pantallaInicio.this, PosicionGlobal.class);

        intent.putExtra("CLIENTE", cliente);

        startActivity(intent);
    }

    public void configuracion(View v) {
        Intent intent = new Intent(pantallaInicio.this, Configuracion.class);

        intent.putExtra("CLIENTE", cliente);

        startActivity(intent);
    }

    public void confCajeros(View v) {
        Intent intent = new Intent(pantallaInicio.this, GestionCajeros.class);

        intent.putExtra("CLIENTE", cliente);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_posicionGlobal:
                posicionGlobal(getCurrentFocus());
                return true;
            case R.id.action_cajerosCercanos:
                confCajeros(getCurrentFocus());
                return true;
            case R.id.action_ingresos:

                return true;
            case R.id.action_tranferencias:
                transferencias(getCurrentFocus());
                return true;
            case R.id.action_cambiarClave:
                cambiarClave(getCurrentFocus());
                return true;
            case R.id.action_configuracion:
                configuracion(getCurrentFocus());
                return true;
            case R.id.action_cerrarSesion:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
