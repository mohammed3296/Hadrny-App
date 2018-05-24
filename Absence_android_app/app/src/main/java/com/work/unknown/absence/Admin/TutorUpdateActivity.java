package com.work.unknown.absence.Admin;


import android.app.AlertDialog;
import android.content.Intent;
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
import com.work.unknown.absence.Admin.model.Tutor;
import com.work.unknown.absence.Login;
import com.work.unknown.absence.R;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class TutorUpdateActivity extends AppCompatActivity {

    EditText tutorEmailText, tutorNameText;
    Button update;
    Tutor tutor;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_update);

        tutorEmailText = findViewById(R.id.tutor_email_txt);
        tutorNameText = findViewById(R.id.tutor_name_txt);
        update = findViewById(R.id.update) ;
        dialog = new SpotsDialog(this);
        tutor = getIntent().getParcelableExtra("tutor");

        tutorEmailText.setText(tutor.getEmail().toString());
        tutorNameText.setText(tutor.getName().toString());
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                updateTutor(tutor.getId().toString() , tutorNameText.toString() , tutorEmailText.toString()) ;
            }
        });
    }
    public void updateTutor(String id , final String name , String email ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.UPDATE_TUTORS_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("successfully")) {
                    dialog.dismiss();
                    Intent intent = new Intent(getBaseContext(), RegisterTutorsActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("dars", "" + error);
                alerter(error.getMessage(), R.color.darkRed);
                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("id", tutor.getId().toString());
                values.put("name", tutorNameText.getText().toString());
                values.put("email", tutorEmailText.getText().toString());
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void alerter(String v, int c)
    {
        Alerter.create(TutorUpdateActivity.this)
                .setTitle("error")
                .setText(v)
                .setBackgroundColorRes(c)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();

    }
}
