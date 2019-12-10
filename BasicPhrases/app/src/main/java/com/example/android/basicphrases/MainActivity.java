package com.example.android.basicphrases;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void play(View view) {
        Button bp = (Button) view;
        MediaPlayer mp = MediaPlayer.create(this,getResources().getIdentifier(bp.getTag().toString(),"raw",getPackageName()));
        mp.start();
    }
}
