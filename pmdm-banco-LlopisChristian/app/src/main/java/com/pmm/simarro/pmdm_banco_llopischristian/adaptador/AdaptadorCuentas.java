package com.pmm.simarro.pmdm_banco_llopischristian.adaptador;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pmm.simarro.pmdm_banco_llopischristian.R;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Cuenta;

import java.util.ArrayList;

/**
 * Created by chris on 23/10/2017.
 */

public class AdaptadorCuentas extends ArrayAdapter<Cuenta> {

    Activity context;
    ArrayList<Cuenta> cuentas;

    public AdaptadorCuentas(Fragment context, ArrayList<Cuenta> cuentas) {
        super(context.getActivity(), R.layout.lista_cuentas_elementos, cuentas);
        this.context = context.getActivity();
        this.cuentas = cuentas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.lista_cuentas_elementos, null);

        TextView numCuenta = (TextView) item.findViewById(R.id.numCuenta);
        numCuenta.setText(cuentas.get(position).getNumeroCuenta());

        return item;
    }
}


