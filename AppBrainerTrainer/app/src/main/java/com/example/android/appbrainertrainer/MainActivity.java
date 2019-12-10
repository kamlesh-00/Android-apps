package com.example.android.appbrainertrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView score,correct,time,ques;
    GridLayout gridLayout;
    Button butt1,butt2,butt3,butt4,again;
    ArrayList<Integer> ans;

    int asked,right;

    CountDownTimer timer;

    int locofcorrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        again = (Button)findViewById(R.id.again);

        asked=0;
        right=0;
        ans = new ArrayList<Integer>();
        butt1 = (Button)findViewById(R.id.button1);
        butt2 = (Button)findViewById(R.id.button2);
        butt3 = (Button)findViewById(R.id.button3);
        butt4 = (Button)findViewById(R.id.button4);

        score = (TextView) findViewById(R.id.score);
        gridLayout = (GridLayout)findViewById(R.id.grid);
        correct = (TextView) findViewById(R.id.correct);
        time = (TextView) findViewById(R.id.time);
        ques = (TextView)findViewById(R.id.ques);

        score.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.INVISIBLE);
        correct.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        ques.setVisibility(View.INVISIBLE);
        again.setVisibility(View.INVISIBLE);
        change();
    }

    public void change()    {

        timer = new CountDownTimer(5000,1000)  {

            @Override
            public void onTick(long ml) {
                int t = (int)ml/1000;
                time.setText(t +"s");
            }

            @Override
            public void onFinish() {
                correct.setText("Time Up!!");
                ans.clear();
                asked++;
                score.setText(right+"/"+asked);
                change();
            }
        };
        timer.start();

        int a,b;
        Random rand = new Random();
        a=rand.nextInt(21);
        b=rand.nextInt(21);
        ques.setText(a+"+"+b);

        locofcorrans = rand.nextInt(4);

        for(int i=0;i<4;i++)    {
            if(i==locofcorrans) {
                ans.add(a+b);
            }
            else {
                int wrong = rand.nextInt(41);
                while(wrong == (a+b)) {
                    wrong = rand.nextInt(41);
                }
                ans.add(wrong);
            }
        }
        butt1.setText(Integer.toString(ans.get(0)));
        butt2.setText(Integer.toString(ans.get(1)));
        butt3.setText(Integer.toString(ans.get(2)));
        butt4.setText(Integer.toString(ans.get(3)));
    }

    public void goo(View view)  {
        score.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        ques.setVisibility(View.VISIBLE);
        Button go = (Button)findViewById(R.id.go);
        go.setVisibility(View.INVISIBLE);
    }

    public void playagain(View view)    {
        asked=0;
        right=0;
        again.setVisibility(View.INVISIBLE);
        score.setText(0+"/"+0);
        correct.setText(null);
        ans.clear();
        change();
    }

    public void choose(View view)   {
        asked++;
        if(asked<=20)  {
            if(Integer.toString(locofcorrans).equals(view.getTag().toString())) {
                correct.setText("Correct!");
                right++;
            }
            else    {
                correct.setText("Wrong!");
            }
            score.setText(right+"/"+asked);
            correct.setVisibility(View.VISIBLE);
            timer.cancel();
            ans.clear();
            change();
        }
        else    {
            again.setVisibility(View.VISIBLE);
            timer.cancel();
        }
    }
}
