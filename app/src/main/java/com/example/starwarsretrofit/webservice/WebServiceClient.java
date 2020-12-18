package com.example.starwarsretrofit.webservice;

import com.example.starwarsretrofit.model.Data;
import com.example.starwarsretrofit.model.Peli;
import com.example.starwarsretrofit.model.Personaje;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {
    @GET("people")
    Call<Data> getPersonajes();

    @GET()
    Call<Data> getPage(@Url String url);

    @GET()
    Call<Data> getPj(@Url String url);

    @GET
    Call<Peli> getInfoPeli(@Url String url);

}
