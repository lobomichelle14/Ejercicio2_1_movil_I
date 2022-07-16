package com.aplicacion.programacionmovil1ejercicio2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.aplicacion.programacionmovil1ejercicio2_1.databinding.ActivityViewVideoBinding;
import com.aplicacion.programacionmovil1ejercicio2_1.databinding.DialogViewVideoBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewVideoActivity extends AppCompatActivity {

    String list[];

    ActivityViewVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewVideoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        init();

        setListeners();
    }

    private void init(){
        list = fileList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        binding.listViewVideo.setAdapter(adapter);
    }

    private void setListeners(){

        binding.listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewVideo(i);
            }
        });
    }


    public void viewVideo(int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewVideoActivity.this);

        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_view_video, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();

        dialog.show();


        VideoView videoView = (VideoView) view.findViewById(R.id.videoViewDialog);
        TextView textTitle = (TextView) view.findViewById(R.id.textTitleDialog);

        showMessage(getFilesDir()+"");


        videoView.setVideoPath(getFilesDir()+"/"+list[position]);
        textTitle.setText(list[position]);
        videoView.start();

    }


    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}