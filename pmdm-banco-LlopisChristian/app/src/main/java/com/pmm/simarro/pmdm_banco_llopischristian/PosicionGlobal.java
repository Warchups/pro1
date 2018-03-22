package com.pmm.simarro.pmdm_banco_llopischristian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pmm.simarro.pmdm_banco_llopischristian.Fragments.MovimientosFragment;
import com.pmm.simarro.pmdm_banco_llopischristian.Fragments.MovimientosListened;
import com.pmm.simarro.pmdm_banco_llopischristian.Fragments.PosicionGlobalFragment;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;


public class PosicionGlobal extends AppCompatActivity implements MovimientosListened {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posicion_global);

        PosicionGlobalFragment frgPosicionGlobal = (PosicionGlobalFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPosicionGlobal);
        frgPosicionGlobal.setCuentaListened(this);


    }

    @Override
    public void onCuentaSeleccionada(Cuenta cuenta) {
        boolean hayMovimientos = (getSupportFragmentManager().findFragmentById(R.id.fragmentMovimientos) != null);

        if (hayMovimientos) {
            ((MovimientosFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMovimientos)).mostrarMovimientos(cuenta);
        }else {
            Intent intent = new Intent(this, Movimientos.class);
            intent.putExtra("Cuenta", cuenta);
            startActivity(intent);
        }
    }
}
