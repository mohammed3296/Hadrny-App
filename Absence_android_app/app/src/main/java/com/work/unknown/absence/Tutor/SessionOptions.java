package com.work.unknown.absence.Tutor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.work.unknown.absence.R;
import com.work.unknown.absence.Tutor.CreateSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SessionOptions extends AppCompatActivity {
    @BindView(R.id.create_session)
    Button create_session;
    @BindView(R.id.view_session)
    Button view_session;
    @BindView(R.id.attend)
    Button attend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_options);
        ButterKnife.bind(this);

        create_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionOptions.this, CreateSession.class));
            }
        });

        view_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionOptions.this, ViewSessions.class));
            }
        });

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SessionOptions.this,StudentSearchActivity.class));
            }
        });
    }
}
