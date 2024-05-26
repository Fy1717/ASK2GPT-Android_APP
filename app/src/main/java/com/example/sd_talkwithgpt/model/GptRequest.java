package com.example.sd_talkwithgpt.model;

public class GptRequest {
    private String model;
    private String prompt;
    private int max_tokens;
    private double temperature;

    public GptRequest(String prompt) {
        this.model = "gpt-3.5-turbo-instruct";
        this.prompt = prompt;
        this.max_tokens = 100;
        this.temperature = 0.7;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
