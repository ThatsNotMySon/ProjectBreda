package com.example.myapplication.Model.RouteData;

import android.location.Location;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

public class Waypoint implements Serializable {

    private PointOfInterest pointOfInterest;
    private Boolean visited = false;
    private String name;
    private String descriptionNL;
    private String descriptionEN;
    private transient Location location;
    private float lat;
    private float lon;
    private transient Marker marker;
    
    public Waypoint(float latitude, float longitude, String name, String descriptionNL, String descriptionEN) {
         this.location = new Location("");
         this.location.setLatitude(latitude);
         this.location.setLongitude(longitude);
         this.lat = latitude;
         this.lon = longitude;
         this.name = name;
         this.descriptionNL = descriptionNL;
         this.descriptionEN = descriptionEN;
         this.marker = null;
    }

    public String getDescriptionNL() {
        return descriptionNL;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }


    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public PointOfInterest getPointOfInterest() {
        return pointOfInterest;
    }

    public void setPointOfInterest(PointOfInterest pointOfInterest) {
        this.pointOfInterest = pointOfInterest;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Location getLocation() {
        return location;
    }
}
