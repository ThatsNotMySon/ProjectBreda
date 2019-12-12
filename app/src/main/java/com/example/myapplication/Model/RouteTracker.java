package com.example.myapplication.Model;

import android.location.Location;

import com.example.myapplication.Model.LocationData.LocationApiListener;
import com.example.myapplication.Model.RouteData.Route;

public class RouteTracker implements LocationApiListener {

    private Route route;

    RouteTracker(Route route) {
        this.route = route;
    }

    @Override
    public void OnLocationChanged(Location location) {

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
