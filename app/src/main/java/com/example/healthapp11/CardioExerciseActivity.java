package com.example.healthapp11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CardioExerciseActivity extends AppCompatActivity {
    private EditText distanceEditText, caloriesEditText;
    private Button saveButton;
    private DBHelper dbHelper;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_exercise);

        distanceEditText = findViewById(R.id.distanceEditText);
        caloriesEditText = findViewById(R.id.caloriesEditText);
        saveButton = findViewById(R.id.saveCardioButton);
        dbHelper = new DBHelper(this);

        // 날짜를 선택할 필요가 없다면 적절한 값을 설정하거나 현재 날짜를 사용합니다.
        selectedDate = getIntent().getStringExtra("selectedDate");

        saveButton.setOnClickListener(v -> {
            if (selectedDate == null) {
                Toast.makeText(CardioExerciseActivity.this, "날짜를 선택해야 합니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            String distanceStr = distanceEditText.getText().toString().trim();
            String caloriesStr = caloriesEditText.getText().toString().trim();

            if (distanceStr.isEmpty() || caloriesStr.isEmpty()) {
                Toast.makeText(CardioExerciseActivity.this, "모든 필드를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            float distance = Float.parseFloat(distanceStr);
            float calories = Float.parseFloat(caloriesStr);

            // 데이터베이스에 저장
            boolean success = dbHelper.insertRecord(selectedDate, "유산소 운동", "운동이름", 0, 0, 0, 0, distance, calories);

            if (success) {
                Toast.makeText(CardioExerciseActivity.this, "유산소 운동이 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CardioExerciseActivity.this, "유산소 운동 저장에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}