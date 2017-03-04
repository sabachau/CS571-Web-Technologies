package com.example.poojaandsaba.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ResultActivity extends Activity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    Intent savedVal,mapVal;
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        ImageView shareButton = (ImageView)findViewById(R.id.btnFbShare);
        ImageView weatherImg=(ImageView)findViewById(R.id.weatherImg);
        TextView summary=(TextView)findViewById(R.id.txtSummary);
        TextView tvTemp=(TextView)findViewById(R.id.temperature);
        TextView rise=(TextView)findViewById(R.id.val_sunrise);
        TextView set=(TextView)findViewById(R.id.val_sunset);
        TextView tvPrec=(TextView)findViewById(R.id.precVal);
        TextView tvProb=(TextView)findViewById(R.id.probVal);
        TextView tvHumidity=(TextView)findViewById(R.id.humidityVal);
        TextView tvWindspeed=(TextView)findViewById(R.id.windspeedVal);
        TextView tvDewpoint=(TextView)findViewById(R.id.dewpointVal);
        TextView tvVisibility=(TextView)findViewById(R.id.visibilityVal);

        Intent intent=getIntent();
        TextView tvHL=(TextView)findViewById(R.id.txtHL);
        final String jsonStr=intent.getStringExtra("JsonObject");
        final String city=intent.getStringExtra("city");
        final String state=intent.getStringExtra("state");
        final String degree=intent.getStringExtra("degree");


        savedVal=new Intent(getApplicationContext(),DetailsActivity.class);
        savedVal.putExtra("city",city);
        savedVal.putExtra("state",state);
        savedVal.putExtra("JsonObject",jsonStr);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mapVal=new Intent(getApplicationContext(),MapsActivity.class);
        mapVal.putExtra("JsonObject",jsonStr);

        FacebookSdk.sdkInitialize(getApplicationContext());

        final String fOrC;

        final String units;
        String windUnit;
        String viUnit;
        String prUnit;
        if(degree.equals("Fahrenheit")){
            fOrC="F";
            units="°F";
            windUnit="mph";
            viUnit="mi";
            prUnit="mb";
        }
        else
        {
            fOrC="C";
            units="°C";
            windUnit="m/s";
            viUnit="km";
            prUnit="hPa";
        }
        savedVal.putExtra("degree",fOrC);

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(ResultActivity.this);


            JSONObject currently=jsonObj.optJSONObject("currently");
            final String offset=jsonObj.getString("offset");
            final String icon=currently.getString("icon");
            final float prec=Float.parseFloat(currently.getString("precipIntensity"));
            final int prob=Math.round(Float.parseFloat(currently.getString("precipProbability")))*100;
            final float wind=Float.parseFloat(currently.getString("windSpeed"));
            final int dew=Math.round(Float.parseFloat(currently.getString("dewPoint")));
            final float humidity=Float.parseFloat(currently.getString("humidity"))*100;
            final float visi=Float.parseFloat(currently.getString("visibility"));
            final String lat=jsonObj.getString("latitude");
            final String lng=jsonObj.getString("longitude");

            //Toast.makeText(getApplicationContext(),"Latitude,Longitude",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),lat+","+lng,Toast.LENGTH_SHORT).show();

            mapVal.putExtra("lat", lat);
            mapVal.putExtra("lng",lng);

            String pdisp;

            if(prec>=0 && prec<0.002)
            {
                pdisp= "None";
            }
            else if(prec>=0.002 && prec<0.017)
            {
                pdisp= "Very Light";
            }
            else if(prec>=0.017 &&prec<0.1){
                pdisp= "Light";
            }
            else if(prec>=0.1 &&prec<0.4)
            {
                pdisp= "Moderate";
            }
            else if(prec>=0.4)
            {
                pdisp= "Heavy";
            }
            else{
                pdisp="NA";
            }
            tvPrec.setText(pdisp);
            tvDewpoint.setText(Float.toString(dew)+" "+units);
            tvHumidity.setText(Float.toString(humidity)+" %");
            tvProb.setText(Integer.toString(prob)+" %");
            tvWindspeed.setText(Float.toString(wind)+" "+windUnit);

            final int temp=Math.round(Float.parseFloat(currently.getString("temperature")));
            final String jsonsummary=currently.getString("summary");
            final String timezone=jsonObj.getString("timezone");
            JSONObject daily=jsonObj.optJSONObject("daily");
            JSONArray data=daily.optJSONArray("data");
            final int tLow=Math.round(Float.parseFloat(data.optJSONObject(0).getString("temperatureMin")));
            final int tHigh=Math.round(Float.parseFloat(data.optJSONObject(0).getString("temperatureMax")));

            SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
            df.setTimeZone(TimeZone.getTimeZone(timezone));
            Long time1=Long.parseLong(data.optJSONObject(0).getString("sunsetTime"));
            Date d= new Date(time1*1000L);
            final String sunset=df.format(d);
            Long time2=Long.parseLong(data.optJSONObject(0).getString("sunriseTime"));
             d= new Date(time2*1000L);
            final String sunrise=df.format(d);

            rise.setText(sunrise);
            set.setText(sunset);
            tvVisibility.setText(Float.toString(visi)+" "+viUnit);

            tvHL.setText("L:" + tLow + "\u00B0 | H:" + tHigh + "\u00B0");

             String tempImgURL="http://cs-server.usc.edu:45678/hw/hw8/images/";

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
            tempImgURL+=holder;
            tempImgURL+=".png";

            int resID = getResources().getIdentifier(holder, "drawable", getPackageName());

            weatherImg.setImageResource(resID);
            String sum=jsonsummary+" in "+city+", "+state;
            summary.setText(sum);
            tvTemp.setText(Html.fromHtml(temp+"<small><sup>"+units+"</sup></small>"));


            final String imgURL=tempImgURL;
           // Toast.makeText(getApplicationContext(), icon, Toast.LENGTH_SHORT).show();
          //  Toast.makeText(getApplicationContext(), imgURL, Toast.LENGTH_SHORT).show();


           //final URI imgURL=new URI(tempImgURL);

//            ShareLinkContent content = new ShareLinkContent.Builder()
//                    .setContentUrl(Uri.parse("https://developers.facebook.com"))
//                    .build();
//            shareButton.setShareContent(content);
//            Toast.makeText(getApplicationContext(), Float.toString(temp), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), state, Toast.LENGTH_SHORT).show();
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                @Override
                public void onCancel(){
                    Toast.makeText(ResultActivity.this,"Facebook post cancelled",Toast.LENGTH_SHORT).show();
                    Log.d("MESSAGE","shared cancel");
                }
                @Override
                public void onSuccess(Sharer.Result result){
                    Toast.makeText(ResultActivity.this,"Facebook post successful",Toast.LENGTH_SHORT).show();

                    Log.d("MESSAGE", "SHARED SUCCESSFULLY");
                }



                @Override
                public void onError(FacebookException exception){
                    Toast.makeText(ResultActivity.this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("MESSAGE", "Share: " + exception.getMessage());
                    exception.printStackTrace();
                }


            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Current Weather in " + city + "," + state)
                                .setContentDescription(jsonsummary+", "+temp+" \u00B0"+fOrC)
                                        .setContentUrl(Uri.parse("http://www.forecast.io")).
                                        setImageUrl(Uri.parse(imgURL))
                                .build();

                        shareDialog.show(linkContent);
                    }//
                }

            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void moreDetails(View view){
        Intent intent=savedVal;

        startActivity(intent);
    }
    public void openMap(View view){
        Intent intent=mapVal;
        startActivity(intent);
    }

}
