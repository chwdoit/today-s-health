package com.example.healthapp11;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private LinearLayout timeCountSettingLV;
    private EditText hourET, minuteET, secondET;
    private TextView timerTextView;
    private Button startButton;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timeCountSettingLV = findViewById(R.id.timeCountSettingLV);
        hourET = findViewById(R.id.hourET);
        minuteET = findViewById(R.id.minuteET);
        secondET = findViewById(R.id.secondET);
        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {
            if (timerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        String hourStr = hourET.getText().toString();
        String minuteStr = minuteET.getText().toString();
        String secondStr = secondET.getText().toString();

        if (hourStr.isEmpty() || minuteStr.isEmpty() || secondStr.isEmpty()) {
            Toast.makeText(this, "시간을 설정해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int hours = parseTime(hourStr);
        int minutes = parseTime(minuteStr);
        int seconds = parseTime(secondStr);

        // 시간을 밀리초로 변환
        timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;

        // 타이머 시작
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                startButton.setText("Start");
                Toast.makeText(TimerActivity.this, "타이머가 종료되었습니다.", Toast.LENGTH_SHORT).show();
                timeCountSettingLV.setVisibility(View.VISIBLE);
                timerTextView.setVisibility(View.GONE);
            }
        }.start();

        timerRunning = true;
        startButton.setText("Pause");
        timeCountSettingLV.setVisibility(View.GONE);
        timerTextView.setVisibility(View.VISIBLE);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        startButton.setText("Start");
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    private int parseTime(String timeStr) {
        // 문자열을 정수로 변환, 빈 문자열인 경우 0으로 처리
        try {
            return Integer.parseInt(timeStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
