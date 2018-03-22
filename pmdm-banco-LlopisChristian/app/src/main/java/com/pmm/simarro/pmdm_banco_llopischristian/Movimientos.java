package com.pmm.simarro.pmdm_banco_llopischristian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pmm.simarro.pmdm_banco_llopischristian.Fragments.MovimientosFragment;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;

public class Movimientos extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);

        Cuenta cuenta = (Cuenta) getIntent().getExtras().getSerializable("Cuenta");

        MovimientosFragment frgMovimientos = (MovimientosFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMovimientos);

        frgMovimientos.mostrarMovimientos(cuenta);
    }
}
