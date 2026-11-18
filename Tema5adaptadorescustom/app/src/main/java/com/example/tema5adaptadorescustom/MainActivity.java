package com.example.tema5adaptadorescustom;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear el array con los datos
        Datos[] datos = {
                new Datos("Android", "Desarrollo de aplicaciones"),
                new Datos("Java", "Programación"),
                new Datos("XML", "Diseño de interfaces"),
                new Datos("Python", "Lenguaje de programación"),
                new Datos("HTML", "Diseño de páginas web")
        };

        // Localizar el ListView
        ListView lista = findViewById(R.id.listView);

        // Crear el adaptador
        Adaptador adaptador = new Adaptador(this, datos);

        // Asignar el adaptador al ListView
        lista.setAdapter(adaptador);
    }
}
