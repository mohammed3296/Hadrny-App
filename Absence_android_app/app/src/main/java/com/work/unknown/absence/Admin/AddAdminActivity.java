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
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.Admin.model.Admin;
import com.work.unknown.absence.R;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class AddAdminActivity extends AppCompatActivity {
    EditText nameText , emailText , passwordText , rPasswordText ;
    Button add ;
    String name , email , password ,rPassword;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        nameText = findViewById(R.id.admin_name_txt) ;
        emailText = findViewById(R.id.admin_email_txt) ;
        passwordText = findViewById(R.id.admin_repassword_txt) ;
        rPasswordText = findViewById(R.id.admin_repeatPassword_txt) ;
        dialog = new SpotsDialog(this);
        name = nameText.getText().toString() ;
        email = emailText.getText().toString() ;
        password = passwordText.getText().toString()  ;
        rPassword = rPasswordText.getText().toString() ;

        add = findViewById(R.id.add_admin_btn) ;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                Admin admin = new Admin(name , email , password) ;

                addAdmin(admin) ;
            }
        });


    }

    public void addAdmin(final Admin admin)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.ADD_ADMIN_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("successfully")) {

                    Toast.makeText(getApplicationContext() , "the response " + response.toString() ,
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    Intent intent =new Intent(getBaseContext() , tasksactivity.class) ;
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("dars", "" + error);
//                alerter(error.getMessage(), R.color.darkRed);
                //               Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("name",nameText.getText().toString() );
                values.put("email", emailText.getText().toString());
                values.put("password", passwordText.getText().toString());
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
