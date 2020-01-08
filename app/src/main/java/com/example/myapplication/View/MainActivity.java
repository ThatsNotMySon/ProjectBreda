package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Model.Datamanagement.Database;
import com.example.myapplication.Model.API.GoogleMapsDirectionsAPI;
import com.example.myapplication.Model.Datamanagement.DatabaseManager;
import com.example.myapplication.Model.LocationData.LocationApi;
import com.example.myapplication.Model.LocationData.LocationApiListener;
import com.example.myapplication.Model.LocationData.LocationCallback;
import com.example.myapplication.Model.RouteData.Route;
import com.example.myapplication.Model.RouteData.Waypoint;
import com.example.myapplication.Model.RouteTracker;
import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private static String TAG = "MainActivity";
    private static String languageCode = "en";

    //Voeg locationListeners aan dit toe om het naar de locatie te laten luisteren
    private ArrayList<LocationApiListener> locationListeners;
    private RouteTracker routeTracker;
    private Route route;
    private GoogleMap map;
    private Marker testmarker;
    private LocationApi locationApi;
    private Location mUserPosition;
    private GoogleMapsDirectionsAPI googleMapsDirectionsAPI;
    private Database currentFirst;
    private ArrayList<Database> allwaypoints;
    private ArrayList<Database> currentRouteWaypoints;
    private ArrayList<LatLng> waypointsLatLng;
    private String lang;

    private void sendToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void sendToast() {
        sendToast(getResources().getString(R.string.ErrorMessage));
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Resources resources = getBaseContext().getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(lang);
        getBaseContext().getApplicationContext().createConfigurationContext(configuration);

        Configuration cfg = new Configuration();
        if (!TextUtils.isEmpty(lang)) {
            cfg.locale = new Locale(lang);
        }
        else {
            cfg.locale = Locale.getDefault();
        }
        this.getResources().updateConfiguration(cfg, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setLocale(DatabaseManager.with(this).getLanguage());
        } catch (Exception ex) {
            sendToast();
        }
        setContentView(R.layout.activity_main);
        try {
            currentFirst = (Database) getIntent().getSerializableExtra("routeId");
            allwaypoints = (ArrayList<Database>) getIntent().getSerializableExtra("allwaypoints");

            currentRouteWaypoints = new ArrayList<>();
            for (Database database : allwaypoints) {
                if (database.getRouteID() == currentFirst.getRouteID()) {
                    currentRouteWaypoints.add(database);
                }
            }
        } catch (Exception e) {
            sendToast();
        }
        //LocationApi wordt hier geinitialiseer. Maar de route bestaat nu alleen nog uit een klasse zonder waardes.
        this.route = new Route();
        try {
            for (Database db : currentRouteWaypoints) {
                route.addWaypoint(
                        new Waypoint(
                                db.getX(),
                                db.getY(),
                                String.valueOf(db.getId()),
                                db.getInfonl(),
                                db.getInfoen()
                        )
                );
            }
        } catch (Exception e) {
            sendToast();
        }

        try {
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
                            if(!waypoint.getVisited()) {
                                waypoint.setVisited(true);
                                Intent intent = new Intent(MainActivity.this, WaypointActivity.class);
                                intent.putExtra("WAYPOINT", waypoint);
                                startActivity(intent);
                            }
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
        } catch (Exception ex) {
            sendToast();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            Intent i = null;
            switch (item.getItemId()) {
                case R.id.settingsItem:
//                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                    i = new Intent(this, SettingsActivity.class);
                    startActivity(i);
                    return true;
                case R.id.routeItem:
//                Toast.makeText(this, "Route", Toast.LENGTH_SHORT).show();
                    i = new Intent(this, RouteActivity.class);
                    startActivity(i);
                    return true;
                case R.id.helpItem:
//                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
                    i = new Intent(this, HelpActivity.class);
                    startActivity(i);
                    return true;
                case R.id.testGeofence:
                    //startGeofence();
                    return true;
            }
        } catch (Exception ex) {
            sendToast();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            this.map = googleMap;
            map.setOnMarkerClickListener(this);
            map.setOnMapClickListener(this);
            this.googleMapsDirectionsAPI = new GoogleMapsDirectionsAPI(this, map);
            this.waypointsLatLng = new ArrayList<>();
            for (Waypoint waypoint : route.getWaypoints()) {
                waypointsLatLng.add(new LatLng(waypoint.getLat(),waypoint.getLon()));
                if (waypoint.getMarker() == null) {
                    waypoint.setMarker(map.addMarker(new MarkerOptions()
                            .position(new LatLng(waypoint.getLat(), waypoint.getLon()))
                            .title(waypoint.getName())));
                }
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.573931, 4.764335),13));
        } catch (Exception ex) {
            sendToast();
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        try {
            if (mUserPosition != null) {
                googleMapsDirectionsAPI.executeDirections(new LatLng(mUserPosition.getLatitude(), mUserPosition.getLongitude()), waypointsLatLng);
            }
        } catch (Exception ex) {
            sendToast();
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            for (Waypoint wp : route.getWaypoints()){
                if(marker.equals(wp.getMarker())){
                    Intent intent = new Intent(MainActivity.this, WaypointActivity.class);
                    intent.putExtra("WAYPOINT", wp);
                    startActivity(intent);
                }
            }
        } catch (Exception ex) {
            sendToast();
        }

        return false;
    }
}
