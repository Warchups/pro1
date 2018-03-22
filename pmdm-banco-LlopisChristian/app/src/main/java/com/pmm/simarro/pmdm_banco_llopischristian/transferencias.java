package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Movimiento;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class transferencias extends AppCompatActivity {

    private GridView cuentasOrigen;
    private RadioButton cuentaMia;
    private RadioButton cuentaAjena;
    private Button ok;
    private Button cancel;
    private ConstraintLayout contenedorMia;
    private ConstraintLayout contenedorAjena;
    private Spinner cuentasMias;
    private Spinner divisa;
    private EditText importe;
    private CheckBox enviarJustificante;

    private EditText ca1;
    private EditText ca2;
    private EditText ca3;
    private EditText ca4;

    private String cuentaOrigen;
    private String cuentaDestino;
    private float imp;
    private String div;
    private boolean justificante;
    private int anterior = -1;

    private String mensaje = "";

    private boolean tipoCuenta;
    private boolean mostrarError = true;
    private boolean mostrarBorrado = true;

    ArrayList<Cuenta> arrayCuentas;
    int origen = 0;

    ShimmerFrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencias);

        cuentasOrigen = (GridView) findViewById(R.id.CuentasOrigen);
        cuentaMia = (RadioButton) findViewById(R.id.cuentaMia);
        cuentaAjena = (RadioButton) findViewById(R.id.cuentaAjena);
        ok = (Button) findViewById(R.id.ok);
        cancel = (Button) findViewById(R.id.cancel);

        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_transferencias);

        SharedPreferences prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        String fuente = prefs.getString("tipoLetra", "");
        String color = prefs.getString("color", "");

        if (Objects.equals(color, "red")) {
            ok.setBackgroundColor(Color.RED);
            cancel.setBackgroundColor(Color.RED);
        }else if (Objects.equals(color, "blue")) {
            ok.setBackgroundColor(Color.BLUE);
            cancel.setBackgroundColor(Color.BLUE);
        }else if (Objects.equals(color, "green")) {
            ok.setBackgroundColor(Color.GREEN);
            cancel.setBackgroundColor(Color.GREEN);
        }else if (Objects.equals(color, "pink")) {
            ok.setBackgroundColor(Color.rgb(255, 20, 147));
            cancel.setBackgroundColor(Color.rgb(255, 20, 147));
        }else if (Objects.equals(color, "orange")) {
            ok.setBackgroundColor(Color.rgb(255, 165, 0));
            cancel.setBackgroundColor(Color.rgb(255, 165, 0));
        }


        if (fuente.length() > 0) {

            String ruta;

            if (Objects.equals(fuente, "helvetica")) {
                ruta = "fonts/" + fuente + ".otf";
            }else {
                ruta = "fonts/" + fuente + ".ttf";
            }

            Typeface face = Typeface.createFromAsset(getAssets(), ruta);
            ok.setTypeface(face);
            cancel.setTypeface(face);
        }

        contenedorMia = (ConstraintLayout) findViewById(R.id.contenedorMia);
        contenedorAjena = (ConstraintLayout) findViewById(R.id.contenedorAjena);
        cuentasMias = (Spinner) findViewById(R.id.cuentasMias);
        divisa = (Spinner) findViewById(R.id.divisa);
        importe = (EditText) findViewById(R.id.importe);
        enviarJustificante = (CheckBox) findViewById(R.id.enviarJustificante);

        ca1 = (EditText) findViewById(R.id.ca1);
        ca2 = (EditText) findViewById(R.id.ca2);
        ca3 = (EditText) findViewById(R.id.ca3);
        ca4 = (EditText) findViewById(R.id.ca4);



        MiBancoOperacional mbo = MiBancoOperacional.getInstance(this);

        Cliente cliente = (Cliente) this.getIntent().getExtras().getSerializable("CLIENTE");

        arrayCuentas = mbo.getCuentas(cliente);

        String[] cuentas = new String[arrayCuentas.size()];

        for (int i = 0 ; i < arrayCuentas.size() ; i++ ) {
            cuentas[i] = arrayCuentas.get(i).getNumeroCuenta();
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cuentas);

        cuentasOrigen.setAdapter(adaptador);

        cuentasOrigen.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, android.view.View v, int position, long id) {
                        cuentasOrigen.getChildAt(position).setBackgroundColor(Color.rgb(100,100,100));
                        Toast.makeText(getApplicationContext(),"Cuenta seleccionada: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        cuentaOrigen = (String) parent.getItemAtPosition(position);
                        origen = position;

                        if (anterior != -1)
                            cuentasOrigen.getChildAt(anterior).setBackgroundColor(Color.TRANSPARENT);

                        anterior = position;
                    }
                });


        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cuentas);

        cuentasMias.setAdapter(adaptadorSpinner);

        String[] divisas = new String[2];
        divisas[0] = "€";
        divisas[1] = "$";

        ArrayAdapter<String> adaptadorDivisas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, divisas);

        divisa.setAdapter(adaptadorDivisas);


    }

    public void onRadioButtonClicked(View v) {
        //boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.cuentaMia:

                tipoCuenta = true;
                contenedorAjena.setVisibility(View.GONE);
                contenedorMia.setVisibility(View.VISIBLE);

                break;
            case R.id.cuentaAjena:

                tipoCuenta = false;
                contenedorMia.setVisibility(View.GONE);
                contenedorAjena.setVisibility(View.VISIBLE);

                break;
        }

    }

    public void cancel(View v) {
        mensaje = "El contenido ha sido borrado.";

        cuentaOrigen = null;
        cuentaDestino = null;
        imp = 0;
        importe.setText("");
        ca1.setText("");
        ca2.setText("");
        ca3.setText("");
        ca4.setText("");

        enviarJustificante.setChecked(false);
        cuentaMia.setChecked(false);
        cuentaAjena.setChecked(false);

        contenedorMia.setVisibility(View.GONE);
        contenedorAjena.setVisibility(View.GONE);

        if (anterior != -1)
            cuentasOrigen.getChildAt(anterior).setBackgroundColor(Color.TRANSPARENT);

        if (mostrarBorrado)
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();

        mostrarBorrado = true;


    }

    public void ok(View v) {

        int destinoPosicion = -1;
        Cuenta destino = new Cuenta();
        if (tipoCuenta) {
            cuentaDestino = cuentasMias.getSelectedItem().toString();

            for (int i = 0 ; i < arrayCuentas.size() ; i++) {
                if (cuentaDestino == arrayCuentas.get(i).getNumeroCuenta()) {
                    destinoPosicion = i;
                    break;
                }
            }

            destino = arrayCuentas.get(destinoPosicion);

        }else {

            destino.setBanco(ca1.getText().toString());
            destino.setSucursal(ca2.getText().toString());
            destino.setDc(ca3.getText().toString());
            destino.setNumeroCuenta(ca4.getText().toString());


        }



        div = divisa.getSelectedItem().toString();
        justificante = enviarJustificante.isChecked();

        if (importe.getText().length() == 0)
            imp = 0;
        else
            imp = Float.parseFloat(importe.getText().toString());



        if (cuentaOrigen == null || cuentaDestino == "" || div == "") {
            mensaje = "Hay algun error con los campos.";
        }else if (imp == 0){
            mensaje = "El importe no puede ser 0.";
        }else{
            if (arrayCuentas.get(origen).getNumeroCuenta() == destino.getNumeroCuenta()) {
                mensaje = "La cuenta origen y la cuenta destino son la misma.";
            }else {
                mostrarError = false;
                TransferenciaAsyncTask tat = new TransferenciaAsyncTask();
                tat.execute(destino);
            }
        }

        if (mostrarError)
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();


    }

    private void cancelar() {
        mostrarBorrado = false;
        cancel(getCurrentFocus());
    }

    class TransferenciaAsyncTask extends AsyncTask<Cuenta, Integer, String> {

        @Override
        protected void onPreExecute() {
            container.startShimmerAnimation();
        }

        @Override
        protected String doInBackground(Cuenta... destino) {
            MiBancoOperacional mbo = MiBancoOperacional.getInstance(transferencias.this);

            String mensaje = "";

            Movimiento movimiento = new Movimiento(24, 0, new Date(), "Transferencia Usuario", imp, arrayCuentas.get(origen), destino[0]);
            switch (mbo.transferencia(movimiento)) {
                case 0:
                    mensaje = "Transferencia realizada correctamente.";
                    break;
                case 1:
                    mensaje = "No se encuentra la cuenta destino.";
                    break;
                case 2:
                    mensaje = "No hay suficiente saldo para realizar la transferencia.";
                    break;
            }

            int tiempo = 2000;
            SystemClock.sleep(tiempo);

            return mensaje;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String res) {
            container.stopShimmerAnimation();
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
            cancelar();
        }
    }
}
