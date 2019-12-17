package com.example.myapplication.Model.API;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.Model.Datamanagement.DataParser;
import com.example.myapplication.Model.RouteData.PointOfInterest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapsDirectionsAPI extends API {

    private final String MY_API_KEY = "f9e60521-895b-457d-957c-deb7e50df84f";
    private GoogleMap map;

    public GoogleMapsDirectionsAPI(Context context, GoogleMap map) {
        super(context);
        this.map = map;
    }

    public void executeDirections(LatLng origin, ArrayList<LatLng> dest){
        String url = getUrl(origin, dest);
        FetchUrl FetchUrl = new FetchUrl();
        FetchUrl.execute(url);
    }

    public String getUrl(LatLng origin, ArrayList<LatLng> waypoints) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Detination of route
        String str_dest = "destination=" + waypoints.get(waypoints.size()-1).latitude + "," + waypoints.get(waypoints.size()-1).longitude;
        String str_waypoints = "waypoints=";

        for (int i = 0; i < waypoints.size()-1; i++) {
            if(i == 0) {
                str_waypoints += "via:" + waypoints.get(i).latitude + "," + waypoints.get(i).longitude;
            } else {
                str_waypoints += "%7Cvia:" + waypoints.get(i).latitude + "," + waypoints.get(i).longitude;
            }
        }
        String trafficMode = "mode=walking";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + str_waypoints + "&" + trafficMode + "&key=" + MY_API_KEY;
        // Output format
        String output = "/json";
        // Building the url to the web service
        String url = "http://directions-aei.avans.nl:3000/directions" + "?" + parameters;
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<LatLng>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<LatLng>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<LatLng>> routeData = null;

            try {
                jObject = new JSONObject(jsonData[0]);

                Log.d("ParserTask", jsonData[0]);
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing routes data
                routeData = parser.parseRoutesInfo(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask-routes: ", routeData.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routeData;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<LatLng>> result) {
            PolylineOptions lineOptions = new PolylineOptions();

            LatLng northEast = result.get(0).get(0);
            LatLng southWest = result.get(0).get(1);
            // The first list contained the bounds of the route and is not part of the route:
            result.remove(0);

            for (List<LatLng> leg : result) {
                lineOptions.addAll(leg);
            }

            lineOptions.width(10);
            lineOptions.color(Color.RED);

            Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                map.addPolyline(lineOptions);

                // zoom to bounding-box of the route:
                LatLngBounds bounds = new LatLngBounds(southWest, northEast);
                int padding = 80;
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

}
