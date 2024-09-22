package milo.probell.View.NotaVentaView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import milo.probell.Controller.ClienteController.ClienteController;
import milo.probell.Controller.DetalleNotaVentaController.DetalleNotaVentaController;
import milo.probell.Controller.NotaVentaController.NotaVentaController;
import milo.probell.Controller.ProductoController.ProductoController;
import milo.probell.Model.ClienteModel.Cliente;
import milo.probell.Model.DetalleNotaVentaModel.DetalleNotaVenta;
import milo.probell.Model.NotaVentaModel.NotaVenta;
import milo.probell.Model.ProductoModel.PDFGenerator;
import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class AgregarNotaVentaActivity extends AppCompatActivity {
    private boolean sw_delivery;
    private DetalleNotaVentaController detalleNotaVentaController;
    private CheckBox checkBoxDelivery;
    private List<Producto> listaProductos;
    private NotaVentaController notaVentaController;
    private Cliente clienteSeleccionado;
    private Spinner spinnerClientes, spinnerProductos;
    private ClienteController clienteController;
    private ProductoController productoController;
    private EditText editTextNombreCliente, editTextCedulaCliente, editTextCelularCliente;
    private EditText editTextCantidadProducto;
    private Button btnAgregarProducto, btnGuardarNotaVenta;
    private TableLayout tableLayoutNotaProductos;

    private Double montoTotal = 0.0;
    private int idProductoSeleccionado;
    private double precioUnitarioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_notal_venta);

        // Inicialización de controladores
        clienteController = new ClienteController(this);
        productoController = new ProductoController(this);
        notaVentaController = new NotaVentaController(this);
        detalleNotaVentaController = new DetalleNotaVentaController(this);

        // Inicialización de vistas
        initViews();

        // Cargar clientes y productos en sus respectivos spinners
        cargarClientesEnSpinner();
        cargarProductosEnSpinner();

        // Configuración del botón para agregar producto
        btnAgregarProducto.setOnClickListener(v -> agregarProductoATabla());

        // Configuración del CheckBox de delivery
        checkBoxDelivery.setOnClickListener(v -> {
            double montoActual = montoTotal;
            if (checkBoxDelivery.isChecked()) {
                sw_delivery = true;
                montoActual += 15;
            } else {
                sw_delivery = false;
                montoActual -= 15;
            }
            montoTotal = montoActual;
            // Actualiza el monto total en la UI si es necesario
            // txv_montoTotal.setText(String.valueOf(montoActual));
        });

        btnGuardarNotaVenta.setOnClickListener(v -> guardarNotaVenta());
    }

    private void initViews() {
        spinnerClientes = findViewById(R.id.spinnerClientes);
        editTextNombreCliente = findViewById(R.id.editTextNombreCliente);
        editTextCedulaCliente = findViewById(R.id.editTextCedulaCliente);
        editTextCelularCliente = findViewById(R.id.editTextCelularCliente);
        spinnerProductos = findViewById(R.id.spinnerProductos);
        editTextCantidadProducto = findViewById(R.id.editTextCantidadProducto);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
        btnGuardarNotaVenta = findViewById(R.id.btnGuardarNotaVenta);
        tableLayoutNotaProductos = findViewById(R.id.tableLayoutNotaProductos);
        checkBoxDelivery = findViewById(R.id.checkBoxDelivery);
    }

    private void cargarClientesEnSpinner() {
        List<Cliente> listaClientes = clienteController.getAllClientes();
        List<String> nombresClientes = new ArrayList<>();

        for (Cliente cliente : listaClientes) {
            nombresClientes.add(cliente.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresClientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);

        if (listaClientes.size() == 1) {
            spinnerClientes.setSelection(0);
            clienteSeleccionado = listaClientes.get(0);
            actualizarDatosCliente(clienteSeleccionado);
        }

        spinnerClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clienteSeleccionado = listaClientes.get(position);
                actualizarDatosCliente(clienteSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void actualizarDatosCliente(Cliente cliente) {
        editTextNombreCliente.setText(cliente.getNombre());
        editTextCedulaCliente.setText(cliente.getCedula());
        editTextCelularCliente.setText(cliente.getCelular());
    }

    private void guardarNotaVenta() {
        try {
            // Crear y guardar la Nota de Venta
            NotaVenta notaVenta = new NotaVenta();
            notaVenta.setIdCliente(clienteSeleccionado.getId());
            notaVenta.setFecha(LocalDate.now().toString());
            notaVenta.setMonto(montoTotal);

            NotaVenta nuevaNotaVenta = notaVentaController.addNotaVenta(notaVenta);

            // Inicializar el generador de PDF
            PDFGenerator pdfUtil = new PDFGenerator(this);

            // Obtener el detalle de la venta desde la tabla
            List<Vector<Object>> listaDetalle = obtenerDetalleVentaProductosDeTabla();
            if (listaDetalle.isEmpty()) {
                Toast.makeText(this, "La lista de productos está vacía.", Toast.LENGTH_LONG).show();
                return;
            }
            for (Vector<Object> detalle : listaDetalle) {
                // Extraer valores del detalle
                int idProducto = (int) detalle.get(0);
                int cantidad = (int) detalle.get(2);
                double precioVenta = (double) detalle.get(3);

                // Crear y guardar el Detalle de Nota de Venta
                DetalleNotaVenta detalleNotaVenta = new DetalleNotaVenta();
                detalleNotaVenta.setIdVenta(nuevaNotaVenta.getId());
                detalleNotaVenta.setIdProducto(idProducto);
                detalleNotaVenta.setCantidad(cantidad);
                detalleNotaVenta.setPreciov(precioVenta);
                detalleNotaVentaController.addDetalleNotaVenta(detalleNotaVenta);

                // Actualizar el stock del producto
                Producto producto = productoController.getProducto(idProducto);
                if (producto != null) {
                    producto.setStock(producto.getStock() - cantidad);
                    productoController.updateProducto(producto);
                } else {
                    Toast.makeText(this, "Producto no encontrado: " + idProducto, Toast.LENGTH_LONG).show();
                }

            }

            // Mostrar un mensaje con la cantidad de productos en la lista
            Toast.makeText(this, "Lista: " + listaDetalle.size(), Toast.LENGTH_LONG).show();

            // Generar y compartir la factura en PDF
            pdfUtil.generateAndShareFacturaPdf(this, clienteSeleccionado, nuevaNotaVenta.getId(), String.valueOf(montoTotal), listaDetalle, sw_delivery);

            // Limpiar campos después de completar la venta
            limpiarCampos();

        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            Toast.makeText(this, "Error al generar la nota de venta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public List<Vector<Object>> obtenerDetalleVentaProductosDeTabla() {
        List<Vector<Object>> filasDetalles = new ArrayList<>();

        Log.d("Tabla", "Número de filas: " + tableLayoutNotaProductos.getChildCount());

        for (int i = 0; i < tableLayoutNotaProductos.getChildCount(); i++) {
            TableRow fila = (TableRow) tableLayoutNotaProductos.getChildAt(i);
            TextView idTextView = (TextView) fila.getChildAt(0);
            TextView nombreTextView = (TextView) fila.getChildAt(1);
            TextView cantidadTextView = (TextView) fila.getChildAt(2);
            TextView precioTextView = (TextView) fila.getChildAt(3);
            TextView totalTextView = (TextView) fila.getChildAt(4);

            int id = Integer.parseInt(idTextView.getText().toString());
            String nombre = nombreTextView.getText().toString();
            int cantidad = Integer.parseInt(cantidadTextView.getText().toString());
            double precio = Double.parseDouble(precioTextView.getText().toString());
            double total = Double.parseDouble(totalTextView.getText().toString());

            Vector<Object> detalleVenta = new Vector<>();
            detalleVenta.add(id);
            detalleVenta.add(nombre);
            detalleVenta.add(cantidad);
            detalleVenta.add(precio);
            detalleVenta.add(total);
            filasDetalles.add(detalleVenta);
        }

        return filasDetalles;
    }


    private void cargarProductosEnSpinner() {
        listaProductos = productoController.getAllProductos();
        List<String> nombresProductos = new ArrayList<>();

        for (Producto producto : listaProductos) {
            nombresProductos.add(producto.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, nombresProductos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductos.setAdapter(adapter);

        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Producto productoSeleccionado = listaProductos.get(position);
                idProductoSeleccionado = productoSeleccionado.getId();
                precioUnitarioSeleccionado = productoSeleccionado.getPrecio();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void agregarProductoATabla() {
        String cantidadStr = editTextCantidadProducto.getText().toString();
        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "Ingrese la cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double total = precioUnitarioSeleccionado * cantidad;

        TableRow row = new TableRow(this);
        TextView textViewId = new TextView(this);
        textViewId.setText(String.valueOf(idProductoSeleccionado));
        textViewId.setPadding(8, 8, 8, 8);

        TextView textViewNombre = new TextView(this);
        textViewNombre.setText(spinnerProductos.getSelectedItem().toString());
        textViewNombre.setPadding(8, 8, 8, 8);

        TextView textViewCantidad = new TextView(this);
        textViewCantidad.setText(String.valueOf(cantidad));
        textViewCantidad.setPadding(8, 8, 8, 8);

        TextView textViewPrecio = new TextView(this);
        textViewPrecio.setText(String.valueOf(precioUnitarioSeleccionado));
        textViewPrecio.setPadding(8, 8, 8, 8);

        TextView textViewTotal = new TextView(this);
        textViewTotal.setText(String.valueOf(total));
        textViewTotal.setPadding(8, 8, 8, 8);

        row.addView(textViewId);
        row.addView(textViewNombre);
        row.addView(textViewCantidad);
        row.addView(textViewPrecio);
        row.addView(textViewTotal);

        tableLayoutNotaProductos.addView(row);
        montoTotal += total;
        // Actualiza el monto total en la UI si es necesario
        // txv_montoTotal.setText(String.valueOf(montoTotal));
    }

    private void limpiarCampos() {
        editTextNombreCliente.setText("");
        editTextCedulaCliente.setText("");
        editTextCelularCliente.setText("");
        editTextCantidadProducto.setText("");
        tableLayoutNotaProductos.removeAllViews();
        montoTotal = 0.0; // Reiniciar monto total
        // txv_montoTotal.setText("0"); // Reiniciar el monto total en la UI
    }
}
