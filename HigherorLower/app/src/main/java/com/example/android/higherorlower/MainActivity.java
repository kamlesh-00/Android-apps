package com.example.android.higherorlower;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int randnum,count=1;
    public void trigger(View view)  {
        EditText text=(EditText) findViewById(R.id.text);
        int num=Integer.parseInt(text.getText().toString());
        if(randnum<num)
            Toast.makeText(this, "Lower!!", Toast.LENGTH_SHORT).show();
        else if(randnum>num)
            Toast.makeText(this, "Higher!!", Toast.LENGTH_SHORT).show();
        else if(randnum==num)
            Toast.makeText(this, "Great, you got it in "+Integer.toString(count)+" steps.", Toast.LENGTH_SHORT).show();
        count++;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random rand = new Random();
        randnum = rand.nextInt(20)+1;
    }
}
