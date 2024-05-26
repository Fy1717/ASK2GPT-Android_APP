package com.example.sd_talkwithgpt.repository;

import android.util.Log;
import com.example.sd_talkwithgpt.model.GptRequest;
import com.example.sd_talkwithgpt.service.ApiService;
import com.example.sd_talkwithgpt.service.RetrofitClient;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GptRepository {
    private ApiService apiService;

    public GptRepository() {
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    public void sendRequest(String prompt, final ResponseCallback callback) {
        Call<JsonObject> call = apiService.getCompletion("Bearer TOKEN", new GptRequest(prompt));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseObject = response.body();
                    String responseText = responseObject.toString();
                    callback.onResponse(responseText);
                } else {
                    String errorBodyString = "";
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            errorBodyString = errorBody.string();
                        }
                    } catch (IOException e) {
                        errorBodyString = "Error reading error body: " + e.getMessage();
                    }
                    Log.e("GPT_ERROR", "Code : " + response.code() + ", Body : " + errorBodyString);
                    callback.onResponse("Error: " + response.code() + ", Body: " + errorBodyString);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onResponse("Failure: " + t.getMessage());
            }
        });
    }

    public interface ResponseCallback {
        void onResponse(String response);
    }
}
