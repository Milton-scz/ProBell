package milo.probell.View.NotaVentaView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import milo.probell.Model.NotaVentaModel.NotaVenta;
import milo.probell.R;

public class NotaVentaAdapter extends RecyclerView.Adapter<NotaVentaAdapter.NotaVentaViewHolder> {

    private List<NotaVenta> notaVentas;

    public NotaVentaAdapter(List<NotaVenta> notaVentas) {
        this.notaVentas = notaVentas;
    }

    @NonNull
    @Override
    public NotaVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota_venta, parent, false);
        return new NotaVentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaVentaViewHolder holder, int position) {
        NotaVenta notaVenta = notaVentas.get(position);
        holder.textViewNotaVentaId.setText("ID Nota Venta: " + notaVenta.getId());
        holder.textViewClienteId.setText("ID Cliente: " + notaVenta.getIdCliente());
        holder.textViewMontoTotal.setText("Monto Total: $" + notaVenta.getMonto());
    }

    @Override
    public int getItemCount() {
        return notaVentas.size();
    }

    public void updateNotaVentas(List<NotaVenta> nuevaListaNotaVentas) {
        this.notaVentas = nuevaListaNotaVentas;
        notifyDataSetChanged();
    }

    public static class NotaVentaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNotaVentaId;
        TextView textViewClienteId;
        TextView textViewMontoTotal;

        public NotaVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotaVentaId = itemView.findViewById(R.id.textViewNotaVentaId);
            textViewClienteId = itemView.findViewById(R.id.textViewClienteId);
            textViewMontoTotal = itemView.findViewById(R.id.textViewMontoTotal);
        }
    }
}
