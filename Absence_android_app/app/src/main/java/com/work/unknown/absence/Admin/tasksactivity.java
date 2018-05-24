package com.work.unknown.absence.Admin;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import com.work.unknown.absence.R;
public class tasksactivity extends AppCompatActivity {
    Button registerStudents;
    Button registerTutors;
    Button registerHoles;
    Button addAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasksactivity);
        registerStudents = findViewById(R.id.register_student_btn);
        registerStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterStudentsActivity.class);
                startActivity(intent);
            }
        });
        registerTutors = findViewById(R.id.register_tutors_btn);
        registerTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterTutorsActivity.class);
                startActivity(intent);
            }
        });

        registerHoles = findViewById(R.id.register_holes_btn);
        registerHoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterHolesActivity.class);
                startActivity(intent);
            }
        });

        addAdmin = findViewById(R.id.add_admin_btn);
        addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), AddAdminActivity.class);
                startActivity(intent);
            }
        });

    }
}
