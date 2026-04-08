package com.example.tt2.ejercicios;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tt2.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Ejercicio7_1Activity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    MediaRecorder recorder;
    String filePath;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    Button btnGrabar, btnDetener, btnSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ejercicio7_1);

        // 🔐 Configurar lanzador de permisos
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                startRecording();
            } else {
                Toast.makeText(this, "Permiso de audio denegado", Toast.LENGTH_SHORT).show();
            }
        });

        // Ajuste de márgenes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // BOTÓN REGRESAR
        ImageView btnBack = findViewById(R.id.ivRegresar);
        btnBack.setOnClickListener(v -> finish());

        // TEXTO CON RESALTADO DE "r" (TRABALENGUAS)
        TextView tvLectura = findViewById(R.id.tvLectura);
        String texto = "El otro dia me cai del ferrocarril\nal lado de un barril\nEl barril tenia ruedas\nQue raro barril!!\nY con las ruedas\ncai en el barro marron\nFui a mi casa, me bańe rapido\ny dije todo otra vez.";
        SpannableString spannable = new SpannableString(texto);

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);
            if (letra == 'r' || letra == 'R') {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        tvLectura.setText(spannable);

        // BOTÓN ESCUCHAR INSTRUCCIONES
        Button btnAudio = findViewById(R.id.btnAudioInstrucciones);
        // Usando r_instrucciones_ejercicio7.mp3 como solicitaste
        mediaPlayer = MediaPlayer.create(this, R.raw.r_instrucciones_ejercicio7);
        btnAudio.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(0);
                } else {
                    mediaPlayer.start();
                }
            }
        });

        // BOTONES DE GRABACIÓN
        btnGrabar = findViewById(R.id.btnGrabar);
        btnDetener = findViewById(R.id.btnDetener);
        btnSubir = findViewById(R.id.btnSubir);

        btnGrabar.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
            }
        });

        btnDetener.setOnClickListener(v -> stopRecording());
        btnSubir.setOnClickListener(v -> uploadAudio());
    }

    private void startRecording() {
        try {
            File file = new File(getExternalFilesDir(null), "audio_ejercicio7_1_trabalenguas.3gp");
            filePath = file.getAbsolutePath();

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(filePath);

            recorder.prepare();
            recorder.start();

            btnGrabar.setEnabled(false);
            btnDetener.setEnabled(true);
            Toast.makeText(this, " Grabando...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al iniciar grabación", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
                btnGrabar.setEnabled(true);
                btnDetener.setEnabled(false);
                Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadAudio() {
        if (filePath == null) {
            Toast.makeText(this, "Primero graba un audio", Toast.LENGTH_SHORT).show();
            return;
        }

        File fileObj = new File(filePath);
        if (!fileObj.exists()) {
            Toast.makeText(this, "Archivo no encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(fileObj);
        StorageReference ref = storageRef.child("ejercicios/ej7_1_trabalenguas_" + System.currentTimeMillis() + ".3gp");

        Toast.makeText(this, "Subiendo audio...", Toast.LENGTH_SHORT).show();

        ref.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        Toast.makeText(this, " Audio subido correctamente", Toast.LENGTH_LONG).show();
                        Log.d("EJERCICIO_7_1", "URL: " + uri.toString());
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e("EJERCICIO_7_1", "Error al subir", e);
                    Toast.makeText(this, " Error al subir: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }
    }
}
