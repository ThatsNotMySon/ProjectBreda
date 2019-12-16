package com.example.myapplication.Model.RouteData;

import android.location.Location;

import java.io.Serializable;

public class Waypoint implements Serializable {

    private PointOfInterest pointOfInterest;
    private Boolean visited = false;

    private transient Location location;
    private float lat;
    private float lon;
    
    public Waypoint(float latitude, float longitude) {
         this.location = new Location("");
         this.location.setLatitude(latitude);
         this.location.setLongitude(longitude);
         this.lat = latitude;
         this.lon = longitude;
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
