package com.example.myapplication.Model.RouteData;

import java.util.ArrayList;

public class Route {

    private ArrayList<Waypoint> waypoints;
    private String name;

    public Route() {

    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    public String getName() {
        return name;
    }

    public void addWaypoint(Waypoint waypoint){
        waypoints.add(waypoint);
    }

}
