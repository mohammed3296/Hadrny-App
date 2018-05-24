package com.work.unknown.absence.Admin;
import android.content.Intent;
import android.app.AlertDialog;
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
import com.work.unknown.absence.R;
import java.util.HashMap;
import java.util.Map;
import dmax.dialog.SpotsDialog;
public class TutorDetailActivity extends AppCompatActivity {

    Button addTutorButton;
    EditText tutorName, tutorEmail, tutorPassword, tutorRepassword, tutorDeparement;
    String name, email, password, repeatPassword, department;
    AlertDialog dialog;
    Tutor tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);

        tutorName = findViewById(R.id.tutor_name_txt);
        tutorEmail = findViewById(R.id.tutor_email_txt);
        tutorPassword = findViewById(R.id.tutor_password_txt);
        tutorRepassword = findViewById(R.id.tutor_repassword_txt);

        dialog = new SpotsDialog(this);
        name = tutorName.getText().toString();
        email = tutorEmail.getText().toString();
        password = tutorPassword.getText().toString();
        repeatPassword = tutorRepassword.getText().toString();

        addTutorButton = findViewById(R.id.add_tutor_btn);
        addTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (name == null || email == null || password == null || repeatPassword == null) {
                    Toast.makeText(getApplicationContext(), "fields cant be empty ", Toast.LENGTH_LONG).show();
                } else {
                    dialog.show();
                    tutor = new Tutor(name, email, password,"");
                    tutor.setName(name);
                    tutor.setEmail(email);
                    tutor.setPassword(password);
                    addTutor(tutor);
                }
            }
        });


    }

    public void addTutor(final Tutor tutor) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.ADD_TUTOR_LINK, new Response.Listener<String>() {
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

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("name", tutorName.getText().toString());
                values.put("email", tutorEmail.getText().toString());
                values.put("password", tutorPassword.getText().toString());
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void alerter(String v, int c)
    {
        Alerter.create(TutorDetailActivity.this)
                .setTitle("error")
                .setText(v)
                .setBackgroundColorRes(c)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();
    }
}
