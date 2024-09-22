package milo.probell.View.ProductoView;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import milo.probell.Controller.ProductoController.ProductoController;
import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class EditarProductoViewActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextPrecio, editTextStock;
    private Button buttonGuardar, buttonEliminar;
    private Producto producto;
    private ProductoController productoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto_view);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        editTextStock = findViewById(R.id.editTextStock);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        productoController = new ProductoController(this);

        buttonEliminar.setOnClickListener(v -> {
            if (producto != null) {
                productoController.deleteProducto(producto.getId());  // Reemplaza con tu método real para eliminar el producto
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                finish();  // Cierra la actividad
            }
        });

        // Obtener el producto desde el intent
        if (getIntent().hasExtra("producto_id")) {
            int productoId = getIntent().getIntExtra("producto_id", -1);
            if (productoId != -1) {
                producto = productoController.getProducto(productoId); // Reemplaza con tu método real
                Toast.makeText(this, "producto: "+producto.getId(), Toast.LENGTH_SHORT).show();
                // Rellenar los campos con los valores actuales del producto
                editTextNombre.setText(producto.getNombre());
                editTextPrecio.setText(String.valueOf(producto.getPrecio()));
                editTextStock.setText(String.valueOf(producto.getStock()));
            }
        }

        // Manejar el click en el botón para guardar los cambios
        buttonGuardar.setOnClickListener(v -> {
            // Validar los campos
            if (TextUtils.isEmpty(editTextNombre.getText())) {
                editTextNombre.setError("El nombre es obligatorio");
                return;
            }

            if (TextUtils.isEmpty(editTextPrecio.getText())) {
                editTextPrecio.setError("El precio es obligatorio");
                return;
            }

            if (TextUtils.isEmpty(editTextStock.getText())) {
                editTextStock.setError("El stock es obligatorio");
                return;
            }

            // Actualizar los valores del producto
            producto.setNombre(editTextNombre.getText().toString());
            producto.setPrecio(Double.parseDouble(editTextPrecio.getText().toString()));
            producto.setStock(Integer.parseInt(editTextStock.getText().toString()));
            productoController.updateProducto(producto);

            finish();
        });
    }
}
