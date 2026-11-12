package com.example.tema5;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        // Configurar el ListView y asignarle el adaptador
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        // Buscar el TextView
        TextView texto = findViewById(R.id.texto);

        // Detectar cuando pulsamos un elemento
        listView.setOnItemClickListener((parent, view, position, id) -> {

            if (position == 0) {
                texto.setText("Has pulsado el elemento 1");
            } else if (position == 1) {
                texto.setText("Has pulsado el elemento 2");
            } else if (position == 2) {
                texto.setText("Has pulsado el elemento 3");
            } else if (position == 3) {
                texto.setText("Has pulsado el elemento 4");
            }

        });
    }
}