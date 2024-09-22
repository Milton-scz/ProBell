package milo.probell.Controller.CategoriaController;

import android.content.Context;
import android.widget.Toast;

import milo.probell.Model.CategoriaModel.Categoria;
import milo.probell.Model.CategoriaModel.CategoriaDatabaseHelper;


import java.util.List;

public class CategoriaController {

    private CategoriaDatabaseHelper categoriaHelper;
    private Context context;

    public CategoriaController(Context context) {
        this.context = context;
        categoriaHelper = new CategoriaDatabaseHelper(context);
    }

    // Agregar una nueva categoría
    public void addCategoria(Categoria categoria) {
        try {
            categoriaHelper.addCategoria(categoria);
            Toast.makeText(context, "Categoría añadida con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al añadir la categoría: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Obtener una categoría por ID
    public Categoria getCategoria(int id) {
        return categoriaHelper.getCategoria(id);
    }

    // Obtener todas las categorías
    public List<Categoria> getAllCategorias() {
        return categoriaHelper.getAllCategorias();
    }

    // Actualizar una categoría
    public void updateCategoria(Categoria categoria) {
        try {
            int result = categoriaHelper.updateCategoria(categoria);
            if (result > 0) {
                Toast.makeText(context, "Categoría actualizada con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar la categoría.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al actualizar la categoría: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Eliminar una categoría
    public void deleteCategoria(int id) {
        try {
            categoriaHelper.deleteCategoria(id);
            Toast.makeText(context, "Categoría eliminada con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al eliminar la categoría: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}