package milo.probell.Controller.NotaVentaController;

import android.content.Context;
import android.widget.Toast;

import milo.probell.Model.NotaVentaModel.NotaVenta;
import milo.probell.Model.NotaVentaModel.NotaVentaDatabaseHelper;


import java.util.List;

public class NotaVentaController {

    private NotaVentaDatabaseHelper notaVentaHelper;
    private Context context;

    public NotaVentaController(Context context) {
        this.context = context;
        notaVentaHelper = new NotaVentaDatabaseHelper(context);
    }

    // Agregar una nueva nota de venta
    public NotaVenta addNotaVenta(NotaVenta notaVenta) {
        try {
            notaVentaHelper.addNotaVenta(notaVenta);
            Toast.makeText(context, "Nota de venta añadida con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al añadir la nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return notaVenta;
    }

    // Obtener una nota de venta por ID
    public NotaVenta getNotaVenta(int id) {
        return notaVentaHelper.getNotaVenta(id);
    }

    // Obtener todas las notas de venta
    public List<NotaVenta> getAllNotaVentas() {
        return notaVentaHelper.getAllNotaVentas();
    }

    // Actualizar una nota de venta
    public void updateNotaVenta(NotaVenta notaVenta) {
        try {
            int result = notaVentaHelper.updateNotaVenta(notaVenta);
            if (result > 0) {
                Toast.makeText(context, "Nota de venta actualizada con éxito.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al actualizar la nota de venta.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al actualizar la nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Eliminar una nota de venta
    public void deleteNotaVenta(int id) {
        try {
            notaVentaHelper.deleteNotaVenta(id);
            Toast.makeText(context, "Nota de venta eliminada con éxito.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al eliminar la nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}