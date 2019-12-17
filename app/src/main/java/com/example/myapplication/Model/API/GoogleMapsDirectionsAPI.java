package com.example.myapplication.Model.API;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.Model.RouteData.PointOfInterest;
import com.google.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsDirectionsAPI extends API {

    private String APIKey = "f9e60521-895b-457d-957c-deb7e50df84f";

    public GoogleMapsDirectionsAPI(Context context) {
        super(context);
    }

    public void getDirections(LatLng begin, LatLng dest, final DirCallback callback) {
        String request =
                "http://145.48.6.80:3000/directions?origin="
                + begin.lat + ","
                + begin.lng + "&destination="
                + dest.lat + ","
                + dest.lng + "&mode=walking&key="
                + this.APIKey;
        addRequest(new JsonObjectRequest(
                request,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<List<LatLng>> data = null;
                        JSONArray routes;
                        JSONArray legs;
                        JSONArray steps;

                        try {
                            routes = response.getJSONArray("routes");
                            JSONObject northeEastJ = ((JSONObject)routes.get(0)).getJSONObject("bounds").getJSONObject("northeast");
                            LatLng northEast = new LatLng(northeEastJ.getDouble("lat"), northeEastJ.getDouble("lng"));
                            JSONObject southWestJ = ((JSONObject)routes.get(0)).getJSONObject("bounds").getJSONObject("southwest");
                            LatLng southWest = new LatLng(southWestJ.getDouble("lat"), southWestJ.getDouble("lng"));
                            List<LatLng> bounds = new ArrayList<>();
                            bounds.add(northEast);
                            bounds.add(southWest);
                            data.add(bounds);

                            for (int i = 0; i < routes.length(); i++) {
                                legs = ((JSONObject)routes.get(i)).getJSONArray("legs");

                                for (int l = 0; l < legs.length(); l++) {
                                    steps = ((JSONObject)legs.get(l)).getJSONArray("steps");

                                    for (int s = 0; s < steps.length(); s++) {
                                        String polyline = "";
                                        polyline = (String)((JSONObject)((JSONObject)steps.get(s)).get("polyline")).get("points");
                                        List<LatLng> list = decodePoly(polyline);

                                        data.add(list);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.onResponse(data);
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
        void onResponse(List<List<LatLng>> response);
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
