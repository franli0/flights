package com.fengsui.flights.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fengsui.flights.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DepartAdapter extends RecyclerView.Adapter<DepartAdapter.ViewHolder> {
    JSONArray flightArray;

    public DepartAdapter(JSONArray array) {
        this.flightArray = array;
    }

    @NonNull
    @Override
    public DepartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depart, parent, false);
        return new DepartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartAdapter.ViewHolder holder, int position) {
        try {
            holder.setData(flightArray.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (flightArray != null) {
            return flightArray.length();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView flightId;
        TextView scheduledTime;
        TextView estimatedTime;
        TextView airlineCompany;
        TextView to;
        TextView gate;
        TextView flightStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flightId = itemView.findViewById(R.id.flight_id);
            scheduledTime = itemView.findViewById(R.id.scheduled_time);
            estimatedTime = itemView.findViewById(R.id.estimated_time);
            airlineCompany = itemView.findViewById(R.id.airline_company);
            to = itemView.findViewById(R.id.to);
            gate = itemView.findViewById(R.id.gate);
            flightStatus = itemView.findViewById(R.id.flight_status);
            itemView.setFocusable(true);
            itemView.setBackgroundResource(R.drawable.flight_item);
        }

        public void setData(JSONObject jsonObject) {
            try {
                flightId.setText(jsonObject.getString("AirlineID")+jsonObject.getString("FlightNumber"));
                scheduledTime.setText(jsonObject.getString("ScheduleDepartureTime").substring(11, 16));
                estimatedTime.setText(jsonObject.getString("EstimatedDepartureTime").substring(11, 16));
                airlineCompany.setText(jsonObject.getString("AirlineID"));
                to.setText(jsonObject.getString("ArrivalAirportID"));
                gate.setText(jsonObject.getString("Gate"));
                flightStatus.setText(jsonObject.getString("DepartureRemark"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
