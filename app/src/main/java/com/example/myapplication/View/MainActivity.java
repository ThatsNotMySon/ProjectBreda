package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Model.API.GeofenceBroadcastReceiver;
import com.example.myapplication.Model.API.GoogleMapsDirectionsAPI;
import com.example.myapplication.Model.Datamanagement.DataParser;
import com.example.myapplication.Model.LocationData.LocationApi;
import com.example.myapplication.Model.LocationData.LocationApiListener;
import com.example.myapplication.Model.LocationData.LocationCallback;
import com.example.myapplication.Model.RouteData.Route;
import com.example.myapplication.Model.RouteData.Waypoint;
import com.example.myapplication.Model.RouteTracker;
import com.example.myapplication.R;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.places.*;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private static String TAG = "MainActivity";

    //Voeg locationListeners aan dit toe om het naar de locatie te laten luisteren
    private ArrayList<LocationApiListener> locationListeners;
    private RouteTracker routeTracker;
    private Route route;
    private GoogleMap map;
    private Marker testmarker;
    private LocationApi locationApi;
    private GeoApiContext mGeoApiContext;
    private Location mUserPosition;
    private GoogleMapsDirectionsAPI googleMapsDirectionsAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LocationApi wordt hier geinitialiseer. Maar de route bestaat nu alleen nog uit een klasse zonder waardes.
        this.route = new Route();
        this.routeTracker = new RouteTracker(this.route);
        this.locationListeners = new ArrayList<>();
        this.locationListeners.add(this.routeTracker);

        this.locationApi = new LocationApi(locationListeners, this, new LocationCallback() {
            @Override
            public void locationCallback(Location location) {
                mUserPosition = location;
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("User")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                if (map != null) {
                    if (testmarker != null) {
                        testmarker.remove();
                    }
                    testmarker = map.addMarker(markerOptions);
                }
                for (Waypoint waypoint : route.getWaypoints()) {
                    if (location.distanceTo(waypoint.getLocation()) <= 50) {
                        Intent intent = new Intent(MainActivity.this, WaypointActivity.class);
                        intent.putExtra("WAYPOINT", waypoint);
                        startActivity(intent);
                    }
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //MapView mapView = findViewById(R.id.map);
        //mapView.getMapAsync(this);
        mapFragment.getMapAsync(this);
        Log.i(TAG, "OnCreate");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.settingsItem:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.routeItem:
                Toast.makeText(this, "Route", Toast.LENGTH_SHORT).show();
                i = new Intent(this, RouteActivity.class);
                startActivity(i);
                return true;
            case R.id.helpItem:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                i = new Intent(this, HelpActivity.class);
                startActivity(i);
                return true;
            case R.id.testGeofence:
                //startGeofence();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);
        this.googleMapsDirectionsAPI = new GoogleMapsDirectionsAPI(this, map);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        route.addWaypoint(new Waypoint((float)latLng.latitude, (float)latLng.longitude, "Test", "Bruhh"));
        for (Waypoint waypoint : route.getWaypoints()) {
            if (waypoint.getMarker() == null) {
                waypoint.setMarker(map.addMarker(new MarkerOptions()
                        .position(new LatLng(waypoint.getLat(), waypoint.getLon()))
                        .title(waypoint.getName())));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        googleMapsDirectionsAPI.executeDirections(new LatLng(mUserPosition.getLatitude(),mUserPosition.getLongitude()), marker.getPosition());
        return false;
    }


}
