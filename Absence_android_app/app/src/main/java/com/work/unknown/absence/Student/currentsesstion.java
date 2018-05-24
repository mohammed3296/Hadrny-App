package com.work.unknown.absence.Student;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Adapters.sesstionadapter;
import com.work.unknown.absence.Login;
import com.work.unknown.absence.Models.sesstion;
import com.work.unknown.absence.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class currentsesstion extends AppCompatActivity {
    RecyclerView sesstions;
    RequestQueue rq;
    String Request_url="http://192.168.43.21/attendance_app/public/attendance/student/get_sessions";
    ArrayList<sesstion>sesstions_data=new ArrayList<>();
    sesstionadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_AppBarOverlay);
        setContentView(R.layout.activity_currentsesstion);
        sesstions=findViewById(R.id.currentsee);
        rq = Volley.newRequestQueue(this);
        sesstions.setLayoutManager(new LinearLayoutManager(currentsesstion.this));
        getcurrentsesstions();
    }
    public void getcurrentsesstions()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
                        sesstionUtils.setSesstion_id(jsonObject.getString("date"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sesstions_data.add(sesstionUtils);

                }

           //     adapter = new CustomRecyclerAdapter(MainActivity.this, personUtilsList);
              if(sesstions_data.size()>0) {
                  adapter = new sesstionadapter(sesstions_data, getApplicationContext());
                  sesstions.setAdapter(adapter);
                  sesstions.setAdapter(adapter);
              }
              else
                  {
                      alerter("No Sesstions  to show");
                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ", error.getMessage());
            }
        });

        rq.add(jsonArrayRequest);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.me,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.show_A_detail)
        {
         Intent i=new Intent(currentsesstion.this,showAttendanceDetails.class);
         startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    public void alerter(String v)
    {
        Alerter.create(currentsesstion.this)
                .setTitle("error")
                .setText(v)
                .setBackgroundColorRes(R.color.darkDeepOrange)
                .setDuration(3000)
                .setIcon(R.drawable.alarm)
                .show();
    }
}

