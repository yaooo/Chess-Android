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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        Context context=this.getApplicationContext();
        String ret="";
        ArrayList<String> moves = new ArrayList<>();
        try {

            InputStream inputStream = context.openFileInput("gameHistory.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                System.out.println(ret);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        moves=getMoves(ret);


    }


    public void ImageOnClick(View v){
        return ;
    }
    public ArrayList<String> getMoves(String s){
        ArrayList<String> L=new ArrayList<>();
        int begin=0;
        if(s.contains(gameName)){
            begin=s.indexOf(gameName);
            System.out.println(begin);
        }
        return L;
    }

}
