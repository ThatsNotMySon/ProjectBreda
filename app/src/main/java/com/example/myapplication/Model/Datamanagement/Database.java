package com.example.myapplication.Model.Datamanagement;

public class Database {

    private float x;
    private float y;
    private String info;
    private int id;
    private int routeID;

    public Database(float x, float y, String info, int id, int routeID) {
        this.x = x;
        this.y = y;
        this.info = info;
        this.id = id;
        this.routeID = routeID;
    }

    public Database() {

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }
}
