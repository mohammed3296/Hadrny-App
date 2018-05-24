package com.work.unknown.absence.Tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Adapters.StudentAdapter;
import com.work.unknown.absence.Ip;
import com.work.unknown.absence.Models.StudentModel;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Tutor.AttendStudentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentSearchActivity extends AppCompatActivity {
    private Button search_button;
    private StudentAdapter mAdapter;
    List<StudentModel> list;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);
        final ListView studentListView = (ListView) findViewById(R.id.users_list);
        search_button = (Button) findViewById(R.id.search_button);
        editText = (EditText) findViewById(R.id.student_name);
        mAdapter = new StudentAdapter(StudentSearchActivity.this, new ArrayList<StudentModel>());

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = null ;
                list = new ArrayList<>();
                mAdapter.clear();
                RequestQueue queue = Volley.newRequestQueue(StudentSearchActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.21/attendance_app/public/attendance/tutor/regist_student",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                        String student_id = jsonObject1.getString("id");
                                        String student_name = jsonObject1.getString("name");
                                        String deviceId = jsonObject1.getString("device_id");
                                        String nationalId = jsonObject1.getString("national_id");
                                        String seating_number = jsonObject1.getString("seating_number");
                                        String level = jsonObject1.getString("level");
                                        String department = jsonObject1.getString("department");

                                        list.add(new StudentModel(student_id, student_name, deviceId, nationalId, seating_number, level, department));
                                    }
                                    if (list != null && !list.isEmpty()) {
                                        mAdapter.addAll(list);
                                    }
                                    studentListView.setAdapter(mAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Alerter.create(StudentSearchActivity.this)
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
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentSearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", editText.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Bundle bundle = new Bundle();

                bundle.putString("STUDENT_ID", list.get(position).getId());
                bundle.putString("DEVICE_ID", list.get(position).getDeviceId());
                bundle.putString("STUDENT_NAME", list.get(position).getName());
                bundle.putString("NATIONAL_ID", list.get(position).getNationalId());
                bundle.putString("SEATING_ID", list.get(position).getSeatingId());
                bundle.putString("LEVEL", list.get(position).getLevel());
                bundle.putString("DEPARTMENT", list.get(position).getDepartment());

                Intent trasverActivity = new Intent(StudentSearchActivity.this, AttendStudentActivity.class);
                trasverActivity.putExtras(bundle);
                startActivity(trasverActivity);

                RequestQueue queue = Volley.newRequestQueue(StudentSearchActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + Ip.ip + "/attendance_app/public/attendance/tutor/regist_student",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Alerter.create(StudentSearchActivity.this)
                                        .setTitle("Attend  student")
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
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(StudentSearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", list.get(position).getId());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
}
