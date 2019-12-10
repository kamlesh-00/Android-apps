package com.example.android.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0.Empty  1.Red  2.Yellow
    //True : Red     False : Yellow
    boolean active = true;
    int[] board={0,0,0,0,0,0,0,0,0};

    public void dropin(View view)  {
        ImageView counter = (ImageView) view;
        Button playagain = findViewById(R.id.playagain);
        TextView winner = findViewById(R.id.winner);
        counter.setTranslationY(-1500);
        int tapped=Integer.parseInt(counter.getTag().toString());
        if(active) {
            board[tapped]=1;
            counter.setImageResource(R.drawable.red);
            active = false;
        }
        else    {
            board[tapped]=2;
            counter.setImageResource(R.drawable.yellow);
            active = true;
        }
        counter.animate().translationYBy(1500).setDuration(300);
        if((board[0]==board[1] && board[1]==board[2] && board[2]!=0) || (board[3]==board[4] && board[4]==board[5] && board[3]!=0) || (board[6]==board[7] && board[7]==board[8] && board[7]!=0) || (board[0]==board[3] && board[3]==board[6] && board[6]!=0) || (board[1]==board[4] && board[4]==board[7] && board[7]!=0) || (board[2]==board[5] && board[5]==board[8] && board[8]!=0) || (board[0]==board[4] && board[4]==board[8] && board[8]!=0) || (board[2]==board[4] && board[4]==board[6] && board[6]!=0))  {
            if(active == false)  {
                Toast.makeText(this, "Red Wins!!", Toast.LENGTH_SHORT).show();
                winner.setText("Red has won!!!");
            }
            else    {
                Toast.makeText(this, "Yellow Wins!!", Toast.LENGTH_SHORT).show();
                winner.setText("Yellow has won!!!");
            }
            winner.setVisibility(View.VISIBLE);
            playagain.setVisibility(View.VISIBLE);
        }
    }

    public void playagain(View view)    {
        Button playagain = findViewById(R.id.playagain);
        TextView winner = findViewById(R.id.winner);
        playagain.setVisibility(View.INVISIBLE);
        winner.setVisibility(View.INVISIBLE);
        for(int i=0;i<9;i++)
            board[i]=0;
        active = true;
        /*
        ImageView a = (ImageView)findViewById(R.id.image00);
        a.setImageResource(R.drawable.blank);
        ImageView b = (ImageView)findViewById(R.id.image01);
        b.setImageResource(R.drawable.blank);
        ImageView c = (ImageView)findViewById(R.id.image02);
        c.setImageResource(R.drawable.blank);
        ImageView d = (ImageView)findViewById(R.id.image10);
        d.setImageResource(R.drawable.blank);
        ImageView e = (ImageView)findViewById(R.id.image11);
        e.setImageResource(R.drawable.blank);
        ImageView f = (ImageView)findViewById(R.id.image12);
        f.setImageResource(R.drawable.blank);
        ImageView g = (ImageView)findViewById(R.id.image20);
        g.setImageResource(R.drawable.blank);
        ImageView h = (ImageView)findViewById(R.id.image21);
        h.setImageResource(R.drawable.blank);
        ImageView i = (ImageView)findViewById(R.id.image22);
        i.setImageResource(R.drawable.blank);
         */
        androidx.gridlayout.widget.GridLayout mygrid = findViewById(R.id.grid);
        for(int i = 0; i < mygrid.getChildCount(); i++)   {
            ImageView child = (ImageView)mygrid.getChildAt(i);
            child.setImageDrawable(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playagain = findViewById(R.id.playagain);
        TextView winner = findViewById(R.id.winner);
        playagain.setVisibility(View.INVISIBLE);
        winner.setVisibility(View.INVISIBLE);
    }
}
