package com.aplicacion.programacionmovil1ejercicio2_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.aplicacion.programacionmovil1ejercicio2_1.databinding.ActivityTakeVideoBinding;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeVideoActivity extends AppCompatActivity {

    static final int PETICION_VIDEO = 100;

    ActivityTakeVideoBinding binding;

    Uri uriVideoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTakeVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        init();
        setLayouts();
    }

    private void init(){

//        MediaController mediaController = new MediaController(this);
//
//        binding.videoView.setMediaController(mediaController);

        uriVideoGeneral = null;
    }

    private void setLayouts(){

        binding.btnBack.setOnClickListener(v -> onBackPressed());

        binding.btnTakeVideo.setOnClickListener(v -> permiso());

        binding.btnSaveVideo.setOnClickListener(v -> saveVideoInStorage());


    }

    private void permiso() {

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_VIDEO);
        }else{
            takeVideo();
        }

    }

    private void takeVideo() {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if(intent.resolveActivity(getPackageManager()) != null){
            pickVideo.launch(intent);
        }
    }

    private void saveVideoInStorage(){



        if (uriVideoGeneral == null){
            showMessage("Por favor capture un video");
            return;
        }


        try {
            
            AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(uriVideoGeneral, "r");

            FileInputStream in = videoAsset.createInputStream();
            FileOutputStream archivo = openFileOutput(newNameMP4(), Context.MODE_PRIVATE);
            
            byte[] buf = new byte[1024];
            
            int len;
            
            while ((len = in.read(buf)) > 0){
                archivo.write(buf, 0, len);
            }

            showMessage("Video guardado correctamente");

            clearComponents();
            
        }catch (IOException e){

            showMessage(e.getMessage());
        }
    }

    private void clearComponents() {

        finish();
    }

    private String newNameMP4() {

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String name = date+".mp4";

        return name;

    }


    private final ActivityResultLauncher<Intent> pickVideo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){

                    if(result.getData() != null){
                        Uri videoUri = result.getData().getData();

                        binding.videoView.setVideoURI(videoUri);

                        binding.videoView.start();

                        uriVideoGeneral = videoUri;
                    }
                }
            }
    );


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_VIDEO){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takeVideo();
            }else{
                showMessage("Esta funcion necesita el acceso a la camara");
            }
        }
    }


    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}