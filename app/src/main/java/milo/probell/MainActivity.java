package milo.probell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import milo.probell.View.CategoriaView.CategoriaViewActivity;
import milo.probell.View.ClienteView.ClienteViewActivity;
import milo.probell.View.NotaVentaView.NotaVentaViewActivity;
import milo.probell.View.ProductoView.ProductoViewActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnCliente;
    private LinearLayout btnProducto;
    private LinearLayout btnCategoria;
    private LinearLayout btnVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar los botones
        btnCliente = findViewById(R.id.btnCliente);
        btnProducto = findViewById(R.id.btnProducto);
        btnCategoria = findViewById(R.id.btnCategoria);
        btnVenta = findViewById(R.id.btnVenta);

        // Configurar listeners para los botones
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Cliente
                Intent intent = new Intent(MainActivity.this, ClienteViewActivity.class);
                startActivity(intent);
            }
        });

        btnProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Producto
                Intent intent = new Intent(MainActivity.this, ProductoViewActivity.class);
                startActivity(intent);
            }
        });

        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Categoría
                Intent intent = new Intent(MainActivity.this, CategoriaViewActivity.class);
                startActivity(intent);
            }
        });

        btnVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de Venta
                Intent intent = new Intent(MainActivity.this, NotaVentaViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
