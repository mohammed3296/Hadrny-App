package com.work.unknown.absence.Admin;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.Admin.model.Hole;
import com.work.unknown.absence.Login;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Student.GPSTracker;
import com.work.unknown.absence.Student.sesstionDetails;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class RegisterHolesActivity extends AppCompatActivity {
    private static final int REQUEST_INTERNET = 200;
    EditText holeNameText, holeTypeText;
    String holeName, holeType;
    Button addHoleButton, location;
    AlertDialog dialog;
    Double longtud , latitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_holes);
        dialog = new SpotsDialog(this);

        holeNameText = findViewById(R.id.hole_name_txt);
        holeTypeText = findViewById(R.id.hole_type_txt);

        holeName = holeNameText.getText().toString();
        holeType = holeTypeText.getText().toString();

        location = findViewById(R.id.getLocation_btn);




        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(RegisterHolesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RegisterHolesActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_INTERNET);
                } else {
                    GPSTracker gps = new GPSTracker(RegisterHolesActivity.this);
                    if (gps.canGetLocation()) {
                        latitud = gps.getLatitude();
                        longtud = gps.getLongitude();
                        // Double f = distance(seLatitude, selongtitude, seLatitude, longtitude);

                    }
                }
            }
        });


        addHoleButton = findViewById(R.id.add_hole_btn);
        addHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hole hole = new Hole(holeName,holeType,longtud,latitud);
                dialog.show();
                addHole(hole);


            }
        });
    }
    public void addHole(final Hole hole) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ADD_HOLE_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        if (response.contains("successfully")) {
                            alerter("Hole Added successfully", R.color.darkGreen);
                            dialog.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("dars", "" + error);
                alerter(error.getMessage(), R.color.darkRed);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("name", holeNameText.getText().toString());
                values.put("longitude", hole.getLogitude().toString());
                values.put("latitude", hole.getLatitude().toString());
                values.put("type", holeTypeText.getText().toString());
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterHolesActivity.this);
        requestQueue.add(stringRequest);
    }
    public void alerter(String v, int c)
    {
        Alerter.create(RegisterHolesActivity.this)
                .setTitle("error")
                .setText(v)
                .setBackgroundColorRes(c)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();
    }
}
