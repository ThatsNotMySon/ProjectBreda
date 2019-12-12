package com.example.myapplication.Model.LocationData;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class LocationManager implements LocationApiListener {

    private  LocationApi locationApi;

    private Location lastLocation;
    protected double meters;

    public LocationManager(Context context) {
        this.locationApi = new LocationApi(this, context);
    }

    @Override
    public void OnLocationChanged(Location location) {
        Log.i("location", location.toString());
    }

    @Override
    public void OnStatusChanged(int status) {

    }

    @Override
    public void OnProviderEnabled(String provider) {

    }

    @Override
    public void OnProviderDisabled(String provider) {

    }
}
