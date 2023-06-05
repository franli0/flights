package com.fengsui.flights.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengsui.flights.R;
import com.fengsui.flights.utils.Date;

public class ColumnView extends RelativeLayout {
    TextView textView;
    ViewGroup column;

    public static int color = Color.GRAY;

    public Date date;

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.column);
        inflate(context, R.layout.view_column, this);
        textView = findViewById(R.id.textView2);
        column = findViewById(R.id.layoutColumn);
    }

    public void setText(CharSequence text){
        textView.setText(text);
    }

    public void disableDate(Date date, boolean isSelected){
        this.date = date;
        textView.setTextColor(Color.GRAY);
        textView.setText(date.d+"");
        column.setFocusable(false);
        setSelectColor(isSelected);
    }

    public void setDate(Date date, boolean isToday, boolean isSelected){
        this.date = date;
        textView.setTextColor(Color.WHITE);
        textView.setText(date.d+"");
        column.setFocusable(true);
        /*if(isToday){
            textView.setTextColor(getContext().getColor(R.color.blue_500));
        }else{
            textView.setTextColor(Color.WHITE);
        }*/
        setSelectColor(isSelected);
    }

    public void setSelectColor(boolean f){
        if(f){
            textView.setBackgroundColor(color);
        }else{
            textView.setBackgroundColor(getContext().getColor(R.color.t));
        }
    }

    public void setId(int id){
        column.setId(id);
    }

    public void setOnClickListener(OnClickListener listener){
        column.setOnClickListener(listener);
    }

    public void focus(){
        column.requestFocus();
    }
}
