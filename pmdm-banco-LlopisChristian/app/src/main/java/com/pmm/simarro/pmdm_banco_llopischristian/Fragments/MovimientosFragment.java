package com.pmm.simarro.pmdm_banco_llopischristian.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.Dialogos.DialogoMovimientos;
import com.pmm.simarro.pmdm_banco_llopischristian.R;
import com.pmm.simarro.pmdm_banco_llopischristian.adaptador.MiFragmentPagerAdapter;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Movimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovimientosFragment extends Fragment {

    private TextView tituloCuenta;
    private TextView saldoCuenta;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movimentos,container,false);
    }

    public void mostrarMovimientos(Cuenta cuenta) {

        MiBancoOperacional mbo = MiBancoOperacional.getInstance(getActivity());

        tituloCuenta = (TextView)getView().findViewById(R.id.tituloCuenta);
        saldoCuenta = (TextView)getView().findViewById(R.id.saldoCuenta);

        tituloCuenta.setText("Movimientos de la cuenta:\n" + cuenta.getNumeroCuenta());
        saldoCuenta.setText(Float.toString(cuenta.getSaldoActual()));

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPagerAdapter(
                getChildFragmentManager(), cuenta, mbo));

        tabLayout = (TabLayout) getActivity().findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

    }
}
