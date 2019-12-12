package com.example.myapplication.Model.API;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public abstract class API {

    private RequestQueue queue;

    public API(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void addRequest(JsonObjectRequest request) {
        this.queue.add(request);
    }

    public void startQueue() {
        this.queue.start();
    }
}
