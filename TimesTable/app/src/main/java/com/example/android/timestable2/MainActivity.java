package com.example.android.timestable2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

		seekBar.setMax(20);
		seekBar.setProgress(10);

		final ArrayList<String> arrayList = new ArrayList<String>();

		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				arrayList.clear();
				if (progress < 1) {
					progress = 1;
					seekBar.setProgress(progress);
				}
				Log.i("Current position", Integer.toString(progress));
				ListView listView = (ListView)findViewById(R.id.listView);
				for(int i=1;i<=20;i++)	{
					arrayList.add(Integer.toString(progress)+" * "+Integer.toString(i)+" = "+Integer.toString(i*progress));
				}
				listView.setAdapter(arrayAdapter);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

	}
}
