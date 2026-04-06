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

import com.example.tt2.ejercicios.Ejercicio6Activity;
import com.example.tt2.ejercicios.Ejercicio7Activity;

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

        Button btnEjercicio6 = view.findViewById(R.id.btnEjercicio6);
        Button btnEjercicio7 = view.findViewById(R.id.btnEjercicio7);
        
        btnEjercicio6.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio6Activity.class);
            startActivity(intent);
        });

        btnEjercicio7.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Ejercicio7Activity.class);
            startActivity(intent);
        });
    }
}
