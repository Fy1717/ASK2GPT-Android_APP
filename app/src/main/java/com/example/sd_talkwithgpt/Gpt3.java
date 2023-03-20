package com.example.sd_talkwithgpt;

import com.google.gson.JsonArray;

public class Gpt3 {
    public String id;
    public JsonArray choices;
    public String object;
    public int created;
    public String model;
    public static String message;
    //public JsonObject usage;

    public Gpt3() {}

    public Gpt3(String id, JsonArray choices, String object, int created, String model) {
        super();
        this.id = id;
        this.choices = choices;
        this.object = object;
        this.created = created;
        this.model = model;
        //this.usage = usage;
    }
}