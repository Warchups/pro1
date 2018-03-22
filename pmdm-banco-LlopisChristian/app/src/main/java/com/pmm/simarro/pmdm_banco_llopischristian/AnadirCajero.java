package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pmm.simarro.pmdm_banco_llopischristian.bd.Constantes;
import com.pmm.simarro.pmdm_banco_llopischristian.dao.CajeroDAO;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;

public class AnadirCajero extends AppCompatActivity {

    private CajeroDAO cajeroDAO;
    private Cursor cursor;

    private int modo;

    private long id;

    private EditText direccion;
    private EditText latitud;
    private EditText longitud;
    private EditText zoom;
    private Button boton_guardar;
    private Button boton_cancelar;

    private Cliente cliente = new Cliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cajero);

        cliente = (Cliente) this.getIntent().getExtras().getSerializable("CLIENTE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAnadirCajeros);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra == null) return;

        direccion =  (EditText) findViewById(R.id.direccion);
        latitud =  (EditText) findViewById(R.id.latitud);
        longitud =  (EditText) findViewById(R.id.longitud);
        zoom =  (EditText) findViewById(R.id.zoom);
        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        cajeroDAO = new CajeroDAO(this);
        cajeroDAO.abrir();

        if (extra.containsKey(CajeroDAO.C_COLUMNA_ID)) {
            id = extra.getLong(CajeroDAO.C_COLUMNA_ID);
            consultar(id);
        }

        establecerModo(extra.getInt(Constantes.C_MODO));
        //
        // Definimos las acciones para los dos botones
        //
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
        boton_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar();
            }
        });

        if (!cliente.getNombre().equals("Admin")) {
            boton_guardar.setEnabled(false);
        }
    }

    private void borrar(final long id)
    {
        AlertDialog.Builder dialogEliminar = new AlertDialog.Builder(this);
        dialogEliminar.setIcon(android.R.drawable.ic_dialog_alert);
        dialogEliminar.setTitle("Eliminar Cajero");
        dialogEliminar.setMessage("¿Seguro que quieres eliminar el cajero?");
        dialogEliminar.setCancelable(false);
        dialogEliminar.setPositiveButton(getResources().getString(android.R.string.ok), new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int boton) {

                        cajeroDAO.delete(id);

                        Toast.makeText(AnadirCajero.this, "Cajero Eliminado",
                                Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);
                        finish();

                    }
                });
        dialogEliminar.setNegativeButton(android.R.string.no, null);
        dialogEliminar.show();
    }

    private void cancelar() {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    private void guardar() {
        ContentValues reg = new ContentValues();

        if (modo == Constantes.C_EDITAR) {
            reg.put(CajeroDAO.C_COLUMNA_ID, id);
        }

        reg.put(CajeroDAO.C_COLUMNA_DIRECCION, direccion.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_LAT, latitud.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_LNG, longitud.getText().toString());
        reg.put(CajeroDAO.C_COLUMNA_ZOOM, zoom.getText().toString());

        if (modo == Constantes.C_CREAR) {
            cajeroDAO.add(reg);
            Toast.makeText(this, "Cajero Creado",
                    Toast.LENGTH_SHORT).show();
        }else if (modo == Constantes.C_EDITAR) {
            cajeroDAO.update(reg);
            Toast.makeText(this, "Cajero Editado",
                    Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);

        finish();
    }

    private void establecerModo(int m) {
        this.modo = m ;

        if (modo == Constantes.C_VISUALIZAR) {
            this.setTitle(direccion.getText().toString());
            this.setEdicion(false);
        }else if (modo == Constantes.C_CREAR){
            this.setTitle("Cajero Nuevo");
            this.setEdicion(true);
        }else if (modo == Constantes.C_EDITAR) {
            this.setTitle("Editar Cajero");
            this.setEdicion(true);
        }
    }

    private void consultar(long id) {
        cursor = cajeroDAO.getRegistro(id);//////


        direccion.setText(cursor.getString(
                cursor.getColumnIndex(CajeroDAO.C_COLUMNA_DIRECCION)));
        latitud.setText(cursor.getString(
                cursor.getColumnIndex(CajeroDAO.C_COLUMNA_LAT)));
        longitud.setText(cursor.getString(
                cursor.getColumnIndex(CajeroDAO.C_COLUMNA_LNG)));
        zoom.setText(cursor.getString(
                cursor.getColumnIndex(CajeroDAO.C_COLUMNA_ZOOM)));
    }

    public void setEdicion(boolean opcion) {
        direccion.setEnabled(opcion);
        longitud.setEnabled(opcion);
        latitud.setEnabled(opcion);
        zoom.setEnabled(opcion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        if (cliente.getNombre().equals("Admin")) {
            if (modo == Constantes.C_VISUALIZAR)
                getMenuInflater().inflate(R.menu.menu_cajero_ver, menu);
            else
                getMenuInflater().inflate(R.menu.menu_cajero_editar, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_editar:
                establecerModo(Constantes.C_EDITAR);
                return true;

            case R.id.menu_eliminar:
                borrar(id);
                return true;
            case R.id.menu_cancelar:
                cancelar();
                return true;
            case R.id.menu_guardar:
                guardar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
