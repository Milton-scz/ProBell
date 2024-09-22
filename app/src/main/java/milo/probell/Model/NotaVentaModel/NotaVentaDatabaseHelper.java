package milo.probell.Model.NotaVentaModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import milo.probell.DataBaseConfig.DatabaseHelper;

public class NotaVentaDatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    // Nombre de la tabla y columnas
    private static final String TABLE_NOTA_VENTA = "nota_venta";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_MONTO = "monto";
    private static final String COLUMN_ID_CLIENTE = "id_cliente";

    // Constructor
    public NotaVentaDatabaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }



    // Insertar una nota de venta en la base de datos
    public void addNotaVenta(NotaVenta notaVenta) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FECHA, notaVenta.getFecha());
        values.put(COLUMN_MONTO, notaVenta.getMonto());
        values.put(COLUMN_ID_CLIENTE, notaVenta.getIdCliente());
        db.insert(TABLE_NOTA_VENTA, null, values);
        db.close();
    }

    // Obtener una nota de venta por id
    public NotaVenta getNotaVenta(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTA_VENTA, new String[]{COLUMN_ID, COLUMN_FECHA, COLUMN_MONTO, COLUMN_ID_CLIENTE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            NotaVenta notaVenta = new NotaVenta(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_CLIENTE))
            );
            cursor.close();
            return notaVenta;
        }
        return null;
    }

    // Obtener todas las notas de venta
    public List<NotaVenta> getAllNotaVentas() {
        List<NotaVenta> notaVentaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NOTA_VENTA;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                NotaVenta notaVenta = new NotaVenta(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_MONTO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_CLIENTE))
                );
                notaVentaList.add(notaVenta);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notaVentaList;
    }

    // Actualizar una nota de venta
    public int updateNotaVenta(NotaVenta notaVenta) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FECHA, notaVenta.getFecha());
        values.put(COLUMN_MONTO, notaVenta.getMonto());
        values.put(COLUMN_ID_CLIENTE, notaVenta.getIdCliente());
        return db.update(TABLE_NOTA_VENTA, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(notaVenta.getId())});
    }

    // Eliminar una nota de venta
    public void deleteNotaVenta(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NOTA_VENTA, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
