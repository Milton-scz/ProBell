package milo.probell.View.ProductoView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import milo.probell.Controller.ProductoController.ProductoController;
import milo.probell.Model.ProductoModel.PDFGenerator;
import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class ProductoViewActivity extends AppCompatActivity {

    private ProductoController productoController;
    private RecyclerView recyclerViewProductos;
    private Button btnAgregarProducto, btnMostrarAlmacen;;
    private Button btnGenerarPDF;
    private ProductoAdapter productoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_view);

        // Inicialización del controller y vista
        productoController = new ProductoController(this);
       // productoController.eliminarTablaProductos();
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnMostrarAlmacen = findViewById(R.id.btnMostrarAlmacen);
        btnGenerarPDF = findViewById(R.id.btnGenerarPDF);

        // Configuración del RecyclerView
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador con la lista de productos y el contexto
        productoAdapter = new ProductoAdapter(productoController.getAllProductos());
        recyclerViewProductos.setAdapter(productoAdapter);

        // Inicialmente ocultar el botón de generar PDF
        btnGenerarPDF.setVisibility(View.GONE);

        productoAdapter.setOnSelectionChangeListener(selectedCount -> {
            if (selectedCount > 0) {
                btnGenerarPDF.setVisibility(View.VISIBLE);
            } else {
                btnGenerarPDF.setVisibility(View.GONE);
            }
        });

        // Configuración del botón para agregar nuevo producto
        btnAgregarProducto.setOnClickListener(v -> {
            Intent intent = new Intent(ProductoViewActivity.this, AgregarProductoViewActivity.class);
            startActivity(intent);
        });
        // Configurar botón para mostrar el almacén
        btnMostrarAlmacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoViewActivity.this, AlmacenActivity.class);
                // Pasar la lista de productos a la nueva Activity
                startActivity(intent);
            }
        });
        // Configuración del botón para generar PDF
        btnGenerarPDF.setOnClickListener(v -> {
            List<Producto> selectedProductos = productoAdapter.getSelectedProductos();
            if (selectedProductos.isEmpty()) {
                Toast.makeText(ProductoViewActivity.this, "No hay productos seleccionados.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamar al método para generar el PDF
            try {
                generarPDF(selectedProductos);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de productos al volver a la actividad
        productoAdapter.updateProductos(productoController.getAllProductos());
    }

   public void generarPDF(List<Producto> productos) throws IOException {
        // Aquí puedes llamar a un método de una clase separada que genere el PDF
        PDFGenerator pdfGenerator = new PDFGenerator(this);
        pdfGenerator.compartirCatalogo(productos);
    }
}
