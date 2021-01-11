package com.example.apiapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {


    @GET("GetDoors/")
    Call<List<model>> getModels();
}
