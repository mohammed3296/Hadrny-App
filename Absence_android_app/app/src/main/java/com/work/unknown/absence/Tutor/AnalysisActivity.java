package com.work.unknown.absence.Tutor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.work.unknown.absence.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Bundle data = getIntent().getExtras();

        ArrayList<String> names = data.getStringArrayList("ARRAY_OF_IDS");
        ArrayList<Integer> students = data.getIntegerArrayList("ARRAY_OF_STUDENTS");
        BarChart mBarChart = (BarChart) findViewById(R.id.barchart);
        for (int i = 0; i < students.size(); i++) {
            mBarChart.addBar(new BarModel(students.get(i), 0xFF123456));
        }
        mBarChart.startAnimation();
    }
}
