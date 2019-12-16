package com.example.myapplication.Model.LocationData;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class LocationApi {

    private android.location.LocationManager locationManager;
    private LocationListener locationListener;
    private ArrayList<LocationApiListener> locationApiListeners;
    private Location lastLocation;
    private LocationCallback locationCallback;

    public LocationApi(ArrayList<LocationApiListener> locationApiListener , Context context, LocationCallback locationCallback) {
        this.locationApiListeners = locationApiListener;
        this.locationCallback = locationCallback;
        //setupLocationService kan ook later aangeroepen worden.
        setupLocationService(context);
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setupLocationService(Context context) {
        this.locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationCallback.locationCallback(location);
                for(LocationApiListener locationApiListener : locationApiListeners) {
                    locationApiListener.OnLocationChanged(location);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                for(LocationApiListener locationApiListener : locationApiListeners) {
                    locationApiListener.OnStatusChanged(status);
                }
            }

            @Override
            public void onProviderEnabled(String provider) {
                for(LocationApiListener locationApiListener : locationApiListeners) {
                    locationApiListener.OnProviderEnabled(provider);
                }
            }

            @Override
            public void onProviderDisabled(String provider) {
                for(LocationApiListener locationApiListener : locationApiListeners) {
                    locationApiListener.OnProviderDisabled(provider);
                };
            }
        };
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this.locationListener);
    }

}
