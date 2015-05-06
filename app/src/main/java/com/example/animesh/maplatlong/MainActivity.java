package com.example.animesh.maplatlong;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity
{
    private static final String TAG_RESULTS="results";
    EditText editText;static String ab;
    TextView lat, lon;
    static String val;static String url;static String a,b;
    JSONArray res = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.addval);
        lat = (TextView) findViewById(R.id.lattitude);
        lon = (TextView) findViewById(R.id.longitude);


    }
    public void retriveval(View view)
    {
        val=editText.getText().toString();
        ab=val;

        val=val.replaceAll(" ","%20");
        url="http://maps.googleapis.com/maps/api/geocode/json?address="+val+"&sensor=true";
        new retrive().execute();

    }

    class retrive extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {

            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            //"http://maps.googleapis.com/maps/api/geocode/json?address=Hsrlayout,bangalore&sensor=true"
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.d("ani "," " + jsonStr);

                    // Getting JSON Array node
                    res = jsonObj.getJSONArray("results");
                    for (int i = 0; i < res.length(); i++)
                    {
                        JSONObject c = res.getJSONObject(i);
                        JSONObject d=c.getJSONObject("geometry");
                        // JSONObject e=d.getJSONObject("viewport");
                        // JSONObject north = e.getJSONObject("northeast");
                        JSONObject north=d.getJSONObject("location");
                        String lati = north.getString("lat");
                        String longi = north.getString("lng");
                        //a=Double.parseDouble(lati.toString());
                        //b=Double.parseDouble(longi.toString());
                        //b=longi;
                        a=lati.toString();
                        b=longi.toString();
                        Log.d("ani",a+","+b);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lat.setText(a+"");
            lon.setText(b+"");
            Intent intent=new Intent(MainActivity.this,MapsActivity.class);
            intent.putExtra("lat",a);
            intent.putExtra("lng",b);
            intent.putExtra("marker",ab);
            startActivity(intent);
        }
    }

}
