package com.example.android.memorableplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    static ArrayAdapter arrayAdapter;

    static ArrayList<String> maps;
    static ArrayList<LatLng> locations = new ArrayList<LatLng>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //      final Intent intent1 = getIntent();

        listView = (ListView)findViewById(R.id.ListView);

        maps = new ArrayList<String>();
        maps.add("Add new location...");
        locations.add(new LatLng(0,0));
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,maps);
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
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                /*if(position == 0){
                    startActivity(intent);
                }else{
                    Intent intent1=getIntent();
                    startActivity(intent);
                    maps.add(intent1.getString());
                 }*/
                intent.putExtra("Map Address",position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        Log.i("Inforr","Came back on MainActivity");
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            maps.add(data.getStringExtra("Map Addresss"));
        }
    }
}