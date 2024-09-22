package milo.probell.View.ProductoView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private List<Producto> productoList;
    private List<Producto> selectedProductos = new ArrayList<>();
    private OnSelectionChangeListener onSelectionChangeListener;

    public ProductoAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(String.valueOf(producto.getPrecio()));

        // Cargar imagen desde byte[]
        if (producto.getImagen() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImagen(), 0, producto.getImagen().length);
            holder.imageViewProducto.setImageBitmap(bitmap);
        } else {
            holder.imageViewProducto.setImageResource(R.drawable.ic_launcher_background); // Imagen por defecto si no hay imagen
        }

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedProductos.contains(producto));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedProductos.contains(producto)) {
                    selectedProductos.add(producto);
                }
            } else {
                selectedProductos.remove(producto);
            }
            if (onSelectionChangeListener != null) {
                onSelectionChangeListener.onSelectionChanged(selectedProductos.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public void updateProductos(List<Producto> productos) {
        this.productoList = productos;
        notifyDataSetChanged();
    }

    public List<Producto> getSelectedProductos() {
        return selectedProductos;
    }

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.onSelectionChangeListener = listener;
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(int selectedCount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProducto;
        TextView tvNombre, tvPrecio;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
