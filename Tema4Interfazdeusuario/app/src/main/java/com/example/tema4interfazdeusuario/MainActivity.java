
package com.example.tema4interfazdeusuario;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recogemos los elementos de la pantalla
        EditText usuario = findViewById(R.id.usuario);
        EditText contrasena = findViewById(R.id.contrasena);
        Button boton = findViewById(R.id.entrar);
        LinearLayout pantalla = findViewById(R.id.pantalla);

        // Aplicamos la animacion a la pantalla
        pantalla.startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.animacion));

        // Comprobamos el usuario y la contrasena
        boton.setOnClickListener(v -> {
            if (usuario.getText().toString().equals("admin")
                    && contrasena.getText().toString().equals("1234")) {

                Toast.makeText(this, "Inicio correcto",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Datos incorrectos",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Comprobamos si el CheckBox esta marcado
        CheckBox checkbox = findViewById(R.id.checkbox1);

        checkbox.setOnCheckedChangeListener((botonCheck, marcado) -> {
            if (marcado) {
                Toast.makeText(this, "Opcion marcada",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Opcion desmarcada",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Recogemos el grupo de botones de opcion
        RadioGroup grupo = findViewById(R.id.grupoRadios);
        Button comprobar = findViewById(R.id.botonComprobar);
        TextView resultado = findViewById(R.id.textoResultado);

        // Comprobamos que opcion ha seleccionado el usuario
        comprobar.setOnClickListener(v -> {

            int idSeleccionado = grupo.getCheckedRadioButtonId();

            if (idSeleccionado != -1) {

                // Buscamos el boton seleccionado
                RadioButton opcion = findViewById(idSeleccionado);

                // Guardamos el texto del boton seleccionado
                String texto = opcion.getText().toString();

                // Mostramos la opcion seleccionada
                resultado.setText("Has seleccionado: " + texto);

            } else {
                // Avisamos si no ha seleccionado ninguna opcion
                resultado.setText(
                        "No has seleccionado ninguna opcion");
            }
        });
    }
}
