package com.fengsui.flights.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.fengsui.flights.R;
import com.fengsui.flights.adapter.AdAdapter;
import com.fengsui.flights.adapter.AirportAdapter;
import com.fengsui.flights.utils.Ad;
import com.fengsui.flights.utils.Airport;
import com.fengsui.flights.utils.Date;
import com.fengsui.flights.utils.HttpUtil;
import com.fengsui.flights.view.CalendarView;

public class FlightsFragment extends Fragment {

    Airport airportSelect;
    Ad adSelect;
    ViewGroup queryButton;

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
        return inflater.inflate(R.layout.fragment_flights, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CalendarView calendar = view.findViewById(R.id.calendar_view);
        TextView dateText = view.findViewById(R.id.date_text);
        dateText.setText(calendar.getDate().toString());
        queryButton = view.findViewById(R.id.button_query);

        calendar.setOnSelectListener(new CalendarView.OnSelectListener() {
            @Override
            public void onSelect(Date date) {
                dateText.setText(date.toString());
            }
        });

        queryButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_big));
                }else{
                    v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.scale_small));
                }
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Query Button", "Query Button Clicked");
                Bundle bundle = new Bundle();
                bundle.putString("date", calendar.getDate().toString());
                bundle.putString("airport", airportSelect.code);
                bundle.putString("ad", adSelect.code);
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment, FlightsInfoFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .addToBackStack("replacement")
                        .commit();

            }
        });

        enableBtn(false);
        airportSelect = null;
        adSelect = null;
        TextView airportText = view.findViewById(R.id.airport_text);
        airportText.setText(getText(R.string.airport));
        TextView adText = view.findViewById(R.id.ad_text);
        adText.setText(getText(R.string.ad));
        RecyclerView airportRecyclerView = view.findViewById(R.id.airport_list);
        RecyclerView adRecyclerView = view.findViewById(R.id.ad_list);
        AirportAdapter airportAdapter = new AirportAdapter(getContext()) {
            @Override
            protected boolean onSelect(Airport airport) {
                if (airportSelect == null || !airportSelect.code.equals(airport.code)) {
                    airportSelect = airport;
                    airportText.setText(airport.getName(getContext()));
                    airportText.setTextColor(Color.WHITE);
                    enableBtn(adSelect != null);
                    return true;
                }
                return false;
            }
        };
        AdAdapter adAdapter = new AdAdapter(getContext()) {
            @Override
            protected boolean onSelect(Ad ad) {
                if (adSelect == null || !adSelect.code.equals(ad.code)) {
                    adSelect = ad;
                    adText.setText(ad.getName(getContext()));
                    adText.setTextColor(Color.WHITE);
                    enableBtn(airportSelect != null);
                    return true;
                }
                return false;
            }
        };
        airportRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        airportRecyclerView.setAdapter(airportAdapter);
        adRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adRecyclerView.setAdapter(adAdapter);
    }

    public void enableBtn(boolean flag){
        queryButton.setFocusable(flag);
        if(flag){
            queryButton.setAlpha(1f);
            queryButton.requestFocus();
        }else{
            queryButton.setAlpha(0.5f);
        }
    }
}