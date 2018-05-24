package com.work.unknown.absence.Adapters;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.work.unknown.absence.Models.StudentModel;
import com.work.unknown.absence.R;

import java.util.List;

/**
 * Created by Mohammed El_amary on 16/04/2018.
 */

public class StudentAdapter extends ArrayAdapter<StudentModel> {

    public StudentAdapter(Activity context, List<StudentModel> students) {

        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.student_item, parent, false);
        }

        StudentModel currentStudentModel = getItem(position);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.student_name);
        magTextView.setText(currentStudentModel.getName());
        return listItemView;
    }

}
