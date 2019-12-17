package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.Model.Datamanagement.Database;
import com.example.myapplication.Model.Datamanagement.DatabaseManager;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private ArrayList<Database> databases;
    private RouteAdapter routeAdapter;
    private ArrayList<Database> finallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        int orientation = getResources().getConfiguration().orientation;

        this.databaseManager = new DatabaseManager(getApplicationContext());
        //fillDatabase();

        databases = new ArrayList<>();
        databases = (ArrayList<Database>) this.databaseManager.allWaypoints();
        finallist = new ArrayList<>();

        int count = 0;
        for(Database database : databases){
            if(database.getRouteID() > count){
                finallist.add(database);
                count = database.getRouteID();
            }
        }


        recyclerView = findViewById(R.id.RoutesRecycle);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    this)
            );
        }

        this.routeAdapter = new RouteAdapter(finallist,databases);
        routeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(this.routeAdapter);
        Log.d("recyclerview", "recylerviewset");
    }

    public void fillDatabase(){
        databaseManager.addData(new Database(51.594112f, 4.779417f, "VVV Beginpunt tot maart 2019", 1));
        databaseManager.addData(new Database(51.593278f, 4.779388f, "Liefdeszuster", 1));
        databaseManager.addData(new Database(51.5925f, 4.779695f, "Nassau Baronie monument", 1));
        databaseManager.addData(new Database(51.592541f, 4.779461f, "Pad ten westen van monument", 2));
        databaseManager.addData(new Database(51.592833f, 4.778472f, "The light house", 2));
        databaseManager.addData(new Database(51.590612f, 4.777f, "Einde park", 2));
    }

}
