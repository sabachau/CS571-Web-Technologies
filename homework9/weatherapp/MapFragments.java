package com.example.poojaandsaba.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class MapFragments extends MapViewFragment implements  OnAerisMapLongClickListener,  AerisCallback {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_frag, container, false);
        mapView = (AerisMapView) view.findViewById(R.id.aerisfrag_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);
        Bundle bundle = getArguments();
        Double latitude = bundle.getDouble("lat");
        Double longitude = bundle.getDouble("lng");
        Log.d("Attention","Attention!");
        Log.d("Lat",Double.toString(latitude));
        Log.d("Lng",Double.toString(longitude));
        mapView.moveToLocation(new LatLng(latitude,longitude), 9);
        mapView.addLayer(AerisTile.RADSAT);
        mapView.setOnAerisMapLongClickListener(this);
        return view;
    }

    @Override
    public void onMapLongClick(double lat, double longitude) {
    }

    @Override
    public void onResult(EndpointType type, AerisResponse response) {

    }
}