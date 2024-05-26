package com.example.sd_talkwithgpt.view;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.sd_talkwithgpt.R;
import com.example.sd_talkwithgpt.viewmodel.GptViewModel;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView ivMic;
    private TextView tvSpeechToText;
    private ScrollView scrollView;
    private GptViewModel gptViewModel;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivMic = findViewById(R.id.iv_mic);
        tvSpeechToText = findViewById(R.id.tv_speech_to_text);
        scrollView = findViewById(R.id.scrollView);

        gptViewModel = new ViewModelProvider(this).get(GptViewModel.class);

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognition();
            }
        });

        gptViewModel.getGptResponse().observe(this, response -> {
            if (response != null) {
                updateUI(response);
            }
        });
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "GPT'ye sor");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String userText = result.get(0);
                gptViewModel.sendGptRequest(userText);
            }
        }
    }

    private void updateUI(String responseText) {
        tvSpeechToText.append("\n\n" + responseText);
        scrollView.post(() -> scrollView.scrollTo(0, scrollView.getBottom()));
    }
}
