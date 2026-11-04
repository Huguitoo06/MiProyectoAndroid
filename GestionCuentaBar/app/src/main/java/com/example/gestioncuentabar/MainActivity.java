package com.example.gestioncuentabar;

import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaramos los controles
    EditText importe;
    CheckBox checkPropina;
    SeekBar seekPropina;
    TextView textPorcentaje, resultado;
    RadioGroup grupoPago;
    RatingBar rating;
    AutoCompleteTextView camarero;
    Button botonCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Relacionamos las variables con el XML
        importe = findViewById(R.id.importe);
        checkPropina = findViewById(R.id.checkPropina);
        seekPropina = findViewById(R.id.seekPropina);
        textPorcentaje = findViewById(R.id.textPorcentaje);
        resultado = findViewById(R.id.resultado);
        grupoPago = findViewById(R.id.grupoPago);
        rating = findViewById(R.id.rating);
        camarero = findViewById(R.id.camarero);
        botonCalcular = findViewById(R.id.botonCalcular);

        // Lista de camareros
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

        // Cambiar el porcentaje de propina
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
                });

        // Boton para calcular
        botonCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String texto = importe.getText().toString();

                // Comprobar si esta vacio
                if (texto.isEmpty()) {
                    resultado.setTextColor(Color.RED);
                    resultado.setText(getString(R.string.error_vacio));
                } else {

                    double cuenta = 0;

                    try {

                        // Convertir el texto en numero
                        texto = texto.replace(",", ".");
                        cuenta = Double.parseDouble(texto);

                        // Comprobar que sea mayor que cero
                        if (cuenta <= 0) {

                            resultado.setTextColor(Color.RED);
                            resultado.setText(getString(R.string.error_positivo));

                        } else {

                            // Comprobar que se ha elegido un pago
                            int pago = grupoPago.getCheckedRadioButtonId();

                            if (pago == -1) {

                                resultado.setTextColor(Color.RED);
                                resultado.setText(getString(R.string.error_pago));

                            } else {

                                RadioButton opcion = findViewById(pago);
                                String metodo = opcion.getText().toString();

                                // Calcular propina
                                double propina = 0;

                                if (checkPropina.isChecked()) {
                                    propina = cuenta * seekPropina.getProgress() / 100;
                                }

                                // Calcular total
                                double total = cuenta + propina;

                                // Obtener valoracion y camarero
                                float estrellas = rating.getRating();
                                String nombre = camarero.getText().toString();

                                // Mostrar resultado
                                resultado.setTextColor(Color.BLACK);

                                resultado.setText(
                                        getString(R.string.resultado,
                                                cuenta,
                                                propina,
                                                total,
                                                metodo,
                                                estrellas,
                                                nombre)
                                );
                            }
                        }

                    } catch (NumberFormatException e) {

                        resultado.setTextColor(Color.RED);
                        resultado.setText(getString(R.string.error_numero));

                    }
                }
            }
        });
    }
}

