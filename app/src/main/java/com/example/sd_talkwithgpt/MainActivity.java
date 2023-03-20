package com.example.sd_talkwithgpt;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_mic;
    private TextView tv_Speech_to_text, answer_of_gpt;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private static String userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_mic = findViewById(R.id.iv_mic);
        tv_Speech_to_text = findViewById(R.id.tv_speech_to_text);
        answer_of_gpt = findViewById(R.id.answer_of_gpt);

        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);

                userText = Objects.requireNonNull(result).get(0);
                tv_Speech_to_text.setText("Your message\n--------------------------\n\n" + userText);

                sendAQuestion(userText);
            }
        }
    }

    private void sendAQuestion(String message) {
        try {
            new GptService().execute(message);

            new AsyncTask().execute();
        } catch (Exception e) {
            System.out.println("ERRORGPT2 : " + e.getLocalizedMessage());
        }
    }

    public class AsyncTask extends android.os.AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                if (Gpt3.message != null) {
                    String responseString = Gpt3.message;

                    //System.out.println("000000000000 : " + responseString);

                    String responseTextFromGpt = responseString.split("text\":")[1];
                    responseTextFromGpt = responseTextFromGpt.split(",\"index\"")[0];

                    //System.out.println("1111111111111 : " + responseTextFromGpt);

                    String finalResponseTextFromGpt = responseTextFromGpt;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalResponseTextFromGpt != null && !finalResponseTextFromGpt.isEmpty()) {
                                StringBuilder sb = new StringBuilder("");
                                sb.append(finalResponseTextFromGpt);

                                //System.out.println("22222222222 ::: " + finalResponseTextFromGpt);

                                replaceAll(sb, "\\n", " ");
                                replaceAll(sb, "\"", "");

                                //System.out.println("33333333333 ::: " + sb);

//                                String out = "";
//                                out += finalResponseTextFromGpt.replaceAll("\"", "");
//                                System.out.println("OUT : " + out.replaceAll("[\\t\\n\\r]+", " "));
                                answer_of_gpt.setText("Answer From GPT\n-------------------------------\n\n" + sb.substring(1, sb.length()-1));
                            } else {
                                answer_of_gpt.setText("There is an error :(");
                            }
                        }
                    });
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Log.e("ASKGPT", "ERROR : " + e);
                    }

                    answer_of_gpt.setText("There is an error :(");
                }
            } catch (Exception e) {
                Log.e("ASKGPT", "ERROR : " + e);

                answer_of_gpt.setText("There is an error :(");
            }

            return null;
        }
    }

    public static void replaceAll(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length(); // Move to the end of the replacement
            index = builder.indexOf(from, index);
        }

        System.out.println("SB RA :: " + builder);
    }
}