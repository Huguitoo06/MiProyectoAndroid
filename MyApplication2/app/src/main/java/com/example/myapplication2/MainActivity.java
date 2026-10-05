package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Asiganmos un Layout a la activity
        EdgeToEdge.enable(this);
        //EScribimos en el Logcat - Para verlo filtramos usando el tag Ejemplo
        setContentView(R.layout.activity_main);
        Log.i("Ejemplo", "Estoy en on create");
    }
    protected void onStart() {
        super.onStart();
        Log.i("Ejemplo", "Estoy on Start");
    };
    protected void onResume() {
        super.onResume();
        Log.i("Ejemplo", "Estoy on Resume");
    };
    protected void onPause() {
        super.onPause();
        Log.i("Ejemplo", "Estoy on Pause");
    };
    protected void onStop() {
        super.onStop();
        Log.i("Ejemplo", "Estoy on Stop");
    };
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ejemplo", "Estoy on Destroy");
        Intent ejemplo = new Intent(this,MainActivity2.class);
        startActivity(ejemplo);
    };
}