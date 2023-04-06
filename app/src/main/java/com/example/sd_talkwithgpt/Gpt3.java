package com.example.sd_talkwithgpt;

import com.google.gson.JsonArray;

public class Gpt3 {
    private String id;
    private JsonArray choices;
    private String object;
    private int created;
    private String model;
    private static String message;

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

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Gpt3.message = message;
    }

    public Gpt3(String id, JsonArray choices, String object, int created, String model) {
        super();
        this.id = id;
        this.choices = choices;
        this.object = object;
        this.created = created;
        this.model = model;
    }
}