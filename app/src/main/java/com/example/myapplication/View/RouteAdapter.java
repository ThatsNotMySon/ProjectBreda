package com.example.myapplication.View;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Datamanagement.Database;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {

    private ArrayList<Database> dataset;
    private ArrayList<Database> allwaypoints;

    public RouteAdapter(ArrayList<Database> dataset, ArrayList<Database> allwaypoints){
        this.dataset = dataset;
        this.allwaypoints = allwaypoints;
        Log.d("dataset", "changed dataset");

    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_route_gird, parent, false);
        Log.d("oncreate", "returned blindwalls");
        return new RouteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        final Database database = dataset.get(position);
        holder.routeID.setText("" + database.getRouteID());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        v.getContext(),
                        MainActivity.class
                );
                intent.putExtra("routeId",  database);
                intent.putExtra("allwaypoints", allwaypoints);


//                v.getContext().startActivity(intent);
                v.getContext().startActivity(intent);
            }
        });
        Log.d("onbind", "set to recycle");
    }

    @Override
    public int getItemCount() {
        Log.d("sizedata", "Send size");
        return dataset.size();
    }

    public class RouteViewHolder extends RecyclerView.ViewHolder{

        public TextView routeID;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeID = itemView.findViewById(R.id.route_name);
            Log.d("viewholder","assinged viewholder");
        }
    }
}
