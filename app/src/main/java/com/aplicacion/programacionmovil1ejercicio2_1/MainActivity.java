package com.aplicacion.programacionmovil1ejercicio2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aplicacion.programacionmovil1ejercicio2_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setLayouts();
    }


    private void init(){

    }

    private void setLayouts(){
        binding.btnTakeVideo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TakeVideoActivity.class);
            startActivity(intent);
        });


        binding.btnListVideos.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ViewVideoActivity.class);
            startActivity(intent);
        });
    }
}