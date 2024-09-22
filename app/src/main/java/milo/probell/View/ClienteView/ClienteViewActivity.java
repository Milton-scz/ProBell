package milo.probell.View.ClienteView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import milo.probell.Controller.ClienteController.ClienteController;
import milo.probell.R;

public class ClienteViewActivity extends AppCompatActivity {

    private ClienteController clienteController;
    private RecyclerView recyclerViewClientes;
    private Button btnAgregarCliente;
    private ClienteAdapter clienteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_view);

        // Inicialización del controller y vista
        clienteController = new ClienteController(this);
        recyclerViewClientes = findViewById(R.id.recyclerViewClientes);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);

        // Configuración del RecyclerView
        recyclerViewClientes.setLayoutManager(new LinearLayoutManager(this));
        clienteAdapter = new ClienteAdapter(this,clienteController.getAllClientes());
        recyclerViewClientes.setAdapter(clienteAdapter);

        // Configuración del botón para agregar un nuevo cliente
        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClienteViewActivity.this, AgregarClienteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de clientes al volver a la actividad
        clienteAdapter.updateClientes(clienteController.getAllClientes());
    }
}
