package milo.probell.View.DetalleNotaVentaView;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import milo.probell.Controller.DetalleNotaVentaController.DetalleNotaVentaController;
import milo.probell.Model.DetalleNotaVentaModel.DetalleNotaVenta;
import milo.probell.R;

public class DetalleNotaVentaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDetalleNotaVenta;
    private DetalleNotaVentaController detalleNotaVentaController;
    private DetalleNotaVentaAdapter detalleNotaVentaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nota_venta);

        // Inicializar el controlador y el RecyclerView
        detalleNotaVentaController = new DetalleNotaVentaController(this);
        recyclerViewDetalleNotaVenta = findViewById(R.id.recyclerViewDetalleNotaVenta);
        recyclerViewDetalleNotaVenta.setLayoutManager(new LinearLayoutManager(this));

        // Cargar todos los detalles de la nota de venta
        cargarTodosLosDetallesNotaVenta(); // Ahora esta línea está activa
    }

    private void cargarTodosLosDetallesNotaVenta() {
        List<DetalleNotaVenta> listaDetalles = detalleNotaVentaController.getAllDetallesNotaVenta();

        if (listaDetalles != null && !listaDetalles.isEmpty()) {
            // Inicializar el adaptador y establecerlo en el RecyclerView
            detalleNotaVentaAdapter = new DetalleNotaVentaAdapter(listaDetalles);
            recyclerViewDetalleNotaVenta.setAdapter(detalleNotaVentaAdapter);
        } else {
            Toast.makeText(this, "No hay detalles de nota de venta para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
}
