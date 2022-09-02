package com.example.timer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView timeText;
    AppCompatButton reset, startStop;
    boolean timerStarted = false;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();

        timeText = (TextView) findViewById(R.id.time);
        startStop = (AppCompatButton) findViewById(R.id.startStop);

    }
    public void startStopTapped(View view) {
        if (timerStarted == false){
            timerStarted = true;
            startStop.setText("STOP");
            startStop.setTextColor(ContextCompat.getColor(this, R.color.red));

            startTimer();
        }
        else {
            timerStarted = false;
            startStop.setText("Start");
            startStop.setTextColor(ContextCompat.getColor(this, R.color.green));

        }
    }
    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timeText.setText(getTimeText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    private String getTimeText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600)/ 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return  String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    public void resetTapped(View view) {
        //AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        //AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyActivity.this, R.style.MyDialogTheme);
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer;");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( timerTask != null) {
                    timerTask.cancel();
                    setButtonUI("START", R.color.green);
                    time = 0.0;
                    timerStarted = false;
                    timeText.setText(formatTime(0,0,0));

                }
            }
        });
       resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

                //do nothing apparently, haha
            }
        });
        resetAlert.show();

    }

    private void setButtonUI(String start, int color) {
        startStop.setText("Start");
        startStop.setTextColor(ContextCompat.getColor(this, color));
    }
}