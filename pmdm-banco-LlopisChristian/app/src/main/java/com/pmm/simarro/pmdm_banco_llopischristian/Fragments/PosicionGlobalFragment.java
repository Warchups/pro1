package com.pmm.simarro.pmdm_banco_llopischristian.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pmm.simarro.pmdm_banco_llopischristian.API.MiBancoOperacional;
import com.pmm.simarro.pmdm_banco_llopischristian.R;
import com.pmm.simarro.pmdm_banco_llopischristian.adaptador.AdaptadorCuentas;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cliente;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;

import java.util.ArrayList;

public class PosicionGlobalFragment extends Fragment {
    private MovimientosListened listener;
    private ListView seleccionarCuenta;
    private Cliente cliente = new Cliente();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posicion_global,container,false);
    }

    public void setCuentaListened(MovimientosListened listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle state) {
        super.onActivityCreated(state);
        MiBancoOperacional mbo = MiBancoOperacional.getInstance(getContext());
        seleccionarCuenta = (ListView) getView().findViewById(R.id.seleccionarCuenta);

        cliente = (Cliente) getActivity().getIntent().getExtras().getSerializable("CLIENTE");

        ArrayList<Cuenta> cuentas = mbo.getCuentas(cliente);

        seleccionarCuenta.setAdapter(new AdaptadorCuentas(this, cuentas));

        seleccionarCuenta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                if (listener != null) {
                    listener.onCuentaSeleccionada((Cuenta) seleccionarCuenta.getAdapter().getItem(pos));
                }
            }
        });
    }
}
