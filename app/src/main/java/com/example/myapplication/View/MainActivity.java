package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Model.API.GeofenceBroadcastReceiver;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.places.*;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, ResultCallback<Status> {

    private static String TAG = "MainActivity";

    //Voeg locationListeners aan dit toe om het naar de locatie te laten luisteren
    private ArrayList<LocationApiListener> locationListeners;
    private RouteTracker routeTracker;
    private Route route;
    private GoogleMap map;
    private Marker testmarker;
    private LocationApi locationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LocationApi wordt hier geinitialiseer. Maar de route bestaat nu alleen nog uit een klasse zonder waardes.
        this.route = new Route();
        route.addWaypoint(new Waypoint(51.514157f, 4.448641f));
        this.routeTracker = new RouteTracker(this.route);
        this.locationListeners = new ArrayList<>();
        this.locationListeners.add(this.routeTracker);
        this.locationApi = new LocationApi(locationListeners, this, new LocationCallback() {
            @Override
            public void locationCallback(Location location) {
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("Testmarker");
                if (map != null) {
                    if (testmarker != null) {
                        testmarker.remove();
                    }
                    testmarker = map.addMarker(markerOptions);
                }
                for(Waypoint waypoint: route.getWaypoints()){
                    if(location.distanceTo(waypoint.getLocation()) <= 50){
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
    }

    @Override
    public void onMapClick(LatLng latLng) {
//      markerForGeofence(latLng);
    }

//    Marker geofenceMarker;
//
//    private void markerForGeofence(LatLng latLng) {
//        MarkerOptions markerOptions = new MarkerOptions()
//                .position(latLng)
//                .title("GeofenceTest");
//
//        if (map != null) {
//            if (geofenceMarker != null) {
//                geofenceMarker.remove();
//            }
//            geofenceMarker = map.addMarker(markerOptions);
//        }
//    }

//    GeofencingRequest geofencingRequest;
//
//    private void startGeofence() {
//        if (geofenceMarker != null) {
//            Geofence geofence = createGeofence(geofenceMarker.getPosition(), 50);
//            geofencingRequest = createGeoRequest(geofence);
//            addGeoFence(geofence);
//        }
//    }
//
//    private void addGeoFence(Geofence geofence) {
//        GeofencingClient client = LocationServices.getGeofencingClient(this);
//        client.addGeofences(geofencingRequest, createGeofencingPendingIntent())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        drawGeofence();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//                });
//    }

//    PendingIntent pendingIntent;
//
//    private PendingIntent createGeofencingPendingIntent() {
//        if (pendingIntent != null) {
//            return pendingIntent;
//        }
//
//        Intent i = new Intent(this, GeofenceBroadcastReceiver.class);
//        return pendingIntent = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//    }

//    private GeofencingRequest createGeoRequest(Geofence geofence) {
//        return new GeofencingRequest.Builder()
//                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//                .addGeofence(geofence)
//                .build();
//    }

//    private Geofence createGeofence(LatLng position, int radius) {
//        return new Geofence.Builder()
//                .setRequestId("Test Geofence")
//                .setCircularRegion(position.latitude, position.longitude, radius)
//                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//                .setExpirationDuration(60 * 60 * 1000)
//                .build();
//    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onResult(@NonNull Status status) {
    }

//    private void drawGeofence() {
//        CircleOptions circleOptions = new CircleOptions()
//                .center(geofenceMarker.getPosition())
//                .strokeColor(Color.argb(50, 70, 70, 70))
//                .fillColor(Color.argb(100, 150, 150, 150))
//                .radius(50);
//        map.addCircle(circleOptions);
//    }
}
