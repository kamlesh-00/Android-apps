package com.example.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Float4;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.android.weatherapp.R.*;

public class MainActivity extends AppCompatActivity {

    ImageView imageView,imageView2;
    TextView textView2,textView3,textView4;
    EditText editText;
    Button button;

    @SuppressLint("StaticFieldLeak")
    public class Downloader extends AsyncTask<String,Void,String>   {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1){
                    result = result + (char)data;
                    data = reader.read();
                }
                return result;
            }catch (Exception e)    {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String weather = jsonObject.getString("weather");
                JSONArray jsonArray = new JSONArray(weather);
                for(int i=0;i<jsonArray.length();i++)   {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    textView3.setText(obj.getString("main"));
                    textView4.setText(obj.getString("description"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Info","Error");
            }
        }
    }

    public void submit(View view)   {
        Downloader task = new Downloader();
        String url = "https://openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=b6907d289e10d714a6e88b30761fae22";
        try {
            task.execute(url);
        } catch (Exception e) {
            e.printStackTrace();
            textView3.setText("Try Again");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(id.button);

        textView2.setAlpha(0);
        textView3.setAlpha(0);
        textView4.setAlpha(0);
        imageView2.animate().alpha(0);
        editText.animate().alpha(0);
        button.setAlpha(0);

        new CountDownTimer(3000,1000)   {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                imageView.animate().alpha(0).setDuration(2000);
                imageView2.animate().alpha(1).setDuration(2000);
                textView2.animate().alpha(1).setDuration(2000);
                textView3.animate().alpha(1).setDuration(2000);
                textView4.animate().alpha(1).setDuration(2000);
                button.animate().alpha(1).setDuration(2000);
                editText.animate().alpha(1).setDuration(2000);
            }
        }.start();
    }
}
