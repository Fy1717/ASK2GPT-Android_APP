package com.example.sd_talkwithgpt;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GptService extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... uri) {
        String urlParameter = "https://api.openai.com/v1/completions";
        String apiToken = "YOUR_API_TOKEN";
        String message = uri[0];

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "text-davinci-003");
            jsonObject.put("prompt", message);
            jsonObject.put("temperature", 0);
            jsonObject.put("max_tokens", 100);
            jsonObject.put("top_p", 1);
            jsonObject.put("frequency_penalty", 0.0);
            jsonObject.put("presence_penalty", 0.0);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Response response = null;

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");

            try {
                RequestBody body = RequestBody.create(mediaType, String.valueOf(jsonObject));
                Request request = new Request.Builder()
                        .url(urlParameter)
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer " + apiToken)
                        .build();
                response = client.newCall(request).execute();

                String responseString = response.body().string();
                System.out.println("RESPONSE FROM GPT: " + responseString);

                if (response.code() == 200) {
                    Gpt3.setMessage(responseString);
                } else {
                    Log.e("GPTSERVICE", "ERROR (Response code): " + response.code());
                }
            } catch (Exception e) {
                Log.e("GPTSERVICE", "ERROR : " + e.getLocalizedMessage());
            }
        } catch (Exception e) {
            Log.e("GPTSERVICE", "ERROR : " + e.getLocalizedMessage());
        }

        return "";
    }
}

