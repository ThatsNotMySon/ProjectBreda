package com.example.myapplication.Model;

import android.content.Context;

import com.example.myapplication.Model.API.API;
import com.example.myapplication.Model.API.GoogleMapsDirectionsAPI;
import com.example.myapplication.Model.API.GoogleMapsGeoLocationAPI;
import com.example.myapplication.Model.API.GoogleMapsPlacesAPI;

import java.util.ArrayList;

public class APIManager {

    private static APIManager instance;

    private ArrayList<API> apis = new ArrayList<>();

    public APIManager(Context context) {
        this.apis.add(new GoogleMapsGeoLocationAPI(context));
        this.apis.add(new GoogleMapsPlacesAPI(context));
    }

    public static APIManager getInstance(Context context) {
        if (instance == null) instance = new APIManager(context);
        return instance;
    }

    public GoogleMapsDirectionsAPI getDirectionsAPI() {
        return (GoogleMapsDirectionsAPI) this.apis.get(0);
    }

    public GoogleMapsGeoLocationAPI getGeoLocationAPI() {
        return (GoogleMapsGeoLocationAPI) this.apis.get(1);
    }

    public GoogleMapsPlacesAPI getPlacesAPI() {
        return (GoogleMapsPlacesAPI) this.apis.get(2);
    }
}
