package com.fengsui.flights.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengsui.flights.R;
import com.fengsui.flights.utils.Date;

import java.util.ArrayList;
import java.util.Calendar;


public class CalendarView extends RelativeLayout implements View.OnClickListener {
    int year = 2023;
    int month = 1;

    Date sd = new Date();

    Date now = new Date();
    TextView textView;
    ArrayList<ColumnView> columns = new ArrayList<>();

    OnSelectListener listener = null;

    public static interface OnSelectListener{
        public void onSelect(Date date);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_calendar, this);
        textView = findViewById(R.id.textView);
        Button prev = findViewById(R.id.buttonLeft);
        Button next = findViewById(R.id.buttonRight);
        ViewGroup all = findViewById(R.id.layoutColumns);
        for (int i=0;i<all.getChildCount();i++){
            ViewGroup row = (ViewGroup) all.getChildAt(i);
            for (int j=0;j<row.getChildCount();j++){
                ColumnView c = (ColumnView) row.getChildAt(j);
                c.setId(columns.size());
                columns.add(c);
            }
        }
        Calendar calendar = Calendar.getInstance();

        select(now);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    public void select(Date date){
        year = date.y;
        month = date.m;
        sd.copy(date);
        init();
        if(listener != null) {
            listener.onSelect(date);
        }
    }

    public void next(){
        if(month+1 > 11){
            month = 0;
            year += 1;
        }else{
            month += 1;
        }
        init();
    }

    public void prev(){
        if(month-1 < 0){
            month = 11;
            year -= 1;
        }else{
            month -= 1;
        }
        init();
    }

    public String getMonthText(){
        if(month<9){
            return "0"+(month+1);
        }
        return ""+(month+1);
    }

    public Date getDate(){
        return sd;
    }

    public Date getCurrentDate(int d){
        return new Date(year, month, d);
    }

    public Date getPrevDate(int d){
        if(month-1 < 0){
            return new Date(year-1, 11, d);
        }
        return new Date(year, month-1, d);
    }

    public Date getNextDate(int d){
        if(month+1 > 11){
            return new Date(year+1, 0, d);
        }
        return new Date(year, month+1, d);
    }

    public void init(){
        textView.setText(year+" - "+getMonthText());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,1);
        int offset = calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.roll(Calendar.DATE,-1);
        int max = calendar.get(Calendar.DATE);
        calendar.set(year,month,1);
        calendar.add(Calendar.DATE,-1);
        int last = calendar.get(Calendar.DATE);
        int d = 1;
        int o = 1;
        for (int i=0;i<columns.size();i++){
            ColumnView columnView = columns.get(i);
            columnView.setOnClickListener(this);
            if(i<offset){
                Date cd = getPrevDate(last-(offset-i)+1);
                columnView.disableDate(cd, sd.isSame(cd));
            }else if(d > max){
                Date cd = getNextDate(o++);
                columnView.disableDate(cd, sd.isSame(cd));
            }else{
                Date cd = getCurrentDate(d);
                if(!cd.isBefore(now)){
                    columnView.setDate(cd, now.isSame(cd), sd.isSame(cd));
                }else {
                    columnView.disableDate(cd, sd.isSame(cd));
                }
                d++;
            }
        }
    }

    public void setOnSelectListener(OnSelectListener l){
        listener = l;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLeft:
                prev();
                break;
            case R.id.buttonRight:
                next();
                break;
            default:
                try {
                    ColumnView columnView = columns.get(v.getId());
                    select(columnView.date);
                }catch (Exception e){e.printStackTrace();}

        }
    }
}
