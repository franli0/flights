package com.fengsui.flights.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fengsui.flights.R;
import com.fengsui.flights.utils.Airport;
import com.fengsui.flights.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public abstract class AirportAdapter extends RecyclerView.Adapter<AirportAdapter.ViewHolder> {

    Context mContext;
    int s = -1;
    ViewHolder ss;

    ArrayList<Airport> airportArrayList = new ArrayList<>();

    public AirportAdapter(Context context) {
        mContext = context;
        try {
            JSONArray jsonArray = Tools.loadJSONFromAsset(mContext, "myairport.json");
            for (int i = 0; i < jsonArray.length(); i++) {
                Airport airport = new Airport(jsonArray.getJSONObject(i));
                airportArrayList.add(airport);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public AirportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_airport, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirportAdapter.ViewHolder holder, int position) {
        holder.setText(airportArrayList.get(position).getName(mContext));
    }

    @Override
    public int getItemCount() {
        return airportArrayList.size();
    }

    protected abstract boolean onSelect(Airport airport);

    public final class ViewHolder extends  RecyclerView.ViewHolder {
        TextView textView;
        ViewGroup item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.airport_array);
            item = itemView.findViewById(R.id.airport_layout);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Airport airport = airportArrayList.get(p);
                    if(p != s && onSelect(airport)) {
                        if(ss != null) {
                            ss.setSelectColor(false);
                        }
                        setSelectColor(true);
                        s = p;
                    }
                }
            });
        }
        public void setText(String name){
            textView.setText(name);
        }

        public void setSelectColor(boolean f){
            if(f){
                ss = ViewHolder.this;
                textView.setBackgroundColor(mContext.getColor(R.color.green_300));
            }else{
                textView.setBackgroundColor(mContext.getColor(R.color.t));
            }
        }
    }
}
