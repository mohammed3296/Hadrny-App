package com.work.unknown.absence.Adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.work.unknown.absence.Models.sesstion;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Student.sesstionDetails;

import java.util.ArrayList;
public class sesstionadapter  extends RecyclerView.Adapter<sesstionadapter.sesstionholder>{
    ArrayList<sesstion> userList=new ArrayList<>();
    Context context;
    public sesstionadapter(ArrayList<sesstion> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }
    @Override
    public sesstionadapter.sesstionholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View form = LayoutInflater.from(parent.getContext()).inflate(R.layout.sesstionrow, parent, false);
        sesstionadapter.sesstionholder sesstion= new  sesstionadapter.sesstionholder(form);
        return sesstion;
    }
    @Override
    public void onBindViewHolder(sesstionadapter.sesstionholder holder, int position) {
        final sesstion po = userList.get(position);
        holder.sesstionname.setText(po.getSesstion_name());
        holder.sesstionDate.setText(po.getSesstion_StartTime());
        holder.sesstionPlace.setText(po.getPlace());
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,sesstionDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("sename",po.getSesstion_name());
                i.putExtra("start_time",po.getSesstion_StartTime());
                i.putExtra("end_time",po.getSesstion_endTime());
                i.putExtra("allowdtime",po.getSesstion_Allowed_time());
                i.putExtra("place",po.getPlace());
                i.putExtra("lat",po.getLat());
                i.putExtra("long",po.getLng());
                i.putExtra("sesstion_id",po.getSesstion_id());
                i.putExtra("sesstion_Date",po.getDate());
                context.startActivity(i);
            }
        });
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    static class sesstionholder extends RecyclerView.ViewHolder
    {
        TextView sesstionname;
        TextView sesstionDate;
        TextView sesstionPlace;
        CardView c;
        public sesstionholder (View itemView) {
            super(itemView);
            sesstionname =itemView.findViewById(R.id.sesstion_name);
            sesstionDate =itemView.findViewById(R.id.sesstion_Date);
            sesstionPlace=itemView.findViewById(R.id.sesstion_place);
            c=itemView.findViewById(R.id.card);
        }
    }
}
