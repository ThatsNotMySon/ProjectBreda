package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.Model.LocationData.LocationApi;
import com.example.myapplication.Model.LocationData.LocationApiListener;
import com.example.myapplication.Model.RouteData.Route;
import com.example.myapplication.Model.RouteTracker;
import com.example.myapplication.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    //Voeg locationListeners aan dit toe om het naar de locatie te laten luisteren
    private ArrayList<LocationApiListener> locationListeners;
    private RouteTracker routeTracker;
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LocationApi wordt hier geinitialiseer. Maar de route bestaat nu alleen nog uit een klasse zonder waardes.
        this.route = new Route();
        this.routeTracker = new RouteTracker(this.route);
        this.locationListeners = new ArrayList<>();
        this.locationListeners.add(this.routeTracker);
        LocationApi locationApi = new LocationApi(locationListeners,this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        switch (item.getItemId()){
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
        }
        return super.onOptionsItemSelected(item);
    }
}
