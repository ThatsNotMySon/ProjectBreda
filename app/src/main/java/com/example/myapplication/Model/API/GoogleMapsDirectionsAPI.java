package com.example.myapplication.Model.API;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Model.RouteData.PointOfInterest;

import org.json.JSONObject;

public class GoogleMapsDirectionsAPI extends API {

    private String APIKey = "f9e60521-895b-457d-957c-deb7e50df84f";

    public GoogleMapsDirectionsAPI(Context context) {
        super(context);
    }

    public void getDirections(PointOfInterest begin, PointOfInterest dest, final DirCallback callback) {
        String request =
                "http://145.48.6.80:3000/directions?origin="
                + begin.getLocation().x + ","
                + begin.getLocation().y + "&destination="
                + dest.getLocation().x + ","
                + dest.getLocation().y + "&mode=walking&key="
                + this.APIKey;
        addRequest(new JsonObjectRequest(
                request,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ));
    }

    public interface DirCallback {
        void onResponse(String response);
    }
}
