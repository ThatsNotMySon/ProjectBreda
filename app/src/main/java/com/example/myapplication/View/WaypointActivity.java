package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Model.RouteData.Waypoint;
import com.example.myapplication.R;

public class WaypointActivity extends AppCompatActivity {

    private Waypoint waypoint;
    private TextView nameTV;
    private TextView descTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        Intent i = getIntent();
        this.waypoint = (Waypoint) i.getSerializableExtra("WAYPOINT");
        this.nameTV = findViewById(R.id.name_tv);
        this.descTV = findViewById(R.id.description_tv);

        nameTV.setText(waypoint.getDescriptionNL().split("\\r?\\n")[0]);
        if(waypoint.getDescriptionNL().split("\\r?\\n").length > 1){
            descTV.setText(waypoint.getDescriptionNL().split("\\r?\\n")[1]);
        } else {
            descTV.setText("Geen beschrijving beschikbaar!");
        }

    }
}
