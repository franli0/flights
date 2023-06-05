package com.fengsui.flights.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.fengsui.flights.R;
import com.fengsui.flights.adapter.ArriveAdapter;
import com.fengsui.flights.adapter.DepartAdapter;
import com.fengsui.flights.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;

public class FlightsInfoFragment extends Fragment {
    ArriveAdapter arriveAdapter;
    DepartAdapter departAdapter;
    RecyclerView flightList;

    public FlightsInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flights_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView flightList = view.findViewById(R.id.flight_list);
        if (getArguments() != null) {
            HttpUtil client = new HttpUtil(getContext());
            Bundle bundle = getArguments();
            if (bundle.getString("ad") == "Arrive") {
                client.get("url", new HttpUtil.Callback() {
                    @Override
                    public void onHttpResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            arriveAdapter = new ArriveAdapter(jsonArray);
                            flightList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            flightList.setAdapter(arriveAdapter);
                            Log.d("Arrive", bundle.getString("ad"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onHttpError(VolleyError error) {

                    }
                });
            } else {
                client.get("url", new HttpUtil.Callback() {
                    @Override
                    public void onHttpResponse(String response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                            departAdapter = new DepartAdapter(jsonArray);
                            flightList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            flightList.setAdapter(departAdapter);
                            Log.d("Depart", bundle.getString("ad"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onHttpError(VolleyError error) {

                    }
                });
            }
        }
    }
}