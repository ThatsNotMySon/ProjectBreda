package com.example.myapplication.Model.RouteData;

public class Waypoint {

    private PointOfInterest pointOfInterest;
    private Boolean visited = false;

    private float longitude;
    private float latitude;
    
    public Waypoint(float latitude, float longitude) {
        
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

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
