package milo.probell.Model.CategoriaModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import milo.probell.DataBaseConfig.DatabaseHelper;

public class CategoriaDatabaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    // Nombre de la tabla y columnas
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";

    // Constructor
    public CategoriaDatabaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insertar una categoría en la base de datos
    public void addCategoria(Categoria categoria) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, categoria.getNombre());
        db.insert(TABLE_CATEGORIA, null, values);
        db.close();
    }

    // Obtener una categoría por id
    public Categoria getCategoria(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIA, new String[]{COLUMN_ID, COLUMN_NOMBRE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Categoria categoria = new Categoria(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
            );
            cursor.close();
            return categoria;
        } else {
            return null;
        }
    }

    // Obtener todas las categorías
    public List<Categoria> getAllCategorias() {
        List<Categoria> categoriaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIA;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                );
                categoriaList.add(categoria);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoriaList;
    }

    // Actualizar una categoría
    public int updateCategoria(Categoria categoria) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, categoria.getNombre());
        return db.update(TABLE_CATEGORIA, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(categoria.getId())});
    }

    // Eliminar una categoría
    public void deleteCategoria(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_CATEGORIA, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
