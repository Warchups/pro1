package com.pmm.simarro.pmdm_banco_llopischristian.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pmm.simarro.pmdm_banco_llopischristian.bd.MiBD;

import java.util.ArrayList;

/**
 * Created by chris on 15/01/2018.
 */

public class CajeroDAO {

    /**
     * Definimos constante con el nombre de la tabla
     */
    public static final String C_TABLA = "cajeros" ;

    /**
     * Definimos constantes con el nombre de las columnas de la tabla
     */
    public static final String C_COLUMNA_ID = "_id";
    public static final String C_COLUMNA_DIRECCION = "direccion";
    public static final String C_COLUMNA_LAT = "lat";
    public static final String C_COLUMNA_LNG = "lng";
    public static final String C_COLUMNA_ZOOM = "zom";

    private Context contexto;
    private SQLiteDatabase db;

    private String[] columnas = new String[]{ C_COLUMNA_ID, C_COLUMNA_DIRECCION,
            C_COLUMNA_LAT, C_COLUMNA_LNG, C_COLUMNA_ZOOM} ;

    public CajeroDAO(Context context) {
        this.contexto = context;
    }

    public CajeroDAO() {

    }

    public CajeroDAO abrir(){
        db = MiBD.getDB();
        return this;
    }

    public long add(ContentValues contentValues) {
        if (db == null)
            db = MiBD.getDB();
        return db.insert(C_TABLA, null, contentValues);
    }


    public long update(ContentValues contentValues) {
        long result = 0;
        if (db == null)
            db = MiBD.getDB();
        if (contentValues.containsKey(C_COLUMNA_ID)) {
            //
            // Obtenemos el id y lo borramos de los valores a actualizar, ya que el id no se actualizar
            //
            long id = contentValues.getAsLong(C_COLUMNA_ID);
            contentValues.remove(C_COLUMNA_ID);
            //
            // Actualizamos el registro con el identificador que hemos extraido
            //
            result = db.update(C_TABLA, contentValues, "_id=" + id, null);
        }
        return result;
    }


    public void delete(long _id) {
        if (db == null)
            db = MiBD.getDB();
        db.delete(C_TABLA, "_id=" + _id, null);
    }

    public Cursor getAll() {
        Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);
        return c;
    }

    public Cursor getRegistro(long id) {
        String condicion = C_COLUMNA_ID + "=" + id;
        Cursor c = db.query( true, C_TABLA, columnas, condicion, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }
}
