package com.work.unknown.absence.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.work.unknown.absence.Adapters.studentsAdapter;
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.Admin.model.Student;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class StudentListActivity extends AppCompatActivity {
    RecyclerView studentRecycler;
    ArrayList<Student> studentsList;
    studentsAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getStudents();
        adapter = new studentsAdapter(this, studentsList);
        studentRecycler = findViewById(R.id.studentsRecycler);
        mLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        studentRecycler.setLayoutManager(mLayoutManager);
        studentRecycler.setItemAnimator(new DefaultItemAnimator());
        studentRecycler.setAdapter(adapter);

    }


    public void getStudents() {
        studentsList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Constant.GET_STUDENTS_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray Result = new JSONArray(response);

                            for (int i = 0; i < Result.length(); i++) {

                                JSONObject object = Result.getJSONObject(i);

                                String id = object.getString("id");
                                String name = object.getString("name");
                                String address = object.getString("address");
                                String deptartement = object.getString("department");
                                String level = object.getString("level");
                                String deviceId = object.getString("device_id");
                                String nationalId = object.getString("national_id");
                                String seatingNumber = object.getString("seating_number");

                                studentsList.add(new Student(id, name, address,
                                        deptartement, level, deviceId, nationalId, seatingNumber));
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error :"+error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
