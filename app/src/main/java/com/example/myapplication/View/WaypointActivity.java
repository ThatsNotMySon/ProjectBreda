package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Model.RouteData.Waypoint;
import com.example.myapplication.R;

import java.util.Locale;

public class WaypointActivity extends AppCompatActivity {

    private Waypoint waypoint;
    private TextView nameTV;
    private TextView descTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waypoint);

        String location = Resources.getSystem().getConfiguration().locale.getLanguage();

        Intent i = getIntent();
        this.waypoint = (Waypoint) i.getSerializableExtra("WAYPOINT");
        this.nameTV = findViewById(R.id.name_tv);
        this.descTV = findViewById(R.id.description_tv);

        String noDescriptionText = getResources().getString(R.string.NoDescWaypoint);
        if(location.equals("nl")) {
            nameTV.setText(waypoint.getDescriptionNL().split("\\r?\\n")[0]);
            if (waypoint.getDescriptionNL().split("\\r?\\n").length > 1) {
                descTV.setText(waypoint.getDescriptionNL().split("\\r?\\n")[1]);
            } else {
                descTV.setText(noDescriptionText);
            }
        } else if (location.equals("en")){
            nameTV.setText(waypoint.getDescriptionEN().split("\\r?\\n")[0]);
            if (waypoint.getDescriptionEN().split("\\r?\\n").length > 1) {
                descTV.setText(waypoint.getDescriptionEN().split("\\r?\\n")[1]);
            } else {
                descTV.setText(noDescriptionText);
            }
        }

    }
}
