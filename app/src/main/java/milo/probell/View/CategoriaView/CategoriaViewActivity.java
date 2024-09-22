package milo.probell.View.CategoriaView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import milo.probell.Controller.CategoriaController.CategoriaController;
import milo.probell.R;

public class CategoriaViewActivity extends AppCompatActivity {

    private CategoriaController categoriaController;
    private RecyclerView recyclerViewCategorias;
    private Button btnAgregarCategoria;
    private CategoriaAdapter categoriaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_view);

        // Inicialización del controller y vista
        categoriaController = new CategoriaController(this);
        recyclerViewCategorias = findViewById(R.id.recyclerViewCategorias);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);

        // Configuración del RecyclerView
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(this));
        categoriaAdapter = new CategoriaAdapter(categoriaController.getAllCategorias());
        recyclerViewCategorias.setAdapter(categoriaAdapter);

        // Configuración del botón para agregar nueva categoría
        btnAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriaViewActivity.this, AgregarCategoriaActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de categorías al volver a la actividad
        categoriaAdapter.updateCategorias(categoriaController.getAllCategorias());
    }
}
