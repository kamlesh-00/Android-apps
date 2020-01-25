package com.example.android.higherorlower;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int randnum,count=1;
    Button butt;
    EditText textt;

    public void trigger(View view)  {
        textt=(EditText) findViewById(R.id.text);
        int num=Integer.parseInt(textt.getText().toString());
        if(randnum<num)
            Toast.makeText(this, "Lower!!", Toast.LENGTH_SHORT).show();
        else if(randnum>num)
            Toast.makeText(this, "Higher!!", Toast.LENGTH_SHORT).show();
        else if(randnum==num){
            Toast.makeText(this, "Great, you got it in "+Integer.toString(count)+" steps.", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Want to play again??")
                    .setMessage("Select Yes to play again")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            update();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(1);
                        }
                    })
                    .show();
        }
        count++;
    }

    public void update(){
        Random rand = new Random();
        randnum = rand.nextInt(20)+1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();

        butt = (Button) findViewById(R.id.button);
    }
}
