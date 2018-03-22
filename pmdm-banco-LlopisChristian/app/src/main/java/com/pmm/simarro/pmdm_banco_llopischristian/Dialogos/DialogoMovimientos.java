package com.pmm.simarro.pmdm_banco_llopischristian.Dialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pmm.simarro.pmdm_banco_llopischristian.R;

public class DialogoMovimientos extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View context = inflater.inflate(R.layout.dialogo_movimientos, null);

        String mensaje = (String) this.getArguments().getString("MENSAJE");

        TextView dialogoTexto = (TextView) context.findViewById(R.id.dialogoTexto);

        dialogoTexto.setText(mensaje);

        builder.setView(context);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
