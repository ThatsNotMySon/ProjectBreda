package com.example.myapplication.Model.RouteData;

import android.graphics.Point;
import android.graphics.PointF;

public class PointOfInterest {

    private PointF location;
    private String name;
    private String description;

    public PointOfInterest(){

    }

    public PointF getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
