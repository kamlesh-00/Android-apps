package com.example.android.newsreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public class IndexDownloader extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                Log.i("REE","IndexDownloader");
                while(data!=-1){
                    result = result + (char)data;
                    data=reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("Datasss",s);
            String[] splits = s.split(", ");
            Random rand = new Random();
            for(int i=0;i<20;i++) {
                int random = rand.nextInt(500);
                MainDownloader mainDownloaders = new MainDownloader();
                mainDownloaders.execute("https://hacker-news.firebaseio.com/v0/item/" + splits[random] + ".json?print=pretty");
            }
            Log.i("ERRRRRatURL","Here");
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class MainDownloader extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result="";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                Log.i("REE","IndexDownloader");
                while(data!=-1){
                    result = result + (char)data;
                    data=reader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                arrayList.add(jsonObject.getString("title"));
                urls.add(jsonObject.getString("url"));
                arrayAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.i("ERRRRRatURL",e.getMessage());
                e.printStackTrace();
            }
        }
    }

    ArrayList<String> arrayList;
    ArrayList<String> urls;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
        urls = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        IndexDownloader indexDownloader = new IndexDownloader();
        indexDownloader.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SecActivity.class);
                intent.putExtra("url",urls.get(position));
                startActivity(intent);
            }
        });
    }
}
