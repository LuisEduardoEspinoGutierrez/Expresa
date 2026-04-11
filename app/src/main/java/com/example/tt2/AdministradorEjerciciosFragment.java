package com.example.tt2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tt2.ejercicios.Ejercicio10Activity;
import com.example.tt2.ejercicios.Ejercicio11Activity;
import com.example.tt2.ejercicios.Ejercicio2Activity;
import com.example.tt2.ejercicios.Ejercicio3Activity;
import com.example.tt2.ejercicios.Ejercicio4Activity;
import com.example.tt2.ejercicios.Ejercicio7_1Activity;
import com.example.tt2.ejercicios.Ejercicio7_2Activity;
import com.example.tt2.ejercicios.Ejercicio7_3Activity;
import com.example.tt2.ejercicios.Ejercicio7_4Activity;

public class AdministradorEjerciciosFragment extends Fragment {

    public AdministradorEjerciciosFragment() {
        // Required empty public constructor
    }

    public static AdministradorEjerciciosFragment newInstance() {
        return new AdministradorEjerciciosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ejercicios_administrador, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnEjercicio2 = view.findViewById(R.id.btnEjercicio2);
        Button btnEjercicio3 = view.findViewById(R.id.btnEjercicio3);
        Button btnEjercicio4 = view.findViewById(R.id.btnEjercicio4);
        Button btnEjercicio7_1 = view.findViewById(R.id.btnEjercicio7_1);
        Button btnEjercicio7_2 = view.findViewById(R.id.btnEjercicio7_2);
        Button btnEjercicio7_3 = view.findViewById(R.id.btnEjercicio7_3);
        Button btnEjercicio7_4 = view.findViewById(R.id.btnEjercicio7_4);
        Button btnEjercicio10 = view.findViewById(R.id.btnEjercicio10);
        Button btnEjercicio11 = view.findViewById(R.id.btnEjercicio11);

        btnEjercicio2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio2Activity.class);
            startActivity(intent);
        });

        btnEjercicio3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio3Activity.class);
            startActivity(intent);
        });

        btnEjercicio4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio4Activity.class);
            startActivity(intent);
        });

        btnEjercicio7_1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio7_1Activity.class);
            startActivity(intent);
        });

        btnEjercicio7_2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio7_2Activity.class);
            startActivity(intent);
        });

        btnEjercicio7_3.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio7_3Activity.class);
            startActivity(intent);
        });

        btnEjercicio7_4.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio7_4Activity.class);
            startActivity(intent);
        });

        btnEjercicio10.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio10Activity.class);
            startActivity(intent);
        });

        btnEjercicio11.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio11Activity.class);
            startActivity(intent);
        });
    }
}
