package milo.probell.View.CategoriaView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import milo.probell.Model.CategoriaModel.Categoria;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> categorias;

    public CategoriaAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.textViewNombre.setText(categoria.getNombre());
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public void updateCategorias(List<Categoria> nuevasCategorias) {
        this.categorias = nuevasCategorias;
        notifyDataSetChanged();
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombre;

        CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(android.R.id.text1);
        }
    }
}
