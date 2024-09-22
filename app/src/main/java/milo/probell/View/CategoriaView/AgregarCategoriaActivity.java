package milo.probell.View.CategoriaView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import milo.probell.Controller.CategoriaController.CategoriaController;
import milo.probell.Model.CategoriaModel.Categoria;

import milo.probell.R;

public class AgregarCategoriaActivity extends AppCompatActivity {

    private EditText edtNombreCategoria;
    private Button btnGuardarCategoria;
    private CategoriaController categoriaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        // Inicialización del controller y vista
        categoriaController = new CategoriaController(this);
        edtNombreCategoria = findViewById(R.id.edtNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        // Configuración del botón para guardar la nueva categoría
        btnGuardarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = edtNombreCategoria.getText().toString().trim();

                if (nombre.isEmpty()) {
                    Toast.makeText(AgregarCategoriaActivity.this, "El nombre de la categoría no puede estar vacío.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Categoria nuevaCategoria = new Categoria();
                nuevaCategoria.setNombre(nombre);

                categoriaController.addCategoria(nuevaCategoria);
                finish(); // Cierra la actividad y regresa al dashboard
            }
        });
    }
}
