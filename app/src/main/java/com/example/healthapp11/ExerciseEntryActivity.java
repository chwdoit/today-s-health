package com.example.healthapp11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciseEntryActivity extends AppCompatActivity {
    private EditText weightEditText, setsEditText, repsEditText;
    private Button saveExerciseButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_entry);

        weightEditText = findViewById(R.id.weightEditText);
        setsEditText = findViewById(R.id.setsEditText);
        repsEditText = findViewById(R.id.repsEditText);
        saveExerciseButton = findViewById(R.id.saveExerciseButton);

        saveExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightStr = weightEditText.getText().toString();
                String setsStr = setsEditText.getText().toString();
                String repsStr = repsEditText.getText().toString();

                if (weightStr.isEmpty() || setsStr.isEmpty() || repsStr.isEmpty()) {
                    Toast.makeText(ExerciseEntryActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                float weight = Float.parseFloat(weightStr);
                int sets = Integer.parseInt(setsStr);
                int reps = Integer.parseInt(repsStr);

                // 날짜와 운동 유형을 인텐트로 전달받아야 함
                Intent intent = getIntent();
                String selectedDate = intent.getStringExtra("selectedDate");
                String exerciseType = intent.getStringExtra("exerciseType");

                if (selectedDate == null || exerciseType == null) {
                    Toast.makeText(ExerciseEntryActivity.this, "운동 유형이나 날짜가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 기본값 설정
                int duration = 0;
                float distance = 0.0f;
                float calories = 0.0f;

                DBHelper dbHelper = new DBHelper(ExerciseEntryActivity.this);
                boolean isInserted = dbHelper.insertRecord(selectedDate, exerciseType, "", weight, sets, reps, duration, distance, calories);

                if (isInserted) {
                    Toast.makeText(ExerciseEntryActivity.this, "운동이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ExerciseEntryActivity.this, "운동 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}