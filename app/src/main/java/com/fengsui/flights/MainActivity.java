package com.fengsui.flights;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.FragmentActivity;

import com.fengsui.Utils.TDXapi;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {
    private Spinner airportSpinner;
    private Button accessToken;
    private TDXapi tdxApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        airportSpinner = (Spinner) findViewById(R.id.airport_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.airport_array_tw, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        airportSpinner.setAdapter(adapter);
        accessToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tdxApi;
            }
        });
    }
}