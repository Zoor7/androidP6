package com.example.starwarsretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwarsretrofit.adapter.OnItemClickListener;
import com.example.starwarsretrofit.adapter.PersonajeAdapter;
import com.example.starwarsretrofit.model.Data;
import com.example.starwarsretrofit.model.Peli;
import com.example.starwarsretrofit.model.Personaje;
import com.example.starwarsretrofit.webservice.WebServiceClient;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private PersonajeAdapter adapter;
    private List<Personaje> personajes;

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    static WebServiceClient client;

    private Button btnPrev;
    private Button btnNext;
    private SearchView search;

    private int pagina = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        client = retrofit.create(WebServiceClient.class);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        search = findViewById(R.id.search);

        recycler = findViewById(R.id.recyclerView);
        personajes = new ArrayList<Personaje>();
        adapter = new PersonajeAdapter(personajes, new OnItemClickListener() {
            @Override
            public void onItemClick(Personaje p, int position) {
                String[] pelis = p.getFilms();
                Intent intent = new Intent(MainActivity.this, ResumPelis.class);
                intent.putExtra("pelis", pelis);
                startActivity(intent);

            }
        });
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        people();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagina > 1) {
                    pagina--;
                    page();
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pagina < 9) {
                    pagina++;
                    page();
                }
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                btnNext.setVisibility(View.INVISIBLE);
                btnPrev.setVisibility(View.INVISIBLE);
                person(query);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    people();
                }
                return false;
            }
        });
    }

    private void people() {
        btnNext.setVisibility(View.VISIBLE);

        Call<Data> call = client.getPersonajes();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                adapter.setPersonajes(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "Error: " + t.getMessage());
            }
        });

    }

    private void page() {

        Call<Data> call = client.getPage("people/?page=" + pagina + "");
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                adapter.setPersonajes(response.body().getResults());

                if (response.body().getNext() == null) {
                    btnNext.setVisibility(View.INVISIBLE);
                } else btnNext.setVisibility(View.VISIBLE);

                if (response.body().getPrevious() == null) {
                    btnPrev.setVisibility(View.INVISIBLE);
                } else btnPrev.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "Error: " + t.getMessage());
            }
        });

    }

    private void person(String str) {

        Call<Data> call = client.getPj("people/?search=" + str);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                adapter.setPersonajes(response.body().getResults());

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "Error: " + t.getMessage());
            }
        });
    }


}

