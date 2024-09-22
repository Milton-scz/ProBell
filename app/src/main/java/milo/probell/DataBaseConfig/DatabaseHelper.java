package milo.probell.DataBaseConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    public static final String DATABASE_NAME ="cosme";
    public static final int DATABASE_VERSION = 9;

    // Definición de la tabla "categoria"
    private static final String TABLE_CATEGORIA = "categoria";
    private static final String TABLE_PRODUCTO = "producto";
    private static final String TABLE_CLIENTE = "cliente";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";

    // Definición de la tabla "detalle_nota_venta"
    private static final String TABLE_DETALLE_NOTA_VENTA = "detalle_nota_venta";
    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_ID_PRODUCTO = "id_producto";
    private static final String COLUMN_CANTIDAD = "cantidad";
    private static final String COLUMN_PRECIOV = "preciov"; // Precio de venta

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "categoria"
        String CREATE_TABLE_CATEGORIA = "CREATE TABLE " + TABLE_CATEGORIA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_CATEGORIA);

        // Crear la tabla "productos"
        String CREATE_TABLE_PRODUCTO = "CREATE TABLE " + TABLE_PRODUCTO + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "descripcion TEXT, " +
                "precio REAL NOT NULL, " +
                "stock INTEGER NOT NULL, " + // Añadir stock aquí
                "imagen BLOB, " + // Asegúrate de añadir una coma aquí
                "categoria_id INTEGER NOT NULL, " + // Añadir referencia a categoria
                "FOREIGN KEY(categoria_id) REFERENCES " + TABLE_CATEGORIA + "(id));";
        db.execSQL(CREATE_TABLE_PRODUCTO);


        // Crear la tabla "clientes"
        String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "cedula TEXT NOT NULL, " +
                "celular TEXT NOT NULL, " +
                "direccion TEXT );";
        db.execSQL(CREATE_TABLE_CLIENTE);

        // Crear la tabla "ventas"
        String CREATE_TABLE_VENTAS = "CREATE TABLE nota_venta (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_cliente INTEGER NOT NULL, " +
                "fecha TEXT NOT NULL, " +
                "monto DOUBLE , " +
                "FOREIGN KEY (id_cliente) REFERENCES cliente(id));"; // Eliminar producto_id de ventas
        db.execSQL(CREATE_TABLE_VENTAS);

        // Crear la tabla "detalle_nota_venta"
        String CREATE_TABLE_DETALLE_NOTA_VENTA = "CREATE TABLE " + TABLE_DETALLE_NOTA_VENTA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_VENTA + " INTEGER NOT NULL, " +
                COLUMN_ID_PRODUCTO + " INTEGER NOT NULL, " +
                COLUMN_CANTIDAD + " INTEGER NOT NULL, " +
                COLUMN_PRECIOV + " REAL NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_ID_VENTA + ") REFERENCES nota_venta(id), " +
                "FOREIGN KEY (" + COLUMN_ID_PRODUCTO + ") REFERENCES producto(id));";
        db.execSQL(CREATE_TABLE_DETALLE_NOTA_VENTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas si hay una actualización
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS cliente");
        db.execSQL("DROP TABLE IF EXISTS nota_venta");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_NOTA_VENTA);

        // Vuelve a crear las tablas
        onCreate(db);
    }
}
