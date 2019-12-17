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
        fillDatabase();
       // fillDatabase();

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
        databaseManager.addData(new Database(51.356467f, 4.467650f, "VVV Beginpunt tot maart 2019", 1));
        databaseManager.addData(new Database(51.355967f, 4.467633f, "Liefdeszuster", 1));
        databaseManager.addData(new Database(51.355500f, 4.467817f, "Nassau Baronie monument", 1));
        databaseManager.addData(new Database(51.355500f, 4.467633f, "Pad ten westen van monument", 2));
        databaseManager.addData(new Database(51.355700f, 4.467083f, "The light house", 2));
        databaseManager.addData(new Database(51.354367f, 4.466200f, "Einde park", 2));
    }
}
