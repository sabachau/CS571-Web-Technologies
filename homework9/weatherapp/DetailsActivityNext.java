package com.example.poojaandsaba.weatherapp;

/**
 * Created by poojaandsaba on 10/12/15.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivityNext extends AppCompatActivity {
    String thisisJsonStr, state, city,degree;
    JSONObject oJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_next);
        TextView date[]=new TextView[7];
        TextView temp[]=new TextView[7];
        ImageView im[]=new ImageView[7];

         date[0] = (TextView) findViewById(R.id.date1);
         date[1] = (TextView) findViewById(R.id.date2);
         date[2] = (TextView) findViewById(R.id.date3);
         date[3] = (TextView) findViewById(R.id.date4);
         date[4] = (TextView) findViewById(R.id.date5);
         date[5] = (TextView) findViewById(R.id.date6);
         date[6] = (TextView) findViewById(R.id.date7);

         temp[0] = (TextView) findViewById(R.id.temp1);
         temp[1] = (TextView) findViewById(R.id.txtbelow);
         temp[2] = (TextView) findViewById(R.id.temp3);
         temp[3] = (TextView) findViewById(R.id.temp4);
         temp[4] = (TextView) findViewById(R.id.temp5);
         temp[5] = (TextView) findViewById(R.id.temp6);
         temp[6] = (TextView) findViewById(R.id.temp7);

         im[0] = (ImageView) findViewById(R.id.icon1);
         im[1] = (ImageView) findViewById(R.id.icon2);
         im[2] = (ImageView) findViewById(R.id.icon3);
         im[3] = (ImageView) findViewById(R.id.icon4);
         im[4] = (ImageView) findViewById(R.id.icon5);
         im[5] = (ImageView) findViewById(R.id.icon6);
         im[6] = (ImageView) findViewById(R.id.icon7);

         Intent intent = getIntent();
         thisisJsonStr = intent.getStringExtra("JsonObject");
         city = intent.getStringExtra("city");
         state = intent.getStringExtra("state");
         degree=intent.getStringExtra("degree");
         TextView hfg=(TextView)findViewById(R.id.nextSHead);
         hfg.setText("More Details for "+city+","+state);
         try {
            JSONObject objectOfJson = new JSONObject(thisisJsonStr);
            JSONArray daily = objectOfJson.getJSONArray("daily");
            for (int i = 6; i >= 0; i--) {
                    date[i].setText(daily.getJSONObject(i).getString("timezone") +
                            ", " +
                            daily.getJSONObject(i).getString("time"));
                    temp[i].setText("Min: "+
                            daily.getJSONObject(i).getString("temperatureMin")+
                            degree+" | Max: "+
                            " " +
                            " "+
                            daily.getJSONObject(i).getString("temperatureMax")+degree);
                    setIcon(im[i], daily.getJSONObject(i).getString("icon"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void goBack(View view){
        Intent i = new Intent(getApplicationContext(),DetailsActivity.class);
        i=new Intent(getApplicationContext(),DetailsActivity.class);
        i.putExtra("city",city);
        i.putExtra("state",state);
        i.putExtra("degree",degree);
        i.putExtra("JsonObject",thisisJsonStr);
        startActivity(i);
    }
    public void setIcon(ImageView weatherImg, String icon) {
        String holder;
        if(icon.equals("clear-day"))
            holder="clear";
        else if (icon.equals("clear-night"))
            holder="clear_night";
        else if (icon.equals("partly-cloudy-day"))
            holder="cloud_day";
        else if(icon.equals("partly-cloudy-night"))
            holder="cloud_night";
        else
            holder=icon;
        int resID = getResources().getIdentifier(holder, "drawable", getPackageName());
        weatherImg.setImageResource(resID);
    }


}
