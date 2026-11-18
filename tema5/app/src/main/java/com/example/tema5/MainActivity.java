package com.example.tema5;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Datos de ejemplo para la lista
        List<String> data = Arrays.asList("Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4");

        // Configurar el GridView y asignarle el adaptador
        GridView gridView = findViewById(R.id.gridView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        gridView.setAdapter(adapter);

        // Buscar el TextView
        TextView texto = findViewById(R.id.texto);

        // Detectar cuando pulsamos
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Obtener el texto del elemento pulsado
                String elemento = (String) parent.getItemAtPosition(position);

                // Mostrar el texto
                texto.setText(elemento);
            }
        });

    }
}