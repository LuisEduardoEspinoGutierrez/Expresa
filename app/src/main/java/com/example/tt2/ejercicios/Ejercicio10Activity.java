package com.example.tt2.ejercicios;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tt2.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class Ejercicio10Activity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ChipGroup chipGroupPalabras;
    private int totalWords;
    private int wordsFoundCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio10);

        ImageView ivRegresar = findViewById(R.id.ivRegresar);
        Button btnAudio = findViewById(R.id.btnAudioInstrucciones);
        SopaDeLetrasView sopa = findViewById(R.id.sopaDeLetrasView);
        chipGroupPalabras = findViewById(R.id.chipGroupPalabras);

        totalWords = chipGroupPalabras.getChildCount();

        ivRegresar.setOnClickListener(v -> finish());

        btnAudio.setOnClickListener(v ->
                playAudio(R.raw.r_instrucciones_ejercicio10));

        sopa.setOnWordFoundListener(word -> {
            removeWordChip(word);
            wordsFoundCount++;

            playWordAudio(word);

            if (wordsFoundCount == totalWords) {
                Toast.makeText(this, "¡Felicidades! Has terminado el ejercicio", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "¡Encontraste: " + word + "!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeWordChip(String word) {
        for (int i = 0; i < chipGroupPalabras.getChildCount(); i++) {
            Chip chip = (Chip) chipGroupPalabras.getChildAt(i);
            if (chip.getText().toString().equalsIgnoreCase(word)) {
                chipGroupPalabras.removeView(chip);
                break;
            }
        }
    }

    private void playWordAudio(String word) {
        // El nombre del recurso es tr_palabra (ej: tr_tractor)
        String resourceName = "tr_" + word.toLowerCase();
        int resId = getResources().getIdentifier(resourceName, "raw", getPackageName());

        if (resId != 0) {
            playAudio(resId);
        } else {
            // Si no se encuentra el audio específico, reproducir "muy bien"
            playAudio(R.raw.muy_bien);
        }
    }

    private void playAudio(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, resId);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
