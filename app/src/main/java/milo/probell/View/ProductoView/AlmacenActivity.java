package milo.probell.View.ProductoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import milo.probell.Controller.ProductoController.ProductoController;
import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class AlmacenActivity extends AppCompatActivity {

    private ProductoController productoController;
    private RecyclerView recyclerViewAlmacen;
    private AlmacenProductoAdapter productoAdapter;
    private List<Producto> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen_view);

        productoController = new ProductoController(this);
        recyclerViewAlmacen = findViewById(R.id.recyclerViewAlmacen);
        recyclerViewAlmacen.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar el adaptador con una lista vacía o la lista de productos actual
        listaProductos = productoController.getAllProductos();
        productoAdapter = new AlmacenProductoAdapter(this, listaProductos);
        recyclerViewAlmacen.setAdapter(productoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de productos cada vez que la actividad se reanuda
        listaProductos = productoController.getAllProductos();
        productoAdapter.setProductos(listaProductos); // Método en el adaptador para actualizar la lista de productos
        productoAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
