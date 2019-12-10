package com.example.android.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convert(View view)  {
        ImageView image = (ImageView)findViewById(R.id.image);
        image.setImageResource(R.drawable.thumbs);
        TextView t = (TextView)findViewById(R.id.editText);
        double prev;
        prev = Double.parseDouble(t.getText().toString());
        double ne;
        ne = prev*71.65;
        String n = Double.toString(ne);
        Toast.makeText(this, n, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
