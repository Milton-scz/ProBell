package milo.probell.Controller.ClienteController;

import android.content.Context;
import android.widget.Toast;

import milo.probell.Model.ClienteModel.Cliente;
import milo.probell.Model.ClienteModel.ClienteDatabaseHelper;


import java.util.List;

public class ClienteController {

    private ClienteDatabaseHelper clienteHelper;
    private Context context;

    public ClienteController(Context context) {
        this.context = context;
        clienteHelper = new ClienteDatabaseHelper(context);
    }

    // Agregar un nuevo cliente
    public void addCliente(Cliente cliente) {
        try {
            clienteHelper.addCliente(cliente);
            Toast.makeText(context, "Cliente añadido con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al añadir el cliente: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Obtener un cliente por ID
    public Cliente getCliente(int id) {
        return clienteHelper.getCliente(id);
    }

    // Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        return clienteHelper.getAllClientes();
    }

    // Actualizar un cliente
    public void updateCliente(Cliente cliente) {
        try {
            int result = clienteHelper.updateCliente(cliente);
            if (result > 0) {
                Toast.makeText(context, "Cliente actualizado con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar el cliente.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al actualizar el cliente: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Eliminar un cliente
    public void deleteCliente(int id) {
        try {
            clienteHelper.deleteCliente(id);
            Toast.makeText(context, "Cliente eliminado con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al eliminar el cliente: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
