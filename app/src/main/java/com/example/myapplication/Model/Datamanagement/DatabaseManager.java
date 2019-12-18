package com.example.myapplication.Model.Datamanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "waypoint_tabel";
    private static final String COL1 = "ID";
    private static final String COL2 = "x";
    private static final String COL3 = "y";
    private static final String COL4 = "infonl";
    private static final String COL5 = "infoen";
    private static final String COL6 = "routeID";
    private static final String[] COLUMS = {COL1, COL2, COL3, COL4, COL5, COL6};

    public DatabaseManager(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +"x FLOAT, "
                + "y FLOAT, " + "infonl STRING, " + "infoen STRING, " + "routeID INTEGER )";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteOne(float x, float y, String infonl, String infoen, int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] {id +""  });
        sqLiteDatabase.close();

    }

    public List<Database> allWaypoints() {

        List<Database> databases = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Database database = null;

        if (cursor.moveToFirst()) {
            do {
                database = new Database();
                database.setId(Integer.parseInt(cursor.getString(0)));
                database.setX(cursor.getFloat(1));
                database.setY(cursor.getFloat(2));
                database.setInfonl(cursor.getString(3));
                database.setInfoen(cursor.getString(4));
                database.setRouteID(Integer.parseInt(cursor.getString(5)));
                databases.add(database);
            } while (cursor.moveToNext());
        }

        return databases;
    }

    public Database database(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                COLUMS,
                " id = ?",
                new String[]{ String.valueOf(id)},
                null,
                null,
                null,
                null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Database database = new Database();
        database.setId(Integer.parseInt(cursor.getString(0)));
        database.setX(cursor.getFloat(1));
        database.setY(cursor.getFloat(2));
        database.setInfonl(cursor.getString(3));
        database.setInfoen(cursor.getString(4));
        database.setRouteID(Integer.parseInt(cursor.getString(5)));

        return database;
    }


    public boolean addData(Database database){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, database.getX());
        contentValues.put(COL3, database.getY());
        contentValues.put(COL4, database.getInfonl());
        contentValues.put(COL5, database.getInfoen());
        contentValues.put(COL6, database.getRouteID());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
