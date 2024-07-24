package com.example.healthapp11;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutSummaryActivity extends AppCompatActivity {

    private LinearLayout summaryLayout;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_summary);

        summaryLayout = findViewById(R.id.summaryLayout);
        dbHelper = new DBHelper(this);

        String selectedDate = getIntent().getStringExtra("selectedDate");
        Log.i("date", selectedDate);
        ArrayList<HashMap<String, String>> records = dbHelper.getRecordsByDate(selectedDate);

        if (records.isEmpty()) {
            TextView noRecordView = new TextView(this);
            noRecordView.setText("운동 기록이 없습니다.");
            summaryLayout.addView(noRecordView);
        } else {
            for (HashMap<String, String> record : records) {
                String exerciseType = record.get("exerciseType");
                String exerciseName = record.get("exerciseName");
                String weight = record.get("weight");
                String sets = record.get("sets");
                String reps = record.get("reps");
                String duration = record.get("duration");
                String distance = record.get("distance");
                String calories = record.get("calories");


                TextView recordView = new TextView(this);
                if ("유산소 운동".equals(exerciseType)) {
                    recordView.append("\n운동 타입: " + exerciseType + "\n거리: " + distance + "km\n소모 칼로리: " + calories + "kcal");
                } else {
                    recordView.setText("\n운동 타입: " + exerciseType + "\n운동 이름: " + exerciseName + "\n세트 수: " + sets + "\n중량: " + weight + "kg\n반복 횟수: " + reps);
                }

                summaryLayout.addView(recordView);
            }
        }
    }
}