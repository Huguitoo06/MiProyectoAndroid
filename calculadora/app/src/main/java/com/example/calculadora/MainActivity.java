package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String expresion = "0";
    boolean resultadoMostrado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Botones numéricos
        int[] numeros = {
                R.id.boton0, R.id.boton1, R.id.boton2,
                R.id.boton3, R.id.boton4, R.id.boton5,
                R.id.boton6, R.id.boton7, R.id.boton8, R.id.boton9
        };

        for (int id : numeros) {
            Button boton = findViewById(id);

            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    escribirNumero(boton.getText().toString());
                }
            });
        }

        // Botones de operaciones
        ponerOperacion(R.id.sumar, "+");
        ponerOperacion(R.id.restar, "-");
        ponerOperacion(R.id.multiplicar, "*");
        ponerOperacion(R.id.dividir, "/");

        // Botón de la coma decimal
        findViewById(R.id.coma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escribirComa();
            }
        });

        // Borra el último carácter
        findViewById(R.id.botonC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expresion = expresion.substring(0, expresion.length() - 1);

                if (expresion.isEmpty()) {
                    expresion = "0";
                }

                resultadoMostrado = false;
                actualizarDisplay();
            }
        });

        // Borra toda la operación
        findViewById(R.id.botonAC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expresion = "0";
                resultadoMostrado = false;
                actualizarDisplay();
            }
        });

        // Calcula el resultado
        findViewById(R.id.igual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
    }

    // Asigna el funcionamiento a cada botón de operación
    private void ponerOperacion(int id, String operacion) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escribirOperacion(operacion);
            }
        });
    }

    // Añade un número sin dejar ceros innecesarios al principio
    private void escribirNumero(String numero) {
        if (resultadoMostrado) {
            expresion = "0";
            resultadoMostrado = false;
        }

        int inicio = 0;

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (c == '+' || c == '-' || c == '*' || c == '/') {
                inicio = i + 1;
            }
        }

        String actual = expresion.substring(inicio);

        if (actual.equals("0")) {
            expresion = expresion.substring(0, inicio) + numero;
        } else {
            expresion += numero;
        }

        actualizarDisplay();
    }

    // Añade una operación y quita la coma si está al final
    private void escribirOperacion(String operacion) {
        char ultimo = expresion.charAt(expresion.length() - 1);

        // Si termina en coma, la quitamos
        if (ultimo == ',') {
            expresion = expresion.substring(0, expresion.length() - 1);
        }

        resultadoMostrado = false;

        ultimo = expresion.charAt(expresion.length() - 1);

        // Evita tener dos operaciones seguidas
        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            expresion = expresion.substring(0, expresion.length() - 1);
        }

        expresion += operacion;
        actualizarDisplay();
    }

    // Añade una coma solo si el número actual no tiene otra
    private void escribirComa() {
        if (resultadoMostrado) {
            expresion = "0";
            resultadoMostrado = false;
        }

        int inicio = 0;

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (c == '+' || c == '-' || c == '*' || c == '/') {
                inicio = i + 1;
            }
        }

        String actual = expresion.substring(inicio);

        // Solo permite la coma si ya hay un número escrito
        if (!actual.isEmpty() && !actual.contains(",")) {
            expresion += ",";
        }

        actualizarDisplay();
    }

    // Separa los números y las operaciones y calcula el resultado
    private void calcular() {
        try {
            String texto = expresion.replace(',', '.');

            char ultimo = texto.charAt(texto.length() - 1);

            if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
                return;
            }

            // No calcula si la expresión termina en coma
            if (ultimo == '.') {
                return;
            }

            ArrayList<Double> numeros = new ArrayList<>();
            ArrayList<Character> operaciones = new ArrayList<>();
            String numero = "";

            for (int i = 0; i < texto.length(); i++) {
                char c = texto.charAt(i);

                if (Character.isDigit(c) || c == '.') {
                    numero += c;
                } else {
                    if (numero.isEmpty() || numero.endsWith(".")) {
                        return;
                    }

                    numeros.add(Double.parseDouble(numero));
                    numero = "";
                    operaciones.add(c);
                }
            }

            if (numero.isEmpty() || numero.endsWith(".")) {
                return;
            }

            numeros.add(Double.parseDouble(numero));

            // Primero se hacen las multiplicaciones y divisiones
            for (int i = 0; i < operaciones.size(); i++) {
                char op = operaciones.get(i);

                if (op == '*' || op == '/') {
                    double a = numeros.get(i);
                    double b = numeros.get(i + 1);

                    if (op == '/' && b == 0) {
                        Toast.makeText(this, "No se puede dividir entre cero",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double resultado;

                    if (op == '*') {
                        resultado = a * b;
                    } else {
                        resultado = a / b;
                    }

                    numeros.set(i, resultado);
                    numeros.remove(i + 1);
                    operaciones.remove(i);
                    i--;
                }
            }

            // Después se hacen las sumas y restas
            double resultado = numeros.get(0);

            for (int i = 0; i < operaciones.size(); i++) {
                if (operaciones.get(i) == '+') {
                    resultado += numeros.get(i + 1);
                } else {
                    resultado -= numeros.get(i + 1);
                }
            }

            if (!Double.isFinite(resultado)) {
                return;
            }

            if (resultado == (long) resultado) {
                expresion = String.valueOf((long) resultado);
            } else {
                expresion = String.valueOf(resultado).replace('.', ',');
            }

            resultadoMostrado = true;
            actualizarDisplay();

        } catch (Exception e) {
            Toast.makeText(this, "Operacion no valida",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Muestra los símbolos habituales de una calculadora
    private void actualizarDisplay() {
        display.setText(expresion.replace('*', '×').replace('/', '÷'));
    }
}
