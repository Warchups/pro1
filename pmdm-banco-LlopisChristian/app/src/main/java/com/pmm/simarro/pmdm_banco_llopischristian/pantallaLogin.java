package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;

import java.util.Objects;
import com.facebook.shimmer.ShimmerFrameLayout;

public class pantallaLogin extends AppCompatActivity {

    private Button botonSalir;
    private Button iniciarSesion;
    private EditText user;
    private EditText pass;
    //Explode explode = new Explode();

    private Cliente login = new Cliente();

    ShimmerFrameLayout container;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);

        botonSalir = (Button) findViewById(R.id.bSalir);
        iniciarSesion = (Button) findViewById(R.id.iniciarSesion);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

        container = (ShimmerFrameLayout) findViewById(R.id.shimmer_login);



        SharedPreferences prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        String fuente = prefs.getString("tipoLetra", "");
        String color = prefs.getString("color", "");

        if (Objects.equals(color, "red")) {
            botonSalir.setBackgroundColor(Color.RED);
            iniciarSesion.setBackgroundColor(Color.RED);
        }else if (Objects.equals(color, "blue")) {
            botonSalir.setBackgroundColor(Color.BLUE);
            iniciarSesion.setBackgroundColor(Color.BLUE);
        }else if (Objects.equals(color, "green")) {
            botonSalir.setBackgroundColor(Color.GREEN);
            iniciarSesion.setBackgroundColor(Color.GREEN);
        }else if (Objects.equals(color, "pink")) {
            botonSalir.setBackgroundColor(Color.rgb(255, 20, 147));
            iniciarSesion.setBackgroundColor(Color.rgb(255, 20, 147));
        }else if (Objects.equals(color, "orange")) {
            botonSalir.setBackgroundColor(Color.rgb(255, 165, 0));
            iniciarSesion.setBackgroundColor(Color.rgb(255, 165, 0));
        }

        if (fuente.length() > 0) {

            String ruta;

            if (Objects.equals(fuente, "helvetica")) {
                ruta = "fonts/" + fuente + ".otf";
            }else {
                ruta = "fonts/" + fuente + ".ttf";
            }

            Typeface face = Typeface.createFromAsset(getAssets(), ruta);
            botonSalir.setTypeface(face);
            iniciarSesion.setTypeface(face);
        }

        /*explode.setDuration(500); // Duración en milisegundos
        getWindow().setEnterTransition(explode);*/
    }

    public void atras(View v) {
        onBackPressed();
    }

    public void iniciar(View v) {
        MiBancoOperacional mbo = MiBancoOperacional.getInstance(this);

        login.setNif(user.getText().toString());
        login.setClaveSeguridad(pass.getText().toString());

        IniciarAsyncTask iat = new IniciarAsyncTask();
        iat.execute(mbo);
    }

    class IniciarAsyncTask extends AsyncTask<MiBancoOperacional, Integer, Cliente> {


        @Override
        protected void onPreExecute() {
            container.startShimmerAnimation();
        }

        @Override
        protected Cliente doInBackground(MiBancoOperacional... mbo) {
            Cliente cliente = mbo[0].login(login);

            int tiempo = 2000;
            SystemClock.sleep(tiempo);

            return cliente;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Cliente res) {
            container.stopShimmerAnimation();
            if (res != null) {
                Intent intent = new Intent(pantallaLogin.this, pantallaInicio.class);
                intent.putExtra("CLIENTE", res);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
