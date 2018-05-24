package com.work.unknown.absence.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.R;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class RegisterStudentsActivity extends AppCompatActivity {
    Button importFromExcelButton;
    private static final int PICKFILE_RESULT_CODE = 1;
    AlertDialog dialog;
    String link;
    Button editStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_students);
        dialog = new SpotsDialog(this);
        importFromExcelButton = findViewById(R.id.import_data_from_excel_btn);
        importFromExcelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting path
                Intent mediaIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mediaIntent.setType("excel/*"); // Set MIME type as per requirement
                startActivityForResult(mediaIntent, PICKFILE_RESULT_CODE);

                dialog.show();
            }
        });

        editStudentButton = findViewById(R.id.edit_students_data);
        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StudentListActivity.class);
                startActivity(intent);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri videoUri = data.getData();
            Log.d("", "Video URI= " + videoUri);
            link = videoUri.toString();
            Toast.makeText(getApplicationContext(), "file path " +
                    videoUri.toString(), Toast.LENGTH_LONG).show();

            sendLink();

        }
    }


    public void sendLink() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.ADD_STUDENTS_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),
                                response.toString(), Toast.LENGTH_LONG).show();
                        if (response.contains("successfully")) {

                            Toast.makeText(getApplicationContext(), "response " + response, Toast.LENGTH_LONG).show();

                            dialog.dismiss();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("dars", "" + error);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//                alerter(error.getMessage(), R.color.darkRed);
                //               Toast.makeText(SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> values = new HashMap<String, String>();
                values.put("link", link);
                return values;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
