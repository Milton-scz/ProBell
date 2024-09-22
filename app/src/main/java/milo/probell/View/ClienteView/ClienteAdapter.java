package milo.probell.View.ClienteView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import milo.probell.Model.ClienteModel.Cliente;
import milo.probell.R;
import milo.probell.View.ProductoView.EditarProductoViewActivity;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {
private Context context;

    private List<Cliente> clientes;

    public ClienteAdapter(Context context,List<Cliente> clientes) {
        this.context = context;
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = clientes.get(position);
        holder.textViewNombre.setText(cliente.getNombre());
        holder.textViewCedula.setText(String.format("Cédula: %s", cliente.getCedula()));
        holder.textViewCelular.setText(String.format("Celular: %s", cliente.getCelular()));
        holder.textViewDireccion.setText(String.format("Dirección: %s", cliente.getDireccion()));

        // Evento para hacer click en un producto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarClienteViewActivity.class);
            intent.putExtra("cliente_id",  cliente.getId());
            ((Activity) context).startActivityForResult(intent, 100);
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public void updateClientes(List<Cliente> nuevosClientes) {
        this.clientes = nuevosClientes;
        notifyDataSetChanged();
    }

    static class ClienteViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;
        TextView textViewCedula;
        TextView textViewCelular;
        TextView textViewDireccion;

        ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewCedula = itemView.findViewById(R.id.textViewCedula);
            textViewCelular = itemView.findViewById(R.id.textViewCelular);
            textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
        }
    }
}
