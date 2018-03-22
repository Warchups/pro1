package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton botonLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonLogin = (ImageButton) findViewById(R.id.bLogin);

        SharedPreferences prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        String idioma = prefs.getString("idioma", "");

        if (idioma.length() > 0) {
            Locale localizacion = new Locale(idioma.toLowerCase(), idioma);

            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

    }

    public void login(View v) {
        Intent intent = new Intent(MainActivity.this, pantallaLogin.class);

        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        startActivity(intent);
    }
}
