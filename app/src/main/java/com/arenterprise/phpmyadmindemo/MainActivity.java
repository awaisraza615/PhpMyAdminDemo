package com.arenterprise.phpmyadmindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    String getid,getstatus;
    String server_url="http://192.168.10.5/api/weather/getinfo.php";
    String URL="http://192.168.10.5/api/led/getinfo.php";
    private TextView temp,hum;
    private ToggleButton toggleButton1, toggleButton2,toggleButton3, toggleButton4;
    private Button getdata;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton1=(ToggleButton)findViewById(R.id.toggleButton);
        toggleButton2=(ToggleButton)findViewById(R.id.toggleButton2);
        toggleButton3=(ToggleButton)findViewById(R.id.toggleButton3);
        toggleButton4=(ToggleButton)findViewById(R.id.toggleButton4);
        temp=(TextView)findViewById(R.id.texttemp);
        hum=(TextView)findViewById(R.id.texthum);
        getdata=(Button)findViewById(R.id.getdata);

        sendjsonrequest();
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendjsonrequest();
            }
        });




        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   // The toggle is enabled
                   getjsonrequest("on","1");
                   Toast.makeText(MainActivity.this, "on", Toast.LENGTH_SHORT).show();
               } else {
                   // The toggle is disabled
                   getjsonrequest("off","1");
                   Toast.makeText(MainActivity.this, "off", Toast.LENGTH_SHORT).show();
               }
           }
       });

       toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked) {
                   // The toggle is enabled
                   getjsonrequest("on","2");
                   Toast.makeText(MainActivity.this, "on", Toast.LENGTH_SHORT).show();
               } else {
                   // The toggle is disabled
                   getjsonrequest("off","2");
                   Toast.makeText(MainActivity.this, "off", Toast.LENGTH_SHORT).show();
               }
           }
       });
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    getjsonrequest("on","3");
                    Toast.makeText(MainActivity.this, "on", Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    getjsonrequest("off","3");
                    Toast.makeText(MainActivity.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        toggleButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    getjsonrequest("on","4");
                    Toast.makeText(MainActivity.this, "on", Toast.LENGTH_SHORT).show();
                } else {
                    // The toggle is disabled
                    getjsonrequest("off","4");
                    Toast.makeText(MainActivity.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });








    }
    public void getjsonrequest(String sts,String sid) {

    final String status,id;
            status=sts;
            id=sid;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                    error.printStackTrace();
                }

            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String>params= new HashMap<String,String>();
                   params.put("status",status);
                   params.put("id",id);
                    return params;
                }
            };
        RequestHandler.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }

    public void sendjsonrequest()
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    getid = response.getString("temp");
                    getstatus = response.getString("hum");


                    temp.setText(getid);
                    hum.setText(getstatus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    }



}
