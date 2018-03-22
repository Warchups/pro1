package com.pmm.simarro.pmdm_banco_llopischristian.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pmm.simarro.pmdm_banco_llopischristian.Dialogos.DialogoMovimientos;
import com.pmm.simarro.pmdm_banco_llopischristian.R;
import com.pmm.simarro.pmdm_banco_llopischristian.adaptador.AdaptadorMovimientos;
import com.pmm.simarro.pmdm_banco_llopischristian.pojo.Movimiento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodosMovimientosFragment extends Fragment {

    private ListView movimientos;
    private ArrayList<Movimiento> arrayMovimientos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_todos_movimientos, container, false);
        movimientos = (ListView)v.findViewById(R.id.movimientos);
        arrayMovimientos = (ArrayList<Movimiento>) getArguments().getSerializable("MOVS");
        mostrarMovimientos();
        return v;
    }

    public void mostrarMovimientos() {
        if (arrayMovimientos.size() > 0){

            final String[] movs = new String[arrayMovimientos.size()];
            for(int i=0;i<arrayMovimientos.size();i++){
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha =  arrayMovimientos.get(i).getFechaOperacion();
                String descripcion = arrayMovimientos.get(i).getDescripcion();
                float importe = arrayMovimientos.get(i).getImporte();

                String cuentaDestino = arrayMovimientos.get(i).getCuentaDestino().getNumeroCuenta();

                if (arrayMovimientos.get(i).getTipo() == 0) {
                    movs[i] = "Fecha de operacion: " + formateador.format(fecha) + "\nDescripción: " + descripcion + "\nImporte: " + importe +"\nCuenta destino: " + cuentaDestino;
                }else if (arrayMovimientos.get(i).getTipo() == 1) {
                    movs[i] = "Fecha de operacion: " + formateador.format(fecha) + "\nDescripción: " + descripcion + "\nImporte: " + importe +"\nCuenta origen: " + cuentaDestino;
                }else {
                    movs[i] = "Fecha de operacion: " + formateador.format(fecha) + "\nDescripción: " + descripcion + "\nImporte: " + importe;
                }

            }
            ArrayList<String> mensajes = new ArrayList<>();

            for (int i = 0; i < movs.length ; i++) {
                mensajes.add(movs[i]);
            }

            movimientos.setAdapter(new AdaptadorMovimientos(this, mensajes));

            movimientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putString("MENSAJE", movs[position]);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    DialogoMovimientos dialogo = new DialogoMovimientos();
                    dialogo.setArguments(bundle);
                    dialogo.show(fragmentManager, "tagAceptar");
                }
            });

        } else {
            ArrayList<String> vacio =  new ArrayList<>();
            vacio.add("La cuenta no tiene movimientos en este apartado.");
            movimientos.setAdapter(new AdaptadorMovimientos(this, vacio));
        }

    }
}
