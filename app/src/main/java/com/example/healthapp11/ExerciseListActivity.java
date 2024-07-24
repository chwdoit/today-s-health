package com.example.healthapp11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseListActivity extends AppCompatActivity {
    private TextView exerciseTypeTextView;
    private Button addCustomExerciseButton;
    private String exerciseType, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        exerciseTypeTextView = findViewById(R.id.exerciseTypeTextView);
        addCustomExerciseButton = findViewById(R.id.addCustomExerciseButton);

        exerciseType = getIntent().getStringExtra("exerciseType");
        selectedDate = getIntent().getStringExtra("selectedDate");

        if (exerciseType != null && selectedDate != null) {
            exerciseTypeTextView.setText(exerciseType + " 운동");
        } else {
            Toast.makeText(ExerciseListActivity.this, "운동 유형이나 날짜가 전달되지 않았습니다.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        addCustomExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExerciseListActivity.this, CustomExerciseActivity.class);
                intent.putExtra("exerciseType", exerciseType);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
            }
        });
    }
}