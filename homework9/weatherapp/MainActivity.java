package com.example.poojaandsaba.weatherapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.*;
import android.widget.TextView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {// implements MainActivity.AsyncResponse
    Spinner state;
    EditText street;
    EditText city;
    TextView errorText, netConnected;
    String geoURL="";
    Intent intent;
    RadioGroup radioGroup;
    RadioButton fah,cel;
    String degree;//="fahrenheit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = (Spinner) findViewById(R.id.state);
        street = (EditText) findViewById(R.id.txtStreet);
        city = (EditText) findViewById(R.id.txtCity);
        errorText = (TextView) findViewById(R.id.txtError);

        radioGroup = (RadioGroup) findViewById(R.id.degGrp);
        fah=(RadioButton)findViewById(R.id.fahrenheit);
        cel=(RadioButton)findViewById(R.id.celsius);


        netConnected = (TextView) findViewById(R.id.txtNetConnected);
        Button search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

           if (street.getText().toString().matches("")) {
               //errorList+="street address";
               errorText.setText("Please enter street address");

           } else if (city.getText().toString().matches("")) {
               errorText.setText("Please enter city");
               //Error.setText("Please enter city");
           } else if (state.getSelectedItem().toString().equals("Select")) {
               errorText.setText("Please select a state");
           } else {

               errorText.setText("");

               RadioButton selectedButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

               setDeg(selectedButton.getText().toString());

               if (isConnected()) {
                   netConnected.setBackgroundColor(0xFF00CC00);
                   netConnected.setText("You are connected");
               } else {
                   netConnected.setText("You are NOT conncted");
               }

               String degUrl;
                    geoURL = "http://sacha-env.elasticbeanstalk.com/?txtStreet=" + street.getText().toString().replace(" ","+") + "&txtCity=" + city.getText().toString().replace(" ","+") + "&state=" + state.getSelectedItem().toString() + "&radDeg="+degree.toLowerCase();
                   // Toast.makeText(MainActivity.this,geoURL,Toast.LENGTH_SHORT).show();
                    intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("city", city.getText().toString());
                    intent.putExtra("state", state.getSelectedItem().toString());
                    //intent.putExtra("degree",radD)
                    intent.putExtra("street", street.getText().toString());

                    new reqLatLng(getApplicationContext(), intent).execute(geoURL.replace(" ", "%20"));
                    intent.putExtra("degree",degree);

                }

            }
        });


    }

    /**
     * Called when the user clicks the About button
     */
    public void aboutStudent(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void clearFunction(View view){
        street.setText("");
        city.setText("");
        state.setSelection(0);
        fah.setChecked(true);
        cel.setChecked(false);

    }

    public void setDeg(String degree){
        this.degree=degree;
    }
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    public void goToForecast(View view){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://forecast.io"));
        startActivity(intent);
    }

    private class reqLatLng extends AsyncTask<String, Void, Void> {

        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        String data="";
        TextView serverDataReceived=(TextView) findViewById(R.id.serviceDataReceived);
        TextView showParsedJSON = (TextView)findViewById(R.id.showParsedJSON);
        Context context;
        Intent intent;

        public reqLatLng(Context context,Intent intent){
            this.context=context;
            this.intent=intent;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog.setTitle("Please wait...");
                    progressDialog.show();
            try {
                data+="&"+ URLEncoder.encode("data","UTF-8")+"="+geoURL;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            BufferedReader br=null;

            URL url= null;
            try {
                url = new URL(params[0]);


                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter outputStreamWr = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWr.write(data);

                outputStreamWr.flush();

                br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line=null;

                while ((line=br.readLine())!=null){
                    sb.append(line);
                    sb.append(System.getProperty("line.seperator"));

                }

                content = sb.toString();

            } catch (MalformedURLException e) {
                error= e.getMessage();

                e.printStackTrace();
            } catch (IOException e) {
                error=e.getMessage();
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            if(error!=null){
                serverDataReceived.setText("Error" + error);

            }else {
                serverDataReceived.setText(content);
                JSONObject jsonResponse;
                try {
                    jsonResponse=new JSONObject(content);
                    intent.putExtra("JsonObject", jsonResponse.toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}