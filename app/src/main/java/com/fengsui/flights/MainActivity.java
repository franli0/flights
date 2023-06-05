package com.fengsui.flights;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.FragmentActivity;
import com.fengsui.flights.fragment.FlightsFragment;
import com.fengsui.flights.utils.HomeWatcher;
import com.fengsui.flights.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {
    protected HomeWatcher homeWatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setLocale(this, Locale.TAIWAN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        HomeWatcher homeWatcher = new HomeWatcher(this);
        homeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                finish();
            }

            @Override
            public void onHomeLongPressed() {

            }
        });
        homeWatcher.startWatch();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, new FlightsFragment(), "Home").commitNow();
        }
    }

    @Override
    public void finish() {
        if(homeWatcher != null){
            homeWatcher.stopWatch();
        }
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().findFragmentByTag("Home") == null){
            super.onBackPressed();
        }else {
            finish();
        }
    }
}