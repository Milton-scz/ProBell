package milo.probell.View.ClienteView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import milo.probell.Controller.ClienteController.ClienteController;
import milo.probell.Model.ClienteModel.Cliente;
import milo.probell.R;

public class EditarClienteViewActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextCedula, editTextCelular, editTextDireccion;
    private Button buttonGuardar, buttonEliminar;
    private Cliente cliente;
    private ClienteController clienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente_view);

        // Obtener los componentes del layout
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCedula = findViewById(R.id.editTextCedula);
        editTextCelular = findViewById(R.id.editTextCelular);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonEliminar = findViewById(R.id.buttonEliminar);
        clienteController = new ClienteController(this);

        buttonEliminar.setOnClickListener(v -> {
            if (cliente != null) {
                clienteController.deleteCliente(cliente.getId());
                Toast.makeText(this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                finish();  // Cierra la actividad
            }
        });
        // Obtener el cliente desde el intent
        if (getIntent().hasExtra("cliente_id")) {
            int clienteId = getIntent().getIntExtra("cliente_id", -1);
            if (clienteId != -1) {
                cliente = clienteController.getCliente(clienteId); // Reemplaza con tu método real
                Toast.makeText(this, "Cliente ID: " + cliente.getId(), Toast.LENGTH_SHORT).show();
                // Rellenar los campos con los valores actuales del cliente
                editTextNombre.setText(cliente.getNombre());
                editTextCedula.setText(cliente.getCedula());
                editTextCelular.setText(cliente.getCelular());
                editTextDireccion.setText(cliente.getDireccion());
            }
        }


        buttonGuardar.setOnClickListener(v -> {
            // Validar los campos
            if (TextUtils.isEmpty(editTextNombre.getText())) {
                editTextNombre.setError("El nombre es obligatorio");
                return;
            }

            if (TextUtils.isEmpty(editTextCedula.getText())) {
                editTextCedula.setError("La cédula es obligatoria");
                return;
            }

            if (TextUtils.isEmpty(editTextCelular.getText())) {
                editTextCelular.setError("El celular es obligatorio");
                return;
            }

            if (TextUtils.isEmpty(editTextDireccion.getText())) {
                editTextDireccion.setError("La dirección es obligatoria");
                return;
            }

            // Actualizar los valores del cliente
            cliente.setNombre(editTextNombre.getText().toString());
            cliente.setCedula(editTextCedula.getText().toString());
            cliente.setCelular(editTextCelular.getText().toString());
            cliente.setDireccion(editTextDireccion.getText().toString());
            clienteController.updateCliente(cliente);

            finish();
        });
    }
}
