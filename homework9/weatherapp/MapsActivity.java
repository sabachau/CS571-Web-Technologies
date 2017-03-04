package com.example.poojaandsaba.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.json.JSONObject;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import com.hamweather.aeris.communication.AerisEngine;

public class MapsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_main);
        double latitude=0.0;
        double longitude=0.0;
        AerisEngine.initWithKeys("TSl32xoqLNNxWXLR7171J", "oSb3zHpx3AmxbaR2qPSvUAbAb6R1YR8x1I0rdNo0", "com.example.poojaandsaba.weatherapp");
        Intent iamintent = getIntent();
        String jsondata = iamintent.getStringExtra("JsonObject");
        JSONObject jsonObj;
        try {
            jsonObj=new JSONObject(jsondata);
             latitude=jsonObj.getDouble("latitude");
             longitude=jsonObj.getDouble("longitude");

        } catch(Exception e)
        {
            e.printStackTrace();
        }

        MapFragments fragment = new MapFragments();
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lng", longitude);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}