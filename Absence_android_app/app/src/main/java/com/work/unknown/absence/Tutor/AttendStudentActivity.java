package com.work.unknown.absence.Tutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.work.unknown.absence.Models.SessionModel;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendStudentActivity extends AppCompatActivity {
    private TextView nameView, nationalIdView, levelView, depView, seatingNumberValue;
    private Spinner sessions;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_student);
        Bundle data = getIntent().getExtras();
        String studentId = data.getString("STUDENT_ID");
        final String device_id = data.getString("DEVICE_ID");
        String studentName = data.getString("STUDENT_NAME");
        String nationalId = data.getString("NATIONAL_ID");
        final String seatingId = data.getString("SEATING_ID");
        String level = data.getString("LEVEL");
        String department = data.getString("DEPARTMENT");
        nameView = findViewById(R.id.name_value);
        nationalIdView = findViewById(R.id.national_id_value);
        levelView = findViewById(R.id.level_value);
        depView = findViewById(R.id.department_value);
        seatingNumberValue = findViewById(R.id.seating_number_value);
        sessions = findViewById(R.id.sessions_spinner);
        button = findViewById(R.id.attend_button);
        nameView.setText(studentName);
        nationalIdView.setText(nationalId);
        seatingNumberValue.setText(seatingId);
        levelView.setText(level);
        depView.setText(department);
        final List<SessionModel> arrayOfSessions = new ArrayList<>();
        final List<String> arraySpinner = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
        ///////////////////////////
        RequestQueue queue1 = Volley.newRequestQueue(AttendStudentActivity.this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://192.168.43.21/attendance_app/public/attendance/tutor/get_sessions_now/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String sessionName = jsonObject1.getString("name");
                                String sessionId = jsonObject1.getString("id");
                                arrayOfSessions.add(new SessionModel(sessionId, sessionName, "", "", "", "", ""));
                            }

                            if (arrayOfSessions.size() > 0) {
                                for (int i = 0; i < arrayOfSessions.size(); i++) {
                                    arraySpinner.add(arrayOfSessions.get(i).getName());
                                }
                            }
                            adapter.addAll(arraySpinner);
                            sessions.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Alerter.create(AttendStudentActivity.this)
                                    .setTitle("Add session")
                                    .setText(response)
                                    .setDuration(10000)
                                    .setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Alerter.hide();
                                        }
                                    })
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Toast.makeText(AttendStudentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue1.add(stringRequest1);
///////////////////////////////////////////////////
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue1 = Volley.newRequestQueue(AttendStudentActivity.this);
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "http://192.168.43.21/attendance_app/public/attendance/tutor/attend_student",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Alerter.create(AttendStudentActivity.this)
                                        .setTitle("Success attend student")
                                        .setText(response)
                                        .setDuration(10000)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Alerter.hide();
                                            }
                                        })
                                        .show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        Toast.makeText(AttendStudentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("device_id", device_id);
                        String session_id = null;
                        for (int i = 0; i < arrayOfSessions.size(); i++) {
                            if (sessions.getSelectedItem().toString() == arrayOfSessions.get(i).getName()) {
                                session_id = arrayOfSessions.get(i).getId();
                                Log.e("?>?>?>?>?" , session_id+"");
                            }
                        }
                        params.put("session_id", session_id);
                        params.put("seating_number", seatingId);
                        return params;
                    }
                };
                queue1.add(stringRequest1);

            }
        });
    }
}
