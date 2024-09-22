package milo.probell.Controller.DetalleNotaVentaController;

import android.content.Context;
import android.widget.Toast;
import java.util.List;
import milo.probell.Model.DetalleNotaVentaModel.DetalleNotaVenta;
import milo.probell.Model.DetalleNotaVentaModel.DetalleNotaVentaDatabaseHelper;


public class DetalleNotaVentaController {

    private DetalleNotaVentaDatabaseHelper detalleNotaVentaHelper;
    private Context context;

    public DetalleNotaVentaController(Context context) {
        this.context = context;
        detalleNotaVentaHelper = new DetalleNotaVentaDatabaseHelper(context);
    }

    // Agregar un nuevo detalle de nota de venta
    public DetalleNotaVenta addDetalleNotaVenta(DetalleNotaVenta detalleNotaVenta) {
        try {
            detalleNotaVentaHelper.addDetalleNotaVenta(detalleNotaVenta);
            Toast.makeText(context, "Detalle de nota de venta añadido con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al añadir el detalle de nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return detalleNotaVenta;
    }

    // Obtener un detalle de nota de venta por ID
    public DetalleNotaVenta getDetalleNotaVenta(int id) {
        return detalleNotaVentaHelper.getDetalleNotaVenta(id);
    }

    // Obtener todos los detalles de nota de venta
    public List<DetalleNotaVenta> getAllDetallesNotaVenta() {
        return detalleNotaVentaHelper.getAllDetallesNotaVenta();
    }

    // Actualizar un detalle de nota de venta
    public void updateDetalleNotaVenta(DetalleNotaVenta detalleNotaVenta) {
        try {
            int result = detalleNotaVentaHelper.updateDetalleNotaVenta(detalleNotaVenta);
            if (result > 0) {
                Toast.makeText(context, "Detalle de nota de venta actualizado con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar el detalle de nota de venta.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al actualizar el detalle de nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Eliminar un detalle de nota de venta
    public void deleteDetalleNotaVenta(int id) {
        try {
            detalleNotaVentaHelper.deleteDetalleNotaVenta(id);
            Toast.makeText(context, "Detalle de nota de venta eliminado con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al eliminar el detalle de nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
