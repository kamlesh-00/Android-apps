package com.example.android.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class TextActivity extends AppCompatActivity {

    String message;
    String ab = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        final int pos = intent.getIntExtra("Position",-1);
        if(pos!=-1){
            editText.setText(MainActivity.arrayList.get(pos));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainActivity.arrayList.set(pos,String.valueOf(s));
                    try {
                        MainActivity.sharedPreferences.edit().putString("Notes",ObjectSerializer.serialize(MainActivity.arrayList)).apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        else {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ab=String.valueOf(s);
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ab!=""){
            MainActivity.arrayList.add(ab);
            try {
                MainActivity.sharedPreferences.edit().putString("Notes",ObjectSerializer.serialize(MainActivity.arrayList)).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MainActivity.arrayAdapter.notifyDataSetChanged();
        finish();
    }
}