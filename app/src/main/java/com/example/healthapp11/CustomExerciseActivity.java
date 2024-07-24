package com.example.healthapp11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomExerciseActivity extends AppCompatActivity {
    private EditText exerciseNameEditText, weightEditText, setsEditText, repetitionsEditText;
    private Button saveExerciseButton;
    private DBHelper dbHelper;
    private String exerciseType, selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_exercise);

        exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        weightEditText = findViewById(R.id.weightEditText);
        setsEditText = findViewById(R.id.setsEditText);
        repetitionsEditText = findViewById(R.id.repetitionsEditText);
        saveExerciseButton = findViewById(R.id.saveExerciseButton);

        dbHelper = new DBHelper(this);

        exerciseType = getIntent().getStringExtra("exerciseType");
        selectedDate = getIntent().getStringExtra("selectedDate");

        saveExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseName = exerciseNameEditText.getText().toString().trim();
                String weightStr = weightEditText.getText().toString().trim();
                String setsStr = setsEditText.getText().toString().trim();
                String repsStr = repetitionsEditText.getText().toString().trim();

                if (exerciseName.isEmpty()) {
                    Toast.makeText(CustomExerciseActivity.this, "운동 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (exerciseType == null || selectedDate == null) {
                    Toast.makeText(CustomExerciseActivity.this, "운동 타입이나 날짜가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                float weight = weightStr.isEmpty() ? 0 : Float.parseFloat(weightStr);
                int sets = setsStr.isEmpty() ? 0 : Integer.parseInt(setsStr);
                int reps = repsStr.isEmpty() ? 0 : Integer.parseInt(repsStr);

                int duration = 0;
                float distance = 0.0f;
                float calories = 0.0f;

                boolean isInserted = dbHelper.insertRecord(selectedDate, exerciseType, exerciseName, weight, sets, reps, duration, distance, calories);

                if (isInserted) {
                    Toast.makeText(CustomExerciseActivity.this, "운동이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CustomExerciseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CustomExerciseActivity.this, "운동 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}