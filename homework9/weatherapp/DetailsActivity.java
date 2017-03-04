package com.example.poojaandsaba.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DetailsActivity extends AppCompatActivity {
    String jObjectStr, city, state,unit,timezone;
    JSONObject jsonObj;
    TextView detailH,col3;
    Intent passToSev;
    ListView lView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        changeButtons();

        Intent intent=getIntent();
        detailH=(TextView)findViewById(R.id.headDetail);
        col3=(TextView)findViewById(R.id.txtDegUnit);
        passToSev=new Intent(getApplicationContext(),DetailsActivityNext.class);
        detailH.setText("More Details for "+city+","+state);
        col3.setText("Temp(°"+unit+")");

        city=intent.getStringExtra("city");
        state=intent.getStringExtra("state");
        unit=intent.getStringExtra("degree");


        try {
            jObjectStr=intent.getStringExtra("JsonObject");


            jsonObj = new JSONObject(jObjectStr);
            JSONObject hourly = jsonObj.getJSONObject("hourly");
            JSONArray data=hourly.getJSONArray("data");
            List<CandyForHour> candyList = new ArrayList<>();

            timezone=jsonObj.getString("timezone");

            lView = (ListView) findViewById(R.id.pack24);
            ArrayAdapter adapForHour = new AdapForHour(this, R.layout.smallcandy, candyList);
            lView.setAdapter(adapForHour);

            //local timezone
            for (int i = 0; i < 48; i++ ) {

                SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
                df.setTimeZone(TimeZone.getTimeZone(timezone));
                Long time1=Long.parseLong(data.getJSONObject(i).getString("time"));
                Date d= new Date(time1*1000L);
                final String time=df.format(d);
                candyList.add(new CandyForHour(data.getJSONObject(i).getString("temperature"), data.getJSONObject(i).getString("icon"), time));
            }


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }
    public void generateNextSev(View view){

    }
    private void changeButtons() {
         Button btnHour = (Button) findViewById(R.id.btnDay);
         Button btnWeek = (Button) findViewById(R.id.btnWeek);

        btnHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DetailsActivityNext.class).putExtra("state", state)
                        .putExtra("city", city)
                        .putExtra("JsonObject", jObjectStr)
                        .putExtra("degree", unit)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }


}


