package com.example.starwarsretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.starwarsretrofit.model.Peli;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumPelis extends AppCompatActivity {
    private Button nextbtn;
    private Button prevbtn;
    private TextView titolText;
    private TextView resumText;
    private int currentPeli=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infopj);

        nextbtn = findViewById(R.id.btnNext);
        prevbtn = findViewById(R.id.btnPrev);
        titolText = findViewById(R.id.titolPeli);
        resumText = findViewById(R.id.resumPeli);

        Intent intent = getIntent();
        String[] pelis = intent.getStringArrayExtra("pelis");
        System.out.println(Arrays.toString(pelis));
        peticionPeli();

        if (pelis.length>0){
            nextbtn.setVisibility(View.VISIBLE);
        }

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevbtn.setVisibility(View.VISIBLE);
                currentPeli++;
                if (currentPeli<pelis.length){
                    peticionPeli();
                }else {
                    nextbtn.setVisibility(View.INVISIBLE);
                    peticionPeli();
                }

            }
        });

        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextbtn.setVisibility(View.VISIBLE);
                currentPeli--;
                if (currentPeli==1){
                    prevbtn.setVisibility(View.INVISIBLE);
                    peticionPeli();
                }else {
                    peticionPeli();
                }


            }
        });

    }
    private void peticionPeli(){

        Call<Peli> peliCall= MainActivity.client.getInfoPeli("films/"+(currentPeli+""));
        peliCall.enqueue(new Callback<Peli>() {
            @Override
            public void onResponse(Call<Peli> call, Response<Peli> response) {
                titolText.setText(response.body().getTitle());
                resumText.setText(response.body().getResum());

            }

            @Override
            public void onFailure(Call<Peli> call, Throwable t) {

            }
        });


    }
}