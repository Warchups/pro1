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
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Movimiento;

import java.util.ArrayList;

/**
 * Created by chris on 27/11/2017.
 */

public class AdaptadorMovimientos extends ArrayAdapter<String> {
    Activity context;
    ArrayList<String> movimientos;

    public AdaptadorMovimientos(Fragment context, ArrayList<String> movimientos) {
        super(context.getActivity(), R.layout.lista_cuentas_elementos, movimientos);
        this.context = context.getActivity();
        this.movimientos = movimientos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.lista_movimientos_elementos, null);

        TextView mov = (TextView) item.findViewById(R.id.mov);
        mov.setText(movimientos.get(position));

        return item;
    }
}
