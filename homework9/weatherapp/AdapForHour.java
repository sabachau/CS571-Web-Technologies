package com.example.poojaandsaba.weatherapp;

/**
 * Created by poojaandsaba on 10/12/15.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapForHour extends ArrayAdapter<CandyForHour> {
    TextView tvTime,tvTemp;
    ImageView adImg;

public AdapForHour(Context context, int resourceId, List<CandyForHour> listOfCandy) {
    super(context, resourceId, listOfCandy);
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflaterForHour = LayoutInflater.from(getContext());

    View ivHourly = inflaterForHour.inflate(R.layout.smallcandy, parent, false);

    CandyForHour smallCon = getItem(position);
     adImg = (ImageView) ivHourly.findViewById(R.id.iconforhour);
     tvTime = (TextView) ivHourly.findViewById(R.id.timeforhour);
     tvTemp = (TextView) ivHourly.findViewById(R.id.tempforhour);
    tvTime.setText(smallCon.getTime());
    tvTemp.setText(smallCon.getTemperatVal());

    String img = smallCon.getIconId();
    if(img.equals("clear"))
    { adImg.setImageResource(R.drawable.clear);}
    if(img.equals("cloud_night"))
    {
        adImg.setImageResource(R.drawable.cloud_night);
    }
    else if(img.equals("cloud_day"))
    {
        adImg.setImageResource(R.drawable.cloud_day);
    }
    else if(img.equals("partly-cloudy-day"))
    {
        adImg.setImageResource(R.drawable.cloud_day);
    }
    else if(img.equals("partly-cloudy-night"))
    {
        adImg.setImageResource(R.drawable.cloud_night);
    }
    else if(img.equals("clear_night"))
    {
        adImg.setImageResource(R.drawable.clear_night);
    }
    else if(img.equals("cloudy"))
    {   adImg.setImageResource(R.drawable.cloudy);

    }
    else if(img.equals("fog"))
    {
        adImg.setImageResource(R.drawable.fog);
    }
    else if(img.equals("rain"))
    {
        adImg.setImageResource(R.drawable.rain);
    }
    else if(img.equals("sleet"))
    {
        adImg.setImageResource(R.drawable.sleet);
    }
    else if(img.equals("snow"))
    {
        adImg.setImageResource(R.drawable.snow);
    }
    else if(img.equals("wind"))
    {
        adImg.setImageResource(R.drawable.wind);
    }

    AbsListView.LayoutParams layoutParam = (AbsListView.LayoutParams) ivHourly.getLayoutParams();
    layoutParam.height=116;
    ivHourly.setLayoutParams(layoutParam);
    if (position % 2!=0) {
        ivHourly.setBackgroundColor(Color.parseColor("#CBCBCB"));
    } else {
        ivHourly.setBackgroundColor(Color.parseColor("#ffffff"));
    }
     return ivHourly;
 }

}
