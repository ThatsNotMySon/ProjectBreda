package com.example.myapplication.Model.API;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

// CAREFUL !! THIS API IS NOT FREE

public class GoogleMapsPlacesAPI extends API {
    private HashMap<String, String> placesIdCache;
    private HashMap<String, JSONObject> placesCache;
    private static String FREE_API_FIELDS =
            "formatted_address,geometry,icon,name,permanently_closed,photos,place_id,plus_code,types";
    private String getApiKey(){
        int x = 6;
        return context.getResources().getString(R.string.PlacesApiKey);
    }
    public GoogleMapsPlacesAPI(Context context) {
        super(context);
        this.placesCache = new HashMap<>();
        this.placesIdCache = new HashMap<>();
    }
    private void findPlaceId(String textQuery, final StringCallback callback){
        if (placesIdCache.containsKey(textQuery)){
            callback.onResponse(placesIdCache.get(textQuery));
        }
        String query =
            "https://maps.googleapis.com/maps/api/place/findplacefromtext/json"
            + "?inputtype=textquery"
            + "&input=" + textQuery
            + "&key=" + getApiKey();
        addRequest(
                new JsonObjectRequest(
                        query,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray results = response.getJSONArray("candidates");
                                    if (results.length() < 1) {
                                        callback.onResponse(null);
                                        return;
                                    }
                                    String id = results.getJSONObject(0).getString("place_id");
                                    callback.onResponse(id);
                                    return;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    callback.onResponse(null);
                                    return;
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                )
        );
        startQueue();
    }
    // DO NOT EDIT !! THIS API IS NOT FREE
    public void getBasicPlacesInfo(String textQuery, final JsonObjectCallback _callback){
        if (placesCache.containsKey(textQuery)){
            _callback.onResponse(placesCache.get(textQuery));
        }
        this.findPlaceId(textQuery, new StringCallback() {
            @Override
            public void onResponse(String response) {
                String query = "https://maps.googleapis.com/maps/api/place/details/json"
                      + "?fields=" + FREE_API_FIELDS
                      + "&place_id=" + response
                      + "&key=" + getApiKey();
                addRequest(
                        new JsonObjectRequest(
                                query,
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        _callback.onResponse(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                }
                        )
                );
                startQueue();
            }
        });
    }
    public interface StringCallback {
        void onResponse(String response);
    }
    public interface JsonObjectCallback {
        void onResponse(JSONObject response);
    }
}
