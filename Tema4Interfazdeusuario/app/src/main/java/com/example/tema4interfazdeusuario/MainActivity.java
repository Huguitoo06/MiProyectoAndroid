package com.example.tema4interfazdeusuario;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView miTexto = (TextView) findViewById(R.id.texto);

        miTexto.setText("Nuevo texto a mostrar");

        // Opcion 1 para cambiar el color
        miTexto.setTextColor(Color.parseColor("#0000FF"));

        // Opcion 2 para cambiar el color
        miTexto.setTextColor(Color.RED);

        // Cambio del texto a cursiva
        miTexto.setTypeface(null, Typeface.ITALIC);

        // Cambio el tamaño del texto
        miTexto.setTextSize(24);

        // Cambiar tipo de letra
        miTexto.setTypeface(Typeface.SANS_SERIF);
    }
}