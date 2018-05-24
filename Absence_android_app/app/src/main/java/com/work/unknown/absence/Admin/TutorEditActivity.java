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
import com.work.unknown.absence.Adapters.tutorsAdapter;
import com.work.unknown.absence.Admin.constants.Constant;
import com.work.unknown.absence.Admin.model.Tutor;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TutorEditActivity extends AppCompatActivity {

    RecyclerView tutorRecycler;
    ArrayList<Tutor> tutorsList;
    tutorsAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_edit);

        getTutors();
        adapter = new tutorsAdapter(this, tutorsList);
        tutorRecycler = findViewById(R.id.tutorRecycler);
        mLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        tutorRecycler.setLayoutManager(mLayoutManager);
        tutorRecycler.setItemAnimator(new DefaultItemAnimator());
        tutorRecycler.setAdapter(adapter);


    }


    public void getTutors() {
        tutorsList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Constant.GET_TUTORS_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray Result = new JSONArray(response);

                            for (int i = 0; i < Result.length(); i++) {

                                JSONObject object = Result.getJSONObject(i);

                                String name = object.getString("name");
                                String email = object.getString("email");
                                String password = object.getString("password");
                                String id = object.getString("id");

                                tutorsList.add(new Tutor(name, email, password, id));
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

