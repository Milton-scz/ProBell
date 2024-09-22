package milo.probell.Model.DetalleNotaVentaModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import milo.probell.DataBaseConfig.DatabaseHelper;

public class DetalleNotaVentaDatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    // Nombre de la tabla y columnas
    private static final String TABLE_DETALLE_NOTA_VENTA = "detalle_nota_venta";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_ID_PRODUCTO = "id_producto";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_PRECIOV = "preciov";

    public DetalleNotaVentaDatabaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insertar un detalle de nota de venta en la base de datos
    public void addDetalleNotaVenta(DetalleNotaVenta detalle) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_VENTA, detalle.getIdVenta());
        values.put(COLUMN_ID_PRODUCTO, detalle.getIdProducto());
        values.put(COLUMN_CANTIDAD, detalle.getCantidad());
        values.put(COLUMN_PRECIOV, detalle.getPreciov());
        db.insert(TABLE_DETALLE_NOTA_VENTA, null, values);
        db.close();
    }

    // Obtener un detalle de nota de venta por id
    public DetalleNotaVenta getDetalleNotaVenta(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DETALLE_NOTA_VENTA, new String[]{
                        COLUMN_ID, COLUMN_ID_VENTA, COLUMN_ID_PRODUCTO, COLUMN_CANTIDAD, COLUMN_PRECIOV},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            DetalleNotaVenta detalle = new DetalleNotaVenta(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_VENTA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_PRODUCTO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIOV))
            );
            cursor.close();
            return detalle;
        } else {
            return null;
        }
    }

    // Obtener todos los detalles de nota de venta
    public List<DetalleNotaVenta> getAllDetallesNotaVenta() {
        List<DetalleNotaVenta> detallesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_DETALLE_NOTA_VENTA;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DetalleNotaVenta detalle = new DetalleNotaVenta(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_VENTA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_PRODUCTO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIOV))
                );
                detallesList.add(detalle);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return detallesList;
    }

    // Actualizar un detalle de nota de venta
    public int updateDetalleNotaVenta(DetalleNotaVenta detalle) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_VENTA, detalle.getIdVenta());
        values.put(COLUMN_ID_PRODUCTO, detalle.getIdProducto());
        values.put(COLUMN_CANTIDAD, detalle.getCantidad());
        values.put(COLUMN_PRECIOV, detalle.getPreciov());
        return db.update(TABLE_DETALLE_NOTA_VENTA, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(detalle.getId())});
    }

    // Eliminar un detalle de nota de venta
    public void deleteDetalleNotaVenta(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_DETALLE_NOTA_VENTA, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
