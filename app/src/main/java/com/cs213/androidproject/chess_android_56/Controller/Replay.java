package com.cs213.androidproject.chess_android_56.Controller;

import android.os.Bundle;

import com.cs213.androidproject.chess_android_56.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
/**
 * Created by Sagar on 12/11/2017.
 */



public class Replay extends AppCompatActivity {
    public static String gameName;
    public ArrayList<String> moves = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);
        System.out.println(gameName);

    }


    public void ImageOnClick(View v){
        return ;
    }
}
