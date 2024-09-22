package milo.probell.View.ProductoView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import milo.probell.Controller.ProductoController.ProductoController;
import milo.probell.Controller.CategoriaController.CategoriaController;
import milo.probell.Model.CategoriaModel.Categoria;
import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class AgregarProductoViewActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText edtNombreProducto;
    private EditText edtDescripcionProducto;
    private EditText edtPrecioProducto;
    private EditText edtStockProducto;
    private Spinner spinnerCategoria;
    private Button btnGuardarProducto;
    private Button btnSeleccionarImagen;
    private ImageView imgProducto;
    private ProductoController productoController;
    private CategoriaController categoriaController;
    private List<Categoria> categorias;
    private Uri imagenSeleccionadaUri; // URI de la imagen seleccionada
    private byte[] imagenEnBytes; // Imagen en bytes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto_view);

        // Inicialización del controller y vista
        productoController = new ProductoController(this);
        categoriaController = new CategoriaController(this);

        edtNombreProducto = findViewById(R.id.edtNombreProducto);
        edtDescripcionProducto = findViewById(R.id.edtDescripcionProducto);
        edtPrecioProducto = findViewById(R.id.edtPrecioProducto);
        edtStockProducto = findViewById(R.id.edtStockProducto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        imgProducto = findViewById(R.id.imgProducto);

        // Cargar categorías en el spinner
        cargarCategorias();

        // Configurar el botón para seleccionar imagen
        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        // Configuración del botón para guardar el nuevo producto
        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombreProducto.getText().toString().trim();
                String descripcion = edtDescripcionProducto.getText().toString().trim();
                String precioStr = edtPrecioProducto.getText().toString().trim();
                String stockStr = edtStockProducto.getText().toString().trim();

                if (nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
                    Toast.makeText(AgregarProductoViewActivity.this, "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio = Double.parseDouble(precioStr);
                int stock = Integer.parseInt(stockStr);
                int categoriaId = ((Categoria) spinnerCategoria.getSelectedItem()).getId();

                // Convertir la URI de la imagen a String
                // No se necesita convertir a String si usas bytes
                // String uriImagen = imagenSeleccionadaUri != null ? imagenSeleccionadaUri.toString() : null;

                // Llamar al controlador para agregar el producto, incluyendo la imagen en bytes
                productoController.agregarProducto(nombre, descripcion, precio, stock, categoriaId, imagenEnBytes);

                // Mostrar mensaje y cerrar la actividad
                Toast.makeText(AgregarProductoViewActivity.this, "Producto agregado exitosamente.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Método para abrir la galería
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Abre la galería para seleccionar una imagen
    }

    // Captura el resultado de la selección de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenSeleccionadaUri = data.getData(); // Obtener el URI de la imagen seleccionada
            imgProducto.setImageURI(imagenSeleccionadaUri); // Mostrar la imagen seleccionada en el ImageView

            try {
                // Convertir la imagen a byte[]
                imagenEnBytes = convertImageUriToByteArray(imagenSeleccionadaUri);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al convertir la imagen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No seleccionaste ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] convertImageUriToByteArray(Uri imageUri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // Usa PNG o JPEG según lo necesites
        return outputStream.toByteArray();
    }

    // Cargar las categorías en el Spinner
    private void cargarCategorias() {
        categorias = categoriaController.getAllCategorias(); // Obtener todas las categorías
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }
}
