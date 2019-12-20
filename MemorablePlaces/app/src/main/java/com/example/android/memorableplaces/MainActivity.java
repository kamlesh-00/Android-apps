package com.example.android.memorableplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static ArrayAdapter arrayAdapter;

    static ArrayList<String> maps;
    static ArrayList<LatLng> locations = new ArrayList<LatLng>();
    ;

    static ArrayList<String> newmap = new ArrayList<String>();
    static ArrayList<String> newlati = new ArrayList<String>();
    static ArrayList<String> newlongi = new ArrayList<String>();

    static SharedPreferences sharedPreferences;

    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //      final Intent intent1 = getIntent();

        listView = (ListView) findViewById(R.id.ListView);

        sharedPreferences = this.getSharedPreferences("com.example.android.memorableplaces", MODE_PRIVATE);

        maps = new ArrayList<String>();

        ArrayList<String> newmap = new ArrayList<String>();
        ArrayList<String> lati = new ArrayList<String>();
        ArrayList<String> longi = new ArrayList<String>();

        maps.add("Add new location...");
        locations.add(new LatLng(0, 0));
        if (flag == 1 && maps.size() > newmap.size()) {
            try {
                newmap = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Maps", ObjectSerializer.serialize(new ArrayList<String>())));
                newlati = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Lati", ObjectSerializer.serialize(new ArrayList<String>())));
                newlongi = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("Longi", ObjectSerializer.serialize(new ArrayList<String>())));
                for (int i = 0; i < newmap.size(); i++) {
                    maps.add(newmap.get(i));
                    locations.add(new LatLng(Double.parseDouble(newlati.get(i)), Double.parseDouble(newlongi.get(i))));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, maps);
        listView.setAdapter(arrayAdapter);

    /*    if(intent1.getStringExtra("Map Addresss")!=null) {
            Log.i("Inforr", "Returns here");
            String a = intent1.getStringExtra("Map Addresss");
            maps.add(a);
        }
*/

        //   final Intent intent = getIntent();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                /*if(position == 0){
                    startActivity(intent);
                }else{
                    Intent intent1=getIntent();
                    startActivity(intent);
                    maps.add(intent1.getString());
                 }*/
                intent.putExtra("Map Address", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("Inforr", "Came back on MainActivity");
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            maps.add(data.getStringExtra("Map Addresss"));
        }
    }
}