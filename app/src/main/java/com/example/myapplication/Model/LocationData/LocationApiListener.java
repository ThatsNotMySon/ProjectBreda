package com.example.myapplication.Model.LocationData;

import android.location.Location;
import android.os.AsyncTask;

import java.security.Provider;

public interface LocationApiListener {
    void OnLocationChanged(Location location);
    void OnStatusChanged(int status);
    void OnProviderEnabled(String provider);
    void OnProviderDisabled(String provider);
}
