package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pmm.simarro.pmdm_banco_llopischristian.adaptador.CajerosCursorAdapter;
import com.pmm.simarro.pmdm_banco_llopischristian.bd.Constantes;
import com.pmm.simarro.pmdm_banco_llopischristian.dao.CajeroDAO;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;

public class GestionCajeros extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    private ListView listaCajeros;
    private CajeroDAO cajeroDAO;
    private CajerosCursorAdapter cajerosAdapter;
    private Cursor cursor;
    private TextView v_txtSinDatos;
    private Cliente cliente = new Cliente();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cajeros);

        cliente = (Cliente) this.getIntent().getExtras().getSerializable("CLIENTE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGestionCajeros);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listaCajeros = (ListView) findViewById(R.id.listaCajeros);

        cajeroDAO = new CajeroDAO(this);

        try {
            cajeroDAO.abrir();

            cursor = cajeroDAO.getAll();
            startManagingCursor(cursor);

            cajerosAdapter = new CajerosCursorAdapter(this, cursor);

            listaCajeros.setAdapter(cajerosAdapter);

            if (cursor.getCount() > 0) {
                v_txtSinDatos = (TextView) findViewById(R.id.txtSinDatos);
                v_txtSinDatos.setVisibility(View.INVISIBLE);
                v_txtSinDatos.invalidate();
            }
        }catch (Exception e) {
            Toast.makeText(getBaseContext(), "Se ha producido un error al abrir la base de datos.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        listaCajeros.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent i = new Intent(this, AnadirCajero.class);

        i.putExtra(Constantes.C_MODO, Constantes.C_VISUALIZAR);

        i.putExtra(CajeroDAO.C_COLUMNA_ID, id);

        i.putExtra("CLIENTE", cliente);

        startActivityForResult(i, Constantes.C_VISUALIZAR);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        switch (id) {
            case R.id.menu_nuevo:
                i = new Intent(this, AnadirCajero.class);
                i.putExtra(Constantes.C_MODO, Constantes.C_CREAR);
                i.putExtra("CLIENTE", cliente);
                startActivityForResult(i, Constantes.C_CREAR);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (cliente != null)
            if (cliente.getNombre().equals("Admin"))
                getMenuInflater().inflate(R.menu.menu_cajero_nuevo, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case Constantes.C_CREAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            case Constantes.C_VISUALIZAR:
                if (resultCode == RESULT_OK)
                    recargar_lista();
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void recargar_lista() {
        CajeroDAO cajeroDAO = new CajeroDAO(getBaseContext());
        cajeroDAO.abrir();
        CajerosCursorAdapter hipotecasCursorAdapter = new CajerosCursorAdapter(this, cajeroDAO.getAll());
        listaCajeros.setAdapter(hipotecasCursorAdapter);
    }
}
