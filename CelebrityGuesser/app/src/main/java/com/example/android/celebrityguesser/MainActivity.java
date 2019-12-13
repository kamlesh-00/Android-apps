package com.example.android.celebrityguesser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button butt1,butt2,butt3,butt4;

    int celebChoosen;

    String[] ans= new String[4];
    int locofcorrans;
    ArrayList<String> celebUrls = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();

    ImageDownloader imageDownloader;
    Downloader task;

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>    {

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);

            }catch (Exception e)   {
                e.printStackTrace();
                return null;
            }
        }
    }

    public class Downloader extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";

            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
                connection1.connect();
                InputStream in = connection1.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data!=-1)    {
                    result = result+(char)data;
                    data = reader.read();
                }
                return result;
            }   catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    public void change()    {

        Random rand = new Random();
        String wrong="";
        celebChoosen = rand.nextInt(celebUrls.size());

        try {
            imageDownloader = new ImageDownloader();
            imageView.setImageBitmap(imageDownloader.execute(celebUrls.get(celebChoosen)).get());
        }catch (Exception e){
            Log.i("Error","Downloading image");
            e.printStackTrace();
        }
        locofcorrans = rand.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (i == locofcorrans)
                ans[i] = celebNames.get(celebChoosen);
            else {
                wrong = celebNames.get(rand.nextInt(celebUrls.size()));
                while (wrong.equals(celebNames.get(celebChoosen))) {
                    wrong = celebNames.get(rand.nextInt(101));
                }
                ans[i] = wrong;
            }
        }
        butt1.setText(ans[0]);
        butt2.setText(ans[1]);
        butt3.setText(ans[2]);
        butt4.setText(ans[3]);
    }

    public void guess(View view)    {
        if(Integer.toString(locofcorrans).equals(view.getTag().toString())) {
            Toast.makeText(this, "You are Right!!", Toast.LENGTH_SHORT).show();
        }
        else    {
            Toast.makeText(this, "Wrong it's "+celebNames.get(celebChoosen), Toast.LENGTH_SHORT).show();
        }
        change();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String result="";

        imageView = (ImageView)findViewById(R.id.imageView);
        butt1 = (Button)findViewById(R.id.button1);
        butt2 = (Button)findViewById(R.id.button2);
        butt3 = (Button)findViewById(R.id.button3);
        butt4 = (Button)findViewById(R.id.button4);

        Random rand = new Random();

        task = new Downloader();
        String[] split;
        try {
            result = task.execute("http://www.posh24.se/kandisar").get();
            split = result.split("div class=\"listedArticle\"");

            Pattern p = Pattern.compile("img src=\"(.*?)\"");
            Matcher m = p.matcher(split[0]);
            while(m.find()) {
                celebUrls.add(m.group(1));
            }

            p=Pattern.compile("alt=\"(.*?)\"");
            m=p.matcher(split[0]);
            while(m.find()) {
                celebNames.add(m.group(1));
            }

            change();
        }catch (Exception e)    {
            e.printStackTrace();
        }
    }
}
