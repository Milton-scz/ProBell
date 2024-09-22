package milo.probell.View.ClienteView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import milo.probell.Model.ClienteModel.Cliente;
import milo.probell.Model.ClienteModel.ClienteDatabaseHelper;
import milo.probell.R;

public class AgregarClienteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etNombre, etCedula, etCelular, etDireccion;
    private Button btnGuardarCliente;
    private LatLng markerLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cliente);

        // Inicialización de vistas
        etNombre = findViewById(R.id.etNombre);
        etCedula = findViewById(R.id.etCedula);
        etCelular = findViewById(R.id.etCelular);
        etDireccion = findViewById(R.id.etDireccion);
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);

        // Configuración del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Configuración del botón para guardar el cliente
        btnGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCliente();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng defaultLocation = new LatLng(-34, 151); // Ubicación por defecto
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Limpiar marcador anterior
                mMap.clear();
                // Añadir nuevo marcador
                mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
                markerLatLng = latLng;
                // Obtener la dirección de la ubicación seleccionada
                obtenerDireccion(latLng);
            }
        });
    }

    private void obtenerDireccion(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String googleMapsLink = "https://www.google.com/maps?q=" + latLng.latitude + "," + latLng.longitude;
                etDireccion.setText(googleMapsLink);
            } else {
                etDireccion.setText("Dirección no encontrada");
            }
        } catch (IOException e) {
            e.printStackTrace();
            etDireccion.setText("Error al obtener dirección");
        }
    }



    private void guardarCliente() {
        String nombre = etNombre.getText().toString();
        String cedula = etCedula.getText().toString();
        String celular = etCelular.getText().toString();
        String direccion = etDireccion.getText().toString();

        if (nombre.isEmpty() || cedula.isEmpty() || celular.isEmpty() || direccion.isEmpty() || markerLatLng == null) {
            // Mostrar mensaje de error o hacer algo si faltan campos
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCedula(cedula);
        cliente.setCelular(celular);
        cliente.setDireccion(direccion);


        ClienteDatabaseHelper clienteDatabaseHelper = new ClienteDatabaseHelper(this);
        clienteDatabaseHelper.addCliente(cliente);

        // Regresar a la actividad anterior
        finish();
    }
}
