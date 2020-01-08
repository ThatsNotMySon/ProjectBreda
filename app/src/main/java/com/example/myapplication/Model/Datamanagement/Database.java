package com.example.myapplication.Model.Datamanagement;

import java.io.Serializable;

public class Database implements Serializable {

    private float x;
    private float y;
    private String infonl;
    private String infoen;
    private int id;
    private int routeID;

    public Database(float x, float y, String infonl, String infoen , int routeID) {
        this.x = x;
        this.y = y;
        this.infonl = infonl;
        this.infoen = infoen;
        this.routeID = routeID;
    }

    public String getInfoen() {
        return infoen;
    }

    public void setInfoen(String infoen) {
        this.infoen = infoen;
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

    public String getInfonl() {
        return infonl;
    }

    public void setInfonl(String info) {
        this.infonl = info;
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
