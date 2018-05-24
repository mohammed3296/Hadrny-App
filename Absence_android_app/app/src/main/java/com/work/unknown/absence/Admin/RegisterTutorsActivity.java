package com.work.unknown.absence.Admin;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.work.unknown.absence.R;

public class RegisterTutorsActivity extends AppCompatActivity {

    Button addButton ;
    Button edit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutors);

        addButton = findViewById(R.id.add_tutor_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getApplicationContext() , TutorDetailActivity.class) ;
                startActivity(intent);
            }
        });

        edit = findViewById(R.id.edit_tutor_btn) ;
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext() , TutorEditActivity.class) ;
                startActivity(intent);
            }
        });
    }
}
