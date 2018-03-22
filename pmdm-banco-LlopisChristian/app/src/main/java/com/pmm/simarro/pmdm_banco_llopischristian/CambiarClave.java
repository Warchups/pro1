package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;

import java.util.Objects;

public class CambiarClave extends AppCompatActivity {

    private Button atras;
    private Button cambiarClave;

    private EditText passActual;
    private EditText passNueva;
    private EditText passRep;


    private Cliente cliente = new Cliente();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_clave);

        atras = (Button) findViewById(R.id.atras);
        cambiarClave = (Button) findViewById(R.id.confrimar);

        SharedPreferences prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        String fuente = prefs.getString("tipoLetra", "");
        String color = prefs.getString("color", "");

        if (Objects.equals(color, "red")) {
            atras.setBackgroundColor(Color.RED);
            cambiarClave.setBackgroundColor(Color.RED);
        }else if (Objects.equals(color, "blue")) {
            atras.setBackgroundColor(Color.BLUE);
            cambiarClave.setBackgroundColor(Color.BLUE);
        }else if (Objects.equals(color, "green")) {
            atras.setBackgroundColor(Color.GREEN);
            cambiarClave.setBackgroundColor(Color.GREEN);
        }else if (Objects.equals(color, "pink")) {
            atras.setBackgroundColor(Color.rgb(255, 20, 147));
            cambiarClave.setBackgroundColor(Color.rgb(255, 20, 147));
        }else if (Objects.equals(color, "orange")) {
            atras.setBackgroundColor(Color.rgb(255, 165, 0));
            cambiarClave.setBackgroundColor(Color.rgb(255, 165, 0));
        }


        if (fuente.length() > 0) {

            String ruta;

            if (Objects.equals(fuente, "helvetica")) {
                ruta = "fonts/" + fuente + ".otf";
            }else {
                ruta = "fonts/" + fuente + ".ttf";
            }

            Typeface face = Typeface.createFromAsset(getAssets(), ruta);
            atras.setTypeface(face);
            cambiarClave.setTypeface(face);
        }

        passActual = (EditText) findViewById(R.id.passActual);
        passNueva = (EditText) findViewById(R.id.passNueva);
        passRep = (EditText) findViewById(R.id.passRep);

        cliente = (Cliente) this.getIntent().getExtras().getSerializable("CLIENTE");
    }

    public void atras(View v) {
        onBackPressed();
    }

    public void cambiarClave(View v) {
        MiBancoOperacional mbo = MiBancoOperacional.getInstance(this);

        String contraseña = cliente.getClaveSeguridad().trim();

        String contraseñaActual = passActual.getText().toString().trim();
        String contraseñaNueva = passNueva.getText().toString().trim();
        String contraseñaRep = passRep.getText().toString().trim();

        if (!compararContrasenas(contraseña, contraseñaActual)) {
            Toast.makeText(getApplicationContext(), "Contraseñas actual erronea!", Toast.LENGTH_SHORT).show();
        }else if (!compararContrasenas(contraseñaNueva, contraseñaRep)) {
            if (contraseñaNueva.length() == 0 ||contraseñaRep.length() == 0) {
                Toast.makeText(getApplicationContext(), "Las contraseñas no pueden estar vacias!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show();
            }
        }else if (compararContrasenas(contraseña, contraseñaNueva)) {
            Toast.makeText(getApplicationContext(), "La contraseña nueva no puede ser igual que la actual!", Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(getApplicationContext(), "Contraseñas correctas!", Toast.LENGTH_SHORT).show();

            cliente.setClaveSeguridad(contraseñaNueva);

            int cambioClave = mbo.changePassword(cliente);

            if (cambioClave == 0) {
                Toast.makeText(getApplicationContext(), "Error al actualizar las contraseñas!", Toast.LENGTH_SHORT).show();
            }else {
                //Toast.makeText(getApplicationContext(), "Contraseñas correctas!", Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Contraseña cambiada correctamente!")
                        .setCancelable(false)
                        .setNeutralButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        onBackPressed();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }

    }

    public boolean compararContrasenas(String pass1, String pass2) {
        if (pass1.length() != pass2.length()) {
            return false;
        }else if (pass1.length() == 0 || pass2.length() == 0) {
            return false;
        }else {
            for (int i = 0 ; i < pass1.length() ; i++ ) {
                if (pass1.charAt(i) != pass2.charAt(i)) {
                    return false;
                }
            }
        }

        return true;
    }
}
