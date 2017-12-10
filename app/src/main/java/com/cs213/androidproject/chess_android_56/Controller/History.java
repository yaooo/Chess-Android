package com.cs213.androidproject.chess_android_56.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cs213.androidproject.chess_android_56.R;


public class History extends AppCompatActivity {
    private ListView historyListView ;
    private ArrayAdapter<String> listAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyListView=(ListView)findViewById(R.id.history);
        listAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        historyListView.setAdapter(listAdapter);
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");
        listAdapter.add("testString");

    }
}
