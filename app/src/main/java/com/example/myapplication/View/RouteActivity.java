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
        databaseManager.addData(new Database(51.592833f, 4.778472f, "The light house", 1));
        databaseManager.addData(new Database(51.590612f, 4.776167f, "Kasteel van Breda", 1));
        databaseManager.addData(new Database(51.589695f, 4.776138f, "Stadhouderspoort", 1));
        databaseManager.addData(new Database(51.590028f, 4.774362f, "Huis van Brecht (rechter zijde)", 1));
        databaseManager.addData(new Database(51.590195f, 4.773445f, "Spanjaardsgat (rechter zijde)", 1));
        databaseManager.addData(new Database(51.589833f, 4.773333f, "Begin Vismarkt", 1));
        databaseManager.addData(new Database(51.589362f, 4.774445f, "Begin Havermarkt", 1));
        databaseManager.addData(new Database(51.588833f, 4.775278f, "Grote Kerk", 1));
        databaseManager.addData(new Database(51.588195f, 4.775138f, "Het Poortje", 1));
        databaseManager.addData(new Database(51.587083f, 4.775750f, "Ridderstraat", 1));
        databaseManager.addData(new Database(51.587417f, 4.776555f, "Grote Markt", 1));
        databaseManager.addData(new Database(51.588028f, 4.776333f, "Bevrijdingsmonument", 1));
        databaseManager.addData(new Database(51.588750f, 4.776112f, "Stadhuis", 1));
        databaseManager.addData(new Database(51.587638f, 4.777250f, "Antonius van Paduakerk", 1));
        databaseManager.addData(new Database(51.592541f, 4.779461f, "Pad ten westen van monument", 2));
        databaseManager.addData(new Database(51.592833f, 4.778472f, "The light house", 2));
        databaseManager.addData(new Database(51.590612f, 4.777f, "Einde park", 2));
    }

}
