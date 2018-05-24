package com.work.unknown.absence.Admin;

import android.annotation.SuppressLint;
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
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.Admin.model.Student;
import com.work.unknown.absence.R;


import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class StudentUpdateActivity extends AppCompatActivity {

    Student student;
    EditText emailText, passwordText;
    Button update;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update);

        dialog = new SpotsDialog(this);

        student = getIntent().getParcelableExtra("student");
        emailText = findViewById(R.id.student_email_txt);
        passwordText = findViewById(R.id.student_password_txt);

        emailText.setText(student.getSeatingNumber().toString());
        passwordText.setText(student.getNationalId());


        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                updateStudent(student.getId(), emailText.getText().toString(), passwordText.getText().toString());

            }
        });


    }


    public void updateStudent(String id, final String email, String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.UPDATE_STUDENTS_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "response " + response.toString(), Toast.LENGTH_LONG).show();
                if (response.contains("successfully")) {
                    Toast.makeText(getApplicationContext(), "the response " + response.toString(), Toast.LENGTH_LONG).show();
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
//                alerter(error.getMessage(), R.color.darkRed);
//                               Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("id", student.getId().toString());
//                values.put("name" , student.getName()) ;
//                values.put("address" , student.getAddress()) ;
//                values.put("department" , student.getDeptartement()) ;
//                values.put("level"  , student.getLevel()) ;
                values.put("seating_number", emailText.getText().toString());
                values.put("national_id", passwordText.getText().toString());
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
