package com.example.healthapp11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private LinearLayout buttonsLayout;
    private Button upperBodyButton, lowerBodyButton, cardioButton, todayWorkoutButton;
    private com.google.android.material.floatingactionbutton.FloatingActionButton addExerciseButton;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        buttonsLayout = findViewById(R.id.buttonsLayout);
        upperBodyButton = findViewById(R.id.upperBodyButton);
        lowerBodyButton = findViewById(R.id.lowerBodyButton);
        cardioButton = findViewById(R.id.cardioButton);
        todayWorkoutButton = findViewById(R.id.todayWorkoutButton);
        addExerciseButton = findViewById(R.id.fab_addExerciseButton);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            buttonsLayout.setVisibility(View.VISIBLE);
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        upperBodyButton.setOnClickListener(v -> navigateToExerciseList("상체"));
        lowerBodyButton.setOnClickListener(v -> navigateToExerciseList("하체"));
        cardioButton.setOnClickListener(v -> navigateToCardioExercise());
        todayWorkoutButton.setOnClickListener(v -> navigateToWorkoutSummary());
        addExerciseButton.setOnClickListener(v -> navigateToTimerActivity());
    }

    private void navigateToExerciseList(String exerciseType) {
        if (selectedDate != null) {
            Intent intent = new Intent(MainActivity.this, ExerciseListActivity.class);
            intent.putExtra("exerciseType", exerciseType);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToCardioExercise() {
        if (selectedDate != null) {
            Intent intent = new Intent(MainActivity.this, CardioExerciseActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToWorkoutSummary() {
        if (selectedDate != null) {
            Intent intent = new Intent(MainActivity.this, WorkoutSummaryActivity.class);
            intent.putExtra("selectedDate", selectedDate);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToTimerActivity() {
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }
}