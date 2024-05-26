package com.example.sd_talkwithgpt.model;

import com.google.gson.JsonArray;

public class GptResponse {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonArray getChoices() {
        return choices;
    }

    public void setChoices(JsonArray choices) {
        this.choices = choices;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private JsonArray choices;
    private String object;
    private int created;
    private String model;
}
