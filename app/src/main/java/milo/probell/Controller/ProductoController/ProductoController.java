package milo.probell.Controller.ProductoController;

import android.content.Context;
import android.widget.Toast;

import java.util.List;

import milo.probell.Model.ProductoModel.Producto;
import milo.probell.Model.ProductoModel.ProductoDataBaseHelper;

public class ProductoController {
    private Context context;
    private ProductoDataBaseHelper dbHelper;

    public ProductoController(Context context) {
        this.context = context;
        dbHelper = new ProductoDataBaseHelper(context);
    }

    public void agregarProducto(String nombre, String descripcion, double precio, int stock, int categoriaId,byte[] uriproducto) {
        Producto producto = new Producto(0, nombre, descripcion, precio, stock, categoriaId, uriproducto);
        dbHelper.agregarProducto(producto);
    }

    public Producto getProducto(int id){
        return  dbHelper.getProducto(id);
    }
    public void deleteProducto(int id) {
        try {
            dbHelper.deleteProducto(id);
            Toast.makeText(context, "Producto eliminado con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al eliminar el producto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void updateProducto(Producto producto) {
        try {
            int result = dbHelper.updateProducto(producto);
            if (result > 0) {
                Toast.makeText(context, "Producto actualizado con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar el producto.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al actualizar el producto: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public List<Producto> getAllProductos() {
        return dbHelper.getAllProductos();
    }
   // public  void eliminarTablaProductos(){
    //    dbHelper.eliminarTablaProductos();
    //}
}
