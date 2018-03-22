package com.pmm.simarro.pmdm_banco_llopischristian.adaptador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.Dialogos.DialogoMovimientos;
import com.pmm.simarro.pmdm_banco_llopischristian.Fragments.TodosMovimientosFragment;
import com.pmm.simarro.pmdm_banco_llopischristian.R;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Movimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    Cuenta cuenta;
    MiBancoOperacional mbo;


    private String tabTitles[] = new String[] { "Todos", "Dinero enviado", "Dinero recibido", "Reintegro cajero"};

    public MiFragmentPagerAdapter(FragmentManager fm, Cuenta cuenta, MiBancoOperacional mbo) {
        super(fm);
        this.cuenta = cuenta;
        this.mbo = mbo;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        Bundle bundle = new Bundle();
        switch(position) {
            case 0:
                bundle.putSerializable("MOVS", mbo.getMovimientos(cuenta));
                f = new TodosMovimientosFragment();
                f.setArguments(bundle);
                break;
            case 1:
                bundle.putSerializable("MOVS", mbo.getMovimientosTipo(cuenta, 0));
                f = new TodosMovimientosFragment();
                f.setArguments(bundle);
                break;
            case 2:
                bundle.putSerializable("MOVS", mbo.getMovimientosTipo(cuenta, 1));
                f = new TodosMovimientosFragment();
                f.setArguments(bundle);
                break;
            case 3:
                bundle.putSerializable("MOVS", mbo.getMovimientosTipo(cuenta, 2));
                f = new TodosMovimientosFragment();
                f.setArguments(bundle);
                break;
        }


        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
