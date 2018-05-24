package com.work.unknown.absence.Tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.tapadoo.alerter.Alerter;
import com.work.unknown.absence.Adapters.SessionAdapter;
import com.work.unknown.absence.Ip;
import com.work.unknown.absence.ListItemClickListener;
import com.work.unknown.absence.Models.SessionModel;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Tutor.AnalysisActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSessions extends AppCompatActivity implements ListItemClickListener {
    private RecyclerView mRecyclerView;
    private SessionAdapter mSessiontAdapter;
    List<SessionModel> list;
    private static final String url = "http://" + Ip.ip + "/attendance_app/public/attendance/tutor/get_sessions/1";

    private Button analysisButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_session);
        list = new ArrayList<>();
        analysisButton = (Button) findViewById(R.id.analysis_button);
        analysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ArrayList<String> names = new ArrayList<>();
                ArrayList<Integer> students = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    names.add(list.get(i).getName());
                    students.add(Integer.parseInt(list.get(i).getNumberOfStudents()));
                }
                bundle.putStringArrayList("ARRAY_OF_IDS", names);
                bundle.putIntegerArrayList("ARRAY_OF_STUDENTS", students);
                Intent trasverActivity = new Intent(ViewSessions.this, AnalysisActivity.class);
                trasverActivity.putExtras(bundle);
                startActivity(trasverActivity);

            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSessiontAdapter = new  SessionAdapter(this);
        RequestQueue queue = Volley.newRequestQueue(ViewSessions.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String sessionName = jsonObject1.getString("name");
                                String sessionTime = jsonObject1.getString("start_time");
                                String date = jsonObject1.getString("date");
                                String numberOfStudents = jsonObject1.getString("no_students");
                                String sessionId = jsonObject1.getString("id");
                                list.add(new SessionModel(sessionId, sessionName, "", "", numberOfStudents, sessionTime, date));
                            }
                            mSessiontAdapter.setSessionData(list, ViewSessions.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Alerter.create(ViewSessions.this)
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
                Toast.makeText(ViewSessions.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };
        queue.add(stringRequest);
        mRecyclerView.setAdapter(mSessiontAdapter);
    }

    @Override
    public void onListItemClick( SessionModel clickedItemIndex) {
        Toast.makeText(this, clickedItemIndex.getName(), Toast.LENGTH_SHORT).show();
    }
}
