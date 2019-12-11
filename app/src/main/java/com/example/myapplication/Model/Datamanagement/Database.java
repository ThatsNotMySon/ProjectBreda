package com.example.myapplication.Model.Datamanagement;

public class Database {

    private float x;
    private float y;
    private String info;
    private int id;

    public Database(float x, float y, String info, int id) {
        this.x = x;
        this.y = y;
        this.info = info;
        this.id = id;
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
}
