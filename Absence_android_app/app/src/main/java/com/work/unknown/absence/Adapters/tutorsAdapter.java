package com.work.unknown.absence.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.work.unknown.absence.Admin.TutorUpdateActivity;
import com.work.unknown.absence.Admin.model.Tutor;
import com.work.unknown.absence.R;

import java.util.ArrayList;

public class tutorsAdapter extends RecyclerView.Adapter<tutorsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Tutor> tutorsList;

    public tutorsAdapter(Context context, ArrayList<Tutor> tutorsList) {
        this.context = context;
        this.tutorsList = tutorsList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tutorName, tutorEmail;

        public MyViewHolder(View itemView) {
            super(itemView);
            tutorName = itemView.findViewById(R.id.tutor_name_txt);
            tutorEmail = itemView.findViewById(R.id.tutor_email_txt_txt);

        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_row, parent, false);
        return (new MyViewHolder(view));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Tutor tutor = tutorsList.get(position);
        holder.tutorEmail.append(tutor.getEmail());
        holder.tutorName.append(tutor.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "clicked ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, TutorUpdateActivity.class);
                intent.putExtra("tutor", tutor);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tutorsList.size();
    }

}
