package com.fengsui.flights.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fengsui.flights.R;
import com.fengsui.flights.utils.Ad;
import com.fengsui.flights.utils.Airport;
import com.fengsui.flights.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public abstract class AdAdapter extends RecyclerView.Adapter<AdAdapter.ViewHolder> {
    Context mContext;
    int s = -1;
    AdAdapter.ViewHolder ss;

    ArrayList<Ad> adArrayList = new ArrayList<>();

    public AdAdapter(Context context) {
        mContext = context;
        try {
            JSONArray jsonArray = Tools.loadJSONFromAsset(mContext, "ad.json");
            for (int i = 0; i < jsonArray.length(); i++) {
                Ad ad = new Ad(jsonArray.getJSONObject(i));
                adArrayList.add(ad);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public AdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ad, parent, false);
        return new AdAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdAdapter.ViewHolder holder, int position) {
        holder.setText(adArrayList.get(position).getName(mContext));
    }

    @Override
    public int getItemCount() {
        return adArrayList.size();
    }

    protected abstract boolean onSelect(Ad ad);

    public final class ViewHolder extends  RecyclerView.ViewHolder {
        TextView textView;
        ViewGroup item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ad_array);
            item = itemView.findViewById(R.id.ad_layout);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    Ad ad = adArrayList.get(p);
                    if(p != s && onSelect(ad)) {
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
                ss = AdAdapter.ViewHolder.this;
                textView.setBackgroundColor(mContext.getColor(R.color.green_300));
            }else{
                textView.setBackgroundColor(mContext.getColor(R.color.t));
            }
        }
    }
}
