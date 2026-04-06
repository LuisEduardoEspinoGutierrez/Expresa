package com.example.tt2.ejercicios;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tt2.R;

public class Ejercicio7Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ejercicio7);

        // Ajuste de márgenes (EdgeToEdge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔙 BOTÓN REGRESAR
        ImageView btnBack = findViewById(R.id.ivRegresar);
        btnBack.setOnClickListener(v -> finish());

        // 📖 TEXTO CON RESALTADO DE "r"
        TextView tvLectura = findViewById(R.id.tvLectura);

        String texto = "Teresa es una niña que está todo el tiempo cuidando un tesoro que le regaló su abuela al morir. El tesoro no es una caja de oro, tampoco un montón de dinero. El tesoro es un corazón de color morado, donde Teresa guarda todos los recuerdos que le dejó su querida abuela al partir.";

        SpannableString spannable = new SpannableString(texto);

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);

            if (letra == 'r' || letra == 'R') {

                // Color llamativo
                spannable.setSpan(
                        new ForegroundColorSpan(Color.parseColor("#FF5722")),
                        i,
                        i + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );

                // Negritas
                spannable.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        i,
                        i + 1,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
        }

        tvLectura.setText(spannable);
    }
}