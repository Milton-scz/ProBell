package milo.probell.Model.ProductoModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import milo.probell.DataBaseConfig.DatabaseHelper;

public class ProductoDataBaseHelper {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    // Nombre de la tabla y columnas
    private static final String TABLE_PRODUCTOS = "producto";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_DESCRIPCION = "descripcion";
    private static final String COLUMN_PRECIO = "precio";
    private static final String COLUMN_STOCK = "stock";
    private static final String COLUMN_CATEGORIA_ID = "categoria_id";
    private static final String COLUMN_IMAGEN = "imagen"; // Columna para la imagen

    // Constructor
    public ProductoDataBaseHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }



    // Insertar un producto en la base de datos
    public void agregarProducto(Producto producto) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, producto.getNombre());
        values.put(COLUMN_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRECIO, producto.getPrecio());
        values.put(COLUMN_STOCK, producto.getStock());
        values.put(COLUMN_CATEGORIA_ID, producto.getCategoriaId());
        values.put(COLUMN_IMAGEN, producto.getImagen()); // Almacenar la imagen como BLOB
        db.insert(TABLE_PRODUCTOS, null, values);
        db.close();
    }

    // Obtener un producto por id
    public Producto getProducto(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTOS, new String[]{COLUMN_ID, COLUMN_NOMBRE, COLUMN_DESCRIPCION, COLUMN_PRECIO, COLUMN_STOCK, COLUMN_CATEGORIA_ID, COLUMN_IMAGEN},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Producto producto = new Producto(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA_ID)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN))
            );
            cursor.close();
            return producto;
        }
        return null;
    }

    // Obtener todos los productos
    public List<Producto> getAllProductos() {
        List<Producto> productos = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS, null);
        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA_ID)),
                        cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN))
                );
                productos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return productos;
    }

    // Actualizar un producto
    public int updateProducto(Producto producto) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, producto.getNombre());
        values.put(COLUMN_DESCRIPCION, producto.getDescripcion());
        values.put(COLUMN_PRECIO, producto.getPrecio());
        values.put(COLUMN_STOCK, producto.getStock());
        values.put(COLUMN_CATEGORIA_ID, producto.getCategoriaId());
        values.put(COLUMN_IMAGEN, producto.getImagen());
        return db.update(TABLE_PRODUCTOS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(producto.getId())});
    }

    // Eliminar un producto
    public void deleteProducto(int id) {
        db = dbHelper.getWritableDatabase();
        db.delete(TABLE_PRODUCTOS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
