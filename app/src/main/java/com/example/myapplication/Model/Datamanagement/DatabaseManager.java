package com.example.myapplication.Model.Datamanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "color_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "HUE";
    private static final String COL3 = "SAT";
    private static final String COL4 = "BRI";
    private static final String[] COLUMS = {COL1, COL2, COL3, COL4};

    public DatabaseManager(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +"HUE FLOAT, "
                + "SAT FLOAT, " + "BRI FLOAT )";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteOne(float hue, float bri, float sat, int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] {id +""  });
        sqLiteDatabase.close();

    }


    
}
