package com.example.sd_talkwithgpt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.sd_talkwithgpt.model.GptResponse;
import com.example.sd_talkwithgpt.repository.GptRepository;

public class GptViewModel extends ViewModel {
    private MutableLiveData<String> gptResponse;
    private GptRepository gptRepository;

    public GptViewModel() {
        gptRepository = new GptRepository();
        gptResponse = new MutableLiveData<>();
    }

    public LiveData<String> getGptResponse() {
        return gptResponse;
    }

    public void sendGptRequest(String prompt) {
        gptRepository.sendRequest(prompt, response -> gptResponse.postValue(response));
    }
}
