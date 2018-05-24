package com.work.unknown.absence.Student;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Login;
import com.work.unknown.absence.R;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class sesstionDetails extends AppCompatActivity {
    TextView sesstionname, sesstionstartTime, sesstionendTime, AllowedTime, holeplace;
    String name, starttime, endtime, allowedtime, place;
    Double latitude, longtitude;
    Double seLatitude, selongtitude;
    String id;
    private static final int REQUEST_INTERNET = 200;
    String  Request_url="http://192.168.43.21/attendance_app/public/attendance/student/attend_session";
    SpotsDialog dialog;
    String deviceid,Email;
    SharedPreferences sharedPreference;
    public static final String MyPREFERENCES = "logdata" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesstion_details);
        sharedPreference=getApplication().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        deviceid=sharedPreference.getString("deviceID","null");
        Email=sharedPreference.getString("Email","null");
        dialog=new SpotsDialog(this);
        sesstionname = findViewById(R.id.hole_name);
        sesstionstartTime = findViewById(R.id.sess_time);
        sesstionendTime = findViewById(R.id.sesstion_endtime);
        AllowedTime = findViewById(R.id.allowed_time);
        holeplace = findViewById(R.id.sesstion_place);
        name = getIntent().getExtras().getString("sename");
        starttime = getIntent().getExtras().getString("start_time");
        endtime = getIntent().getExtras().getString("end_time");
        allowedtime = getIntent().getExtras().getString("allowdtime");
        place = getIntent().getExtras().getString("place");
        latitude = getIntent().getDoubleExtra("Lat", 0.0);
        longtitude = getIntent().getDoubleExtra("Long", 0.0);
        id=getIntent().getStringExtra("sesstion_id");
        sesstionname.setText(name);
        sesstionstartTime.setText(starttime);
        sesstionendTime.setText(endtime);
        AllowedTime.setText(allowedtime);
        AllowedTime.append("M");
        holeplace.setText(place);
    }

    public void getCurrentuserLocation() {
        if (ContextCompat.checkSelfPermission(sesstionDetails.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(sesstionDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_INTERNET);
        } else {
            GPSTracker gps = new GPSTracker(this);
            if (gps.canGetLocation()) {
                seLatitude = gps.getLatitude();
                selongtitude = gps.getLongitude();
                Double f = distance(seLatitude, selongtitude, seLatitude, longtitude);
                 if(f<100)
                 {
                     Attend_process();
                 }
            } else {
                gps.showSettingsAlert();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_INTERNET) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                GPSTracker gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    seLatitude = gps.getLatitude();
                    selongtitude = gps.getLongitude();
                    Toast.makeText(this, seLatitude + "::::" + selongtitude, Toast.LENGTH_SHORT).show();
                }
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(sesstionDetails.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "Please agree permission:(", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(sesstionDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_INTERNET);
                } else {
                    Toast.makeText(this, "Exit :(", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }
    }

    public void attend(View view) {
        getCurrentuserLocation();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public void Attend_process()
    {
                dialog.show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Request_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.i("res", response);
                if (response.contains("successfully")) {
                    alerter("you have been Attended sucessfully");
                }else
                    {
                        Alerter.create(sesstionDetails.this)
                                .setTitle("Not allowed")
                                .setText("you are not allowed to register in this sesstion")
                                .setBackgroundColorRes(R.color.darkGreen)
                                .setDuration(3000)
                                .setIcon(R.drawable.alarm)
                                .show();

                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("dars", ""+error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>values=new HashMap<String,String>();
                values.put("device_id",deviceid);
                values.put("session_id",id);
                values.put("seating_number",Email);

                return values;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    public void alerter(String v)
    {
        Alerter.create(sesstionDetails.this)
                .setTitle("sucessfully")
                .setText(v)
                .setBackgroundColorRes(R.color.darkGreen)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();
    }
}
