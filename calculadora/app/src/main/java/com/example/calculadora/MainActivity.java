package com.example.calculadora;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String expresion = "0";
    boolean resultadoMostrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        display = findViewById(R.id.display);


        int[] numeros = {R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3, R.id.boton4, R.id.boton5, R.id.boton6, R.id.boton7, R.id.boton8, R.id.boton9};

        for (int id : numeros) {
            Button boton = findViewById(id);
            boton.setOnClickListener(v -> escribirNumero(boton.getText().toString()));
        }


        findViewById(R.id.sumar).setOnClickListener(v -> escribirOperacion("+"));
        findViewById(R.id.restar).setOnClickListener(v -> escribirOperacion("-"));
        findViewById(R.id.multiplicar).setOnClickListener(v -> escribirOperacion("*"));
        findViewById(R.id.dividir).setOnClickListener(v -> escribirOperacion("/"));

        // Coma decimal
        findViewById(R.id.coma).setOnClickListener(v -> escribirComa());


        findViewById(R.id.botonC).setOnClickListener(v -> {
            if (expresion.length() > 1) {
                expresion = expresion.substring(0, expresion.length() - 1);
            } else {
                expresion = "0";
            }
            resultadoMostrado = false;
            actualizarDisplay();
        });


        findViewById(R.id.botonAC).setOnClickListener(v -> {
            expresion = "0";
            resultadoMostrado = false;
            actualizarDisplay();
        });


        findViewById(R.id.igual).setOnClickListener(v -> calcular());
    }


    private void escribirNumero(String numero) {
        if (resultadoMostrado) {
            expresion = "0";
            resultadoMostrado = false;
        }

        if (expresion.equals("0")) {
            expresion = numero;
        } else {
            int inicio = expresion.length() - 1;

            while (inicio >= 0 && (Character.isDigit(expresion.charAt(inicio)) || expresion.charAt(inicio) == ',')) {
                inicio--;
            }

            String actual = expresion.substring(inicio + 1);

            if (actual.equals("0")) {
                expresion = expresion.substring(0, inicio + 1) + numero;
            } else {
                expresion += numero;
            }
        }

        actualizarDisplay();
    }


    private void escribirOperacion(String operacion) {
        if (resultadoMostrado) {
            resultadoMostrado = false;
        }

        char ultimo = expresion.charAt(expresion.length() - 1);

        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            expresion = expresion.substring(0, expresion.length() - 1);
        }

        expresion += operacion;
        actualizarDisplay();
    }


    private void escribirComa() {
        int inicio = expresion.length() - 1;

        while (inicio >= 0 && (Character.isDigit(expresion.charAt(inicio)) || expresion.charAt(inicio) == ',')) {
            inicio--;
        }

        String actual = expresion.substring(inicio + 1);

        if (!actual.contains(",")) {
            expresion += actual.isEmpty() ? "0," : ",";
            resultadoMostrado = false;
            actualizarDisplay();
        }
    }

    private void calcular() {
        try {
            String texto = expresion.replace(',', '.');

            if (texto.endsWith("+") || texto.endsWith("-") || texto.endsWith("*") || texto.endsWith("/")) {
                return;
            }

            ArrayList<Double> numeros = new ArrayList<>();
            ArrayList<Character> operaciones = new ArrayList<>();
            StringBuilder numero = new StringBuilder();

            for (int i = 0; i < texto.length(); i++) {
                char c = texto.charAt(i);

                if (Character.isDigit(c) || c == '.') {
                    numero.append(c);
                } else {
                    if (numero.length() == 0) return;

                    numeros.add(Double.parseDouble(numero.toString()));
                    numero.setLength(0);
                    operaciones.add(c);
                }
            }

            if (numero.length() == 0) return;
            numeros.add(Double.parseDouble(numero.toString()));


            for (int i = 0; i < operaciones.size(); i++) {
                char op = operaciones.get(i);

                if (op == '*' || op == '/') {
                    double a = numeros.get(i);
                    double b = numeros.get(i + 1);

                    if (op == '/' && b == 0) {
                        Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double resultado = (op == '*') ? a * b : a / b;

                    numeros.set(i, resultado);
                    numeros.remove(i + 1);
                    operaciones.remove(i);
                    i--;
                }
            }


            double resultado = numeros.get(0);

            for (int i = 0; i < operaciones.size(); i++) {
                if (operaciones.get(i) == '+') {
                    resultado += numeros.get(i + 1);
                } else {
                    resultado -= numeros.get(i + 1);
                }
            }

            if (!Double.isFinite(resultado)) return;

            if (resultado == (long) resultado) {
                expresion = String.valueOf((long) resultado);
            } else {
                expresion = String.valueOf(resultado).replace('.', ',');
            }

            resultadoMostrado = true;
            actualizarDisplay();

        } catch (Exception e) {
            Toast.makeText(this, "Operacion no valida", Toast.LENGTH_SHORT).show();
        }
    }


    private void actualizarDisplay() {
        display.setText(expresion.replace('*', '×').replace('/', '÷'));
    }
}
