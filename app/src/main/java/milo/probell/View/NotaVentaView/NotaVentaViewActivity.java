package milo.probell.View.NotaVentaView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import milo.probell.Controller.NotaVentaController.NotaVentaController;
import milo.probell.R;
import milo.probell.View.DetalleNotaVentaView.DetalleNotaVentaActivity;

public class NotaVentaViewActivity extends AppCompatActivity {

    private NotaVentaController notaVentaController;
    private RecyclerView recyclerViewNotaVentas;
    private Button btnCrearNotaVenta, btnVerDetalleNotaVenta;
    private NotaVentaAdapter notaVentaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_venta_view);

        // Inicialización del controller y vista
        notaVentaController = new NotaVentaController(this);
        recyclerViewNotaVentas = findViewById(R.id.recyclerViewNotaVentas);
        btnCrearNotaVenta = findViewById(R.id.btnCrearNotaVenta);
        btnVerDetalleNotaVenta = findViewById(R.id.btnVerDetalleNotaVenta);

        // Configuración del RecyclerView
        recyclerViewNotaVentas.setLayoutManager(new LinearLayoutManager(this));
        notaVentaAdapter = new NotaVentaAdapter(notaVentaController.getAllNotaVentas());
        recyclerViewNotaVentas.setAdapter(notaVentaAdapter);

        // Configuración del botón para agregar nueva nota de venta
        btnCrearNotaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotaVentaViewActivity.this, AgregarNotaVentaActivity.class);
               startActivity(intent);
            }
        });



        btnVerDetalleNotaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia una nueva actividad que muestre los detalles de la nota de venta
                Intent intent = new Intent(NotaVentaViewActivity.this, DetalleNotaVentaActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de notas de venta al volver a la actividad
        notaVentaAdapter.updateNotaVentas(notaVentaController.getAllNotaVentas());
    }
}
