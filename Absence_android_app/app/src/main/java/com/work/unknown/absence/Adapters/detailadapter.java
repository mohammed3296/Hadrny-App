package com.work.unknown.absence.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.work.unknown.absence.Models.sesstion;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Student.sesstionDetails;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.work.unknown.absence.Models.sesstion;
import com.work.unknown.absence.R;
import com.work.unknown.absence.Student.sesstionDetails;
import java.util.ArrayList;
public class detailadapter extends RecyclerView.Adapter<sesstionadapter.sesstionholder>{
    ArrayList<sesstion> userList=new ArrayList<>();
    Context context;
    public detailadapter(ArrayList<sesstion> userList, Context context) {
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
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }
    class sesstionholder extends RecyclerView.ViewHolder
    {
        TextView sesstionname;
        public sesstionholder (View itemView) {
            super(itemView);
            sesstionname =itemView.findViewById(R.id.sesstion_name);
        }
    }
}
