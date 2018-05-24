package com.work.unknown.absence.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.work.unknown.absence.ListItemClickListener;
import com.work.unknown.absence.Models.SessionModel;
import com.work.unknown.absence.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Mohammed El_amary on 14/04/2018.
 */

public class SessionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ListItemClickListener lOnClickListener;
    public List<SessionModel> data = Collections.emptyList();
   SessionModel current;

    public SessionAdapter(ListItemClickListener listener) {
        lOnClickListener = listener;
    }


    public void setSessionData(List<SessionModel> sessionsIn, Context context) {
        data = sessionsIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.session_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MyHolder viewHolder = new MyHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.sessionNam.setText(current.getName());
        myHolder.date.setText(current.getDate());
        myHolder.startTime.setText(current.getStart_time());
        myHolder.numberOfStudent.setText(current.getNumberOfStudents());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView sessionNam, numberOfStudent, date, startTime;

        public MyHolder(View itemView) {
            super(itemView);

            sessionNam = (TextView) itemView.findViewById(R.id.session_name_item);
            numberOfStudent = (TextView) itemView.findViewById(R.id.number_of_students);
            date = (TextView) itemView.findViewById(R.id.date_sesstion_item);
            startTime = (TextView) itemView.findViewById(R.id.time_session_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            lOnClickListener.onListItemClick(data.get(clickedPosition));
        }
    }
}
