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

public class ArriveAdapter extends RecyclerView.Adapter<ArriveAdapter.ViewHolder> {
    JSONArray flightArray;

    public ArriveAdapter(JSONArray array) {
        this.flightArray = array;
    }

    @NonNull
    @Override
    public ArriveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_arrive, parent, false);
        return new ArriveAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArriveAdapter.ViewHolder holder, int position) {
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
        TextView from;
        TextView gate;
        TextView flightStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flightId = itemView.findViewById(R.id.flight_id);
            scheduledTime = itemView.findViewById(R.id.scheduled_time);
            estimatedTime = itemView.findViewById(R.id.estimated_time);
            airlineCompany = itemView.findViewById(R.id.airline_company);
            from = itemView.findViewById(R.id.from);
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
                from.setText(jsonObject.getString("DepartureAirportID"));
                gate.setText(jsonObject.getString("Gate"));
                flightStatus.setText(jsonObject.getString("ArrivalRemark"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}