package com.example.focus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    
    
    ImageView stop,start;
    MaterialButton reset;
    private int seconds = 0;
    private boolean running ;
    private boolean wasRunning;

    
    TextView timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null)
        {
            seconds = savedInstanceState.getInt("seconds");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            running = savedInstanceState.getBoolean("running");

        }

        stop = (ImageView) findViewById(R.id.pause);
        start = (ImageView) findViewById(R.id.play);
        reset = (MaterialButton) findViewById(R.id.restart);




        start.setOnClickListener(view -> {
            stop.setVisibility(view.VISIBLE);
            start.setVisibility(view.GONE);
            running = true;
            StartTimer();
        });

        stop.setOnClickListener(view -> {
            start.setVisibility(view.VISIBLE);
            stop.setVisibility(view.GONE);
            running = false;
        });

        reset.setOnClickListener(view -> {
            running = false;
            seconds = 0;

            stop.setVisibility(view.GONE);
            start.setVisibility(view.VISIBLE);

        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunnning",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning)
            running = true;
    }

    public void StartTimer()
    {   timer =(TextView) findViewById(R.id.textView);
        Handler hd = new Handler();

        hd.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds%3600) / 60;
                int sec = seconds%60;
                //int mili_sec = 0;

                String time = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,sec);
                timer.setText(time);
                if(running)
                {
                    seconds++;
                }

                hd.postDelayed(this,1);
            }
        });
    }
}