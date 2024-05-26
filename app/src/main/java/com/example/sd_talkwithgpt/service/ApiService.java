package com.example.sd_talkwithgpt.service;

import com.example.sd_talkwithgpt.model.GptRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("v1/completions")
    Call<JsonObject> getCompletion(@Header("Authorization") String token, @Body GptRequest request);
}
