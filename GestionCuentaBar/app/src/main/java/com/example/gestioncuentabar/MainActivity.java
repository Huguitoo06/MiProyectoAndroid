package com.example.gestioncuentabar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Declaramos los elementos de la pantalla
    EditText importe;
    CheckBox checkPropina;
    SeekBar seekPropina;
    TextView textPorcentaje;
    TextView resultado;
    RadioGroup grupoPago;
    RatingBar rating;
    AutoCompleteTextView camarero;
    Button botonCalcular;
    Button botonEspanol;
    Button botonIngles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Relacionamos los elementos con sus IDs del XML
        importe = findViewById(R.id.importe);
        checkPropina = findViewById(R.id.checkPropina);
        seekPropina = findViewById(R.id.seekPropina);
        textPorcentaje = findViewById(R.id.textPorcentaje);
        resultado = findViewById(R.id.resultado);
        grupoPago = findViewById(R.id.grupoPago);
        rating = findViewById(R.id.rating);
        camarero = findViewById(R.id.camarero);
        botonCalcular = findViewById(R.id.botonCalcular);

        botonEspanol = findViewById(R.id.botonEspanol);
        botonIngles = findViewById(R.id.botonIngles);

        // Lista de camareros para el autocompletado
        String[] listaCamareros = {
                "Carlos", "Miguel", "Laura",
                "Ana", "Pedro", "Lucia"
        };

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                listaCamareros
        );

        camarero.setAdapter(adaptador);
        camarero.setThreshold(3);

        // Actualizamos el porcentaje al mover el SeekBar
        seekPropina.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {

                        textPorcentaje.setText(
                                getString(R.string.propina_porcentaje, progress)
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

        // Botón para cambiar el idioma a español
        botonEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags("es")
                );
            }
        });

        // Botón para cambiar el idioma a inglés
        botonIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags("en")
                );
            }
        });
    }

    // Método que realiza el cálculo de la cuenta
    private void calcularCuenta() {

        String textoImporte = importe.getText().toString().trim();

        // Comprobamos que se haya escrito un importe
        if (textoImporte.isEmpty()) {
            mostrarError(R.string.error_vacio);
            return;
        }

        double precio;

        try {
            // Permitimos utilizar coma o punto decimal
            textoImporte = textoImporte.replace(",", ".");
            precio = Double.parseDouble(textoImporte);

        } catch (NumberFormatException e) {
            mostrarError(R.string.error_numero);
            return;
        }

        // Comprobamos que el importe sea mayor que cero
        if (precio <= 0) {
            mostrarError(R.string.error_positivo);
            return;
        }

        // Comprobamos que se haya seleccionado un método de pago
        int pagoSeleccionado = grupoPago.getCheckedRadioButtonId();

        if (pagoSeleccionado == -1) {
            mostrarError(R.string.error_pago);
            return;
        }

        String metodoPago;

        if (pagoSeleccionado == R.id.efectivo) {
            metodoPago = getString(R.string.efectivo);
        } else {
            metodoPago = getString(R.string.tarjeta);
        }

        // Calculamos la propina si está marcada la casilla
        double propina = 0;

        if (checkPropina.isChecked()) {
            int porcentaje = seekPropina.getProgress();
            propina = precio * porcentaje / 100;
        }

        // Calculamos el total
        double total = precio + propina;

        // Obtenemos la valoración y el nombre del camarero
        float valoracion = rating.getRating();

        String nombreCamarero = camarero.getText().toString().trim();

        if (nombreCamarero.isEmpty()) {
            nombreCamarero = "-";
        }

        // Mostramos el resultado con el idioma seleccionado
        String textoResultado = String.format(
                Locale.getDefault(),
                getString(R.string.resultado),
                precio,
                propina,
                total,
                metodoPago,
                valoracion,
                nombreCamarero
        );

        resultado.setText(textoResultado);

        // Ponemos el texto del resultado en morado
        resultado.setTextColor(Color.rgb(81, 64, 120));
    }

    // Método para mostrar los errores en rojo
    private void mostrarError(int mensaje) {
        resultado.setText(getString(mensaje));
        resultado.setTextColor(Color.RED);
    }
}