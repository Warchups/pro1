package com.pmm.simarro.pmdm_banco_llopischristian;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

public class Configuracion extends AppCompatActivity {

    static SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("preferenciasbancarias", Context.MODE_PRIVATE);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, new PreferenciasFragment());
        ft.commit();
    }

    public static class PreferenciasFragment extends PreferenceFragment {

        static ListPreference idioma;
        static ListPreference origenDatos;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.opciones);

            idioma = (ListPreference) findPreference("idioma");
            origenDatos = (ListPreference) findPreference("origenDatos");

            if(idioma.getValue() == null){
                idioma.setValueIndex(0);
            }
            if(origenDatos.getValue() == null){
                origenDatos.setValueIndex(0);
            }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EditTextPreference saludo = (EditTextPreference) findPreference("saludo");
            ListPreference idioma = (ListPreference) findPreference("idioma");
            ListPreference origenDatos = (ListPreference) findPreference("origenDatos");
            CheckBoxPreference reproducirMusica = (CheckBoxPreference) findPreference("reproducirMusica");
            CheckBoxPreference reproducirVideo = (CheckBoxPreference) findPreference("reproducirVideo");
            CheckBoxPreference valoresDefecto = (CheckBoxPreference) findPreference("valoresDefecto");
            ListPreference color = (ListPreference) findPreference("color");
            ListPreference fuente = (ListPreference) findPreference("tipoLetra");

            SharedPreferences.Editor editor = prefs.edit();

            if (valoresDefecto.isChecked()) {
                editor.putString("saludo", "");
                editor.putString("idioma", "ES");
                editor.putString("basededatos", "LOCAL");
                editor.putBoolean("reproducirMusica", false);
                editor.putBoolean("reproducirVideo", true);
                editor.putString("tipoLetra", "");
                editor.putString("color", "");

            } else {
                editor.putString("saludo", saludo.getText());
                editor.putString("idioma", idioma.getValue());
                editor.putString("origenDatos", origenDatos.getValue());
                editor.putBoolean("reproducirMusica", reproducirMusica.isChecked());
                editor.putBoolean("reproducirVideo", reproducirVideo.isChecked());
                editor.putString("tipoLetra", fuente.getValue());
                editor.putString("color", color.getValue());
            }


            editor.commit();

            //Quan tire arrere, com les activitys ja estan creades no es veu que canvie el idioma, pero si tornes a crear una activity si que canvia
            String idi = prefs.getString("idioma", "");

            Locale localizacion = new Locale(idi.toLowerCase(), idi);

            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        }

    }
}
