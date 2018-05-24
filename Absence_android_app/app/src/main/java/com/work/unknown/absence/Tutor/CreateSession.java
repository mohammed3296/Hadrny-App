package com.work.unknown.absence.Tutor;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Models.Hole;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class CreateSession extends AppCompatActivity {

    @BindView(R.id.button_date)
    Button button_date;
    @BindView(R.id.button_time)
    Button button_time;
    @BindView(R.id.button_time_end)
    Button button_time_end;
    @BindView(R.id.submit_button_session)
    Button submit_button_session;
    @BindView(R.id.session_name)
    EditText session_name;
    @BindView(R.id.avalible_time)
    EditText avalible_time;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Spinner holes;
    String name;
    String time;
    String startTime;
    String endTime;
    String date;
    String hole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);
        ButterKnife.bind(this);
        final List<Hole> arrayOfHoles = new ArrayList<>();
        final List<String> arraySpinner = new ArrayList<>();
        holes = (Spinner) findViewById(R.id.holes_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinner);
        RequestQueue queue1 = Volley.newRequestQueue(CreateSession.this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://192.168.43.21/attendance_app/public/attendance/tutor/get_halls",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String holeName = jsonObject1.getString("name");
                                arrayOfHoles.add(new Hole(holeName));
                            }
                            if (arrayOfHoles.size() > 0) {
                                for (int i = 0; i < arrayOfHoles.size(); i++) {
                                    arraySpinner.add(arrayOfHoles.get(i).getHoleName());
                                }
                            }
                            adapter.addAll(arraySpinner);
                            holes.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Alerter.create(CreateSession.this)
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
                Toast.makeText(CreateSession.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue1.add(stringRequest1);

        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateSession.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date1 = day + "/" + month + "/" + year;
                button_date.setText(date1);
                date = date1;
            }
        };


        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSession.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        button_time.setText(selectedHour + ":" + selectedMinute);
                        startTime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        button_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateSession.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        button_time_end.setText(selectedHour + ":" + selectedMinute);
                        endTime = selectedHour + ":" + selectedMinute;
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        submit_button_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSession();
            }
        });
    }

    public void createSession() {

        if (!validate()) {
            Toast.makeText(CreateSession.this, "Failed", Toast.LENGTH_SHORT).show();
            return;
        }
        final AlertDialog dialog = new SpotsDialog(CreateSession.this);
        dialog.setTitle("Creating Session...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(CreateSession.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.21/attendance_app/public/attendance/tutor/add_session",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("<><><><>", response);
                        Alerter.create(CreateSession.this)
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
                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                dialog.dismiss();
                Toast.makeText(CreateSession.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("registeration_time", time);
                params.put("hall_name", hole);
                params.put("end_time", endTime);
                params.put("start_time", startTime);
                params.put("name", name);
                params.put("date", date);
                params.put("tutor_id", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public boolean validate() {
        boolean valid = true;
        name = session_name.getText().toString().trim();
        time = avalible_time.getText().toString().trim();
        hole = holes.getSelectedItem().toString();
        if (name.isEmpty() || name.length() < 2) {
            session_name.setError("At least 2 characters");
            valid = false;
        } else {
            session_name.setError(null);
        }

        if (time.isEmpty() || Double.parseDouble(time) == 0) {
            avalible_time.setError("Enter a valid time");
            valid = false;
        } else {
            avalible_time.setError(null);
        }

        if (hole == null) {
            Toast.makeText(this, "Please chose a hole", Toast.LENGTH_SHORT).show();
        }

        if (startTime == null) {
            valid = false;
            Toast.makeText(this, "Please chose a Start time", Toast.LENGTH_SHORT).show();
        }

        if (endTime == null) {
            valid = false;
            Toast.makeText(this, "Please chose a End time", Toast.LENGTH_SHORT).show();
        }

        if (date == null) {
            valid = false;
            Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();
        }
        return valid;
    }
}
