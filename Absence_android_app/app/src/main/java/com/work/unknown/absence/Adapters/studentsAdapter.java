package com.work.unknown.absence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.work.unknown.absence.Admin.StudentUpdateActivity;
import com.work.unknown.absence.Admin.model.Student;
import com.work.unknown.absence.R;

import java.util.ArrayList;


/**
 * Created by asdasd on 17/04/2018.
 */

public class studentsAdapter extends RecyclerView.Adapter<studentsAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<Student> studentsList;

    public studentsAdapter(Context context, ArrayList<Student> studentsList) {
        this.context = context;
        this.studentsList = studentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_row,
                parent, false);
        return (new MyViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Student student = studentsList.get(position);
        holder.studentEmail.append(student.getSeatingNumber());
        holder.studentPassword.append(student.getNationalId());
        holder.studentName.append(student.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "clicked ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, StudentUpdateActivity.class);
                intent.putExtra("student", student);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView studentName, studentEmail, studentPassword;

        public MyViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name_txt);
            studentEmail = itemView.findViewById(R.id.tutor_email_txt);
            studentPassword = itemView.findViewById(R.id.student_password_txt);

        }


    }

}
