package com.example.gestioncuentabar;

import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText importe;
    CheckBox checkPropina;
    SeekBar seekPropina;
    TextView textPorcentaje;
    RadioGroup grupoPago;
    RatingBar rating;
    AutoCompleteTextView camarero;
    Button botonCalcular;
    TextView resultado;
    Button botonEspanol;
    Button botonIngles;

    int porcentaje = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Conectamos los elementos del XML
        importe = findViewById(R.id.importe);
        checkPropina = findViewById(R.id.checkPropina);
        seekPropina = findViewById(R.id.seekPropina);
        textPorcentaje = findViewById(R.id.textPorcentaje);
        grupoPago = findViewById(R.id.grupoPago);
        rating = findViewById(R.id.rating);
        camarero = findViewById(R.id.camarero);
        botonCalcular = findViewById(R.id.botonCalcular);
        resultado = findViewById(R.id.resultado);
        botonEspanol = findViewById(R.id.botonEspanol);
        botonIngles = findViewById(R.id.botonIngles);

        // Lista de nombres para las sugerencias
        String[] nombres = {
                "Carlos", "Miguel", "Laura",
                "Ana", "Pedro", "Lucia"
        };

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                nombres
        );

        camarero.setAdapter(adaptador);
        camarero.setThreshold(3);

        // Controlamos el porcentaje de propina
        seekPropina.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress,
                                                  boolean fromUser) {

                        porcentaje = progress;

                        textPorcentaje.setText(
                                getString(R.string.propina_porcentaje, porcentaje)
                        );
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );

        // Botón para calcular la cuenta
        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularCuenta();
            }
        });

        // Cambiar a español
        botonEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarIdioma("es");
            }
        });

        // Cambiar a inglés
        botonIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarIdioma("en");
            }
        });
    }

    // Método para calcular la cuenta
    public void calcularCuenta() {

        String textoImporte = importe.getText().toString().trim();

        // Comprobamos que no esté vacío
        if (textoImporte.isEmpty()) {
            mostrarError(R.string.error_vacio);
            return;
        }

        double precio;

        // Convertimos el texto a número
        try {
            textoImporte = textoImporte.replace(",", ".");
            precio = Double.parseDouble(textoImporte);
        } catch (NumberFormatException e) {
            mostrarError(R.string.error_numero);
            return;
        }

        // Comprobamos que el precio sea positivo
        if (precio <= 0 || Double.isInfinite(precio) || Double.isNaN(precio)) {
            mostrarError(R.string.error_positivo);
            return;
        }

        // Comprobamos el método de pago
        int pagoSeleccionado = grupoPago.getCheckedRadioButtonId();

        if (pagoSeleccionado == -1) {
            mostrarError(R.string.error_pago);
            return;
        }

        String pago;

        if (pagoSeleccionado == R.id.efectivo) {
            pago = getString(R.string.efectivo);
        } else {
            pago = getString(R.string.tarjeta);
        }

        // Calculamos la propina
        double propina = 0;

        if (checkPropina.isChecked()) {
            propina = precio * porcentaje / 100;
        }

        // Calculamos el total
        double total = precio + propina;

        // Recogemos la valoración
        float valoracion = rating.getRating();

        // Recogemos el nombre del camarero
        String nombre = camarero.getText().toString().trim();

        if (nombre.isEmpty()) {
            nombre = "-";
        }

        // Preparamos el resultado
        String textoResultado = String.format(
                Locale.getDefault(),
                getString(R.string.resultado),
                precio,
                propina,
                total,
                pago,
                valoracion,
                nombre
        );

        // Mostramos el resultado
        resultado.setTextColor(Color.parseColor("#4527A0"));
        resultado.setText(textoResultado);

        // Animación al mostrar el resultado
        resultado.setAlpha(0f);
        resultado.animate().alpha(1f).setDuration(400).start();
    }

    // Método para mostrar errores
    public void mostrarError(int mensaje) {
        resultado.setTextColor(Color.RED);
        resultado.setText(getString(mensaje));
    }

    // Método para cambiar el idioma
    public void cambiarIdioma(String idioma) {
        AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(idioma)
        );
    }
}
