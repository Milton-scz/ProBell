package milo.probell.Model.ClienteModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import milo.probell.DataBaseConfig.DatabaseHelper;

public class ClienteDatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    // Nombre de la tabla y columnas
    private static final String TABLE_CLIENTE = "cliente";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_CEDULA = "cedula";
    private static final String COLUMN_CELULAR = "celular";
    private static final String COLUMN_DIRECCION = "direccion";

    // Constructor
    public ClienteDatabaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insertar un cliente en la base de datos
    public void addCliente(Cliente cliente) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, cliente.getNombre());
        values.put(COLUMN_CEDULA, cliente.getCedula());
        values.put(COLUMN_CELULAR, cliente.getCelular());
        values.put(COLUMN_DIRECCION, cliente.getDireccion());
        db.insert(TABLE_CLIENTE, null, values);
        db.close();
    }

    // Obtener un cliente por id
    public Cliente getCliente(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CLIENTE, new String[]{COLUMN_ID, COLUMN_NOMBRE, COLUMN_CEDULA, COLUMN_CELULAR, COLUMN_DIRECCION},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Cliente cliente = new Cliente(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEDULA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CELULAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION))
            );
            cursor.close();
            return cliente;
        } else {
            return null;
        }
    }

    // Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        List<Cliente> clienteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CLIENTE;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEDULA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CELULAR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION))
                );
                clienteList.add(cliente);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clienteList;
    }

    // Actualizar un cliente
    public int updateCliente(Cliente cliente) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, cliente.getNombre());
        values.put(COLUMN_CEDULA, cliente.getCedula());
        values.put(COLUMN_CELULAR, cliente.getCelular());
        values.put(COLUMN_DIRECCION, cliente.getDireccion());
        return db.update(TABLE_CLIENTE, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
    }

    // Eliminar un cliente
    public void deleteCliente(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_CLIENTE, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
