package com.work.unknown.absence.Student;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.work.unknown.absence.Adapters.attendancedetails;
import com.work.unknown.absence.Adapters.detailadapter;
import com.work.unknown.absence.Adapters.sesstionadapter;
import com.work.unknown.absence.Models.sesstion;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showAttendanceDetails extends AppCompatActivity {
    RecyclerView sesstions;
    RequestQueue rq;
    String Request_url="http://192.168.43.21/attendance_app/public/attendance/student/get_sessions/{}";
    ArrayList<sesstion> sesstions_data=new ArrayList<>();
    detailadapter adapter;
    SharedPreferences sharedPreference;
    String Email=" ";
    public static final String MyPREFERENCES = "logdata" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_details);
        sesstions=findViewById(R.id.currentseeee);
        rq = Volley.newRequestQueue(this);
        sharedPreference=getApplication().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Email=sharedPreference.getString("Email","null");
        if(Email!=null) {
            Request_url = "http://192.168.43.21/attendance_app/public/attendance/student/get_sessions_student/"+Email;
        }
        getcurrentsesstions();
    }
    public void getcurrentsesstions()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                  if(response.length()==0)
                {
                    Toast.makeText(showAttendanceDetails.this, "No sesstions to view", Toast.LENGTH_SHORT).show();
                }
                for(int i = 0; i < response.length(); i++){
                    sesstion sesstionUtils = new sesstion();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        sesstionUtils.setSesstion_name(jsonObject.getString("name"));
                        sesstionUtils.setSesstion_StartTime(jsonObject.getString("start_time"));
                        sesstionUtils.setSesstion_endTime(jsonObject.getString("end_time"));
                        sesstionUtils.setSesstion_Allowed_time(jsonObject.getString("registeration_time"));
                        sesstionUtils.setPlace(jsonObject.getString("hall_name"));
                        sesstionUtils.setSesstion_id(jsonObject.getString("id"));
                    //    Toast.makeText(showAttendanceDetails.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sesstions_data.add(sesstionUtils);
                }
                if(sesstions_data.size()>0) {
                    sesstions.setLayoutManager(new LinearLayoutManager(showAttendanceDetails.this));
                    adapter = new detailadapter(sesstions_data, getApplicationContext());
                    sesstions.setAdapter(adapter);
                    sesstions.setAdapter(adapter);
                }
                else
                    {
                        Toast.makeText(showAttendanceDetails.this, "No sesstions to view" , Toast.LENGTH_SHORT).show();
                    }
           }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /// Log.i("Volley Error: ", error.getMessage());
                if(error!=null) {
                    Toast.makeText(showAttendanceDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rq.add(jsonArrayRequest);

    }
}
