package milo.probell.View.DetalleNotaVentaView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import milo.probell.Model.DetalleNotaVentaModel.DetalleNotaVenta;
import milo.probell.R;

public class DetalleNotaVentaAdapter extends RecyclerView.Adapter<DetalleNotaVentaAdapter.ViewHolder> {

    private List<DetalleNotaVenta> listaDetalleNotaVenta;

    public DetalleNotaVentaAdapter(List<DetalleNotaVenta> listaDetalleNotaVenta) {
        this.listaDetalleNotaVenta = listaDetalleNotaVenta;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detalle_nota_venta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetalleNotaVenta detalle = listaDetalleNotaVenta.get(position);
        holder.tvProducto.setText("ID Producto: " + detalle.getIdProducto());
        holder.tvCantidad.setText("Cantidad: " + detalle.getCantidad());
        holder.tvPrecio.setText("Precio: " + detalle.getPreciov());
    }

    @Override
    public int getItemCount() {
        return listaDetalleNotaVenta.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProducto, tvCantidad, tvPrecio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
