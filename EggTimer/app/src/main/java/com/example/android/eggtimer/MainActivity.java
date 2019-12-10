package com.example.android.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean active;
    TextView textView;
    SeekBar seekBar;
    CountDownTimer cd;

    public void pressed(View view) throws InterruptedException {
        final Button button = (Button)findViewById(R.id.button);
        if(active) {
            button.setText("Stop");
            cd = new CountDownTimer(seekBar.getProgress()*1000,1000)  {

                @Override
                public void onTick(long l) {
                    update((int)l/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.sample);
                    mp.start();
                    button.setText("Start");
                }
            };
            cd.start();
            active = false;
            seekBar.setEnabled(false);
        }
        else {
            cd.cancel();
            button.setText("Start");
            active = true;
            textView.setText("00:00");
            seekBar.setProgress(0);
            seekBar.setEnabled(true);
        }
    }

    public void update(int progress)    {
        int min,sec;
        min = progress/60;
        sec = progress-min*60;
        String second;
        String minute;
        if(sec<=9)
            second="0"+Integer.toString(sec);
        else
            second=Integer.toString(sec);
        if(min<=9)
            minute="0"+Integer.toString(min);
        else
            minute=Integer.toString(min);
        textView.setText(minute+":"+second);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        active=true;
        textView = (TextView)findViewById(R.id.textView);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(1800);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}