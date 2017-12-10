package com.cs213.androidproject.chess_android_56.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.os.Bundle;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Context;

import com.cs213.androidproject.chess_android_56.R;


public class History extends AppCompatActivity {
    private ListView historyListView ;
    private ArrayAdapter<String> listAdapter ;
    private ArrayList<String> gameTitles;
    private ArrayList<String> gameDates;
    String ret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyListView=(ListView)findViewById(R.id.history);
        listAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        historyListView.setAdapter(listAdapter);
        gameTitles=new ArrayList<String>();
        Context context=this.getApplicationContext();

        try{

            InputStream inputStream = context.openFileInput("gameHistory.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                System.out.println(ret);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        gameTitles=getTitles();
        gameDates=getDates();
        listAdapter.addAll(gameDates);

    }



        public ArrayList<String> getTitles(){
            ArrayList<String> g=new ArrayList<String>();
            String current="";
            boolean title=true;
            for(int i=0;i< ret.length();i++){
                char a = ret.charAt(i);
                if(a=='+' && title==true){
                    title=false;
                    g.add(current);
                    current="";
                }
                else if(a==':' && title==false){
                    i=i+2;
                    if(i>=ret.length()){
                        break;
                    }
                    title=true;
                    a = ret.charAt(i);
                }

                if(title==true) {
                    current += a;
                }
            }

           return g;
        }

        public ArrayList<String> getDates(){
            ArrayList<String> g=new ArrayList<String>();
            String current="";
            boolean date=false;
            for(int i=0;i<ret.length();i++){
                char a=ret.charAt(i);
                if(a=='+' && date==false){
                    date=true;
                    i++;
                    a=ret.charAt(i);
                }
                else if(a=='~' && date==true){
                    date=false;
                    g.add(current);
                    current="";
                }

                if(date==true){
                    current+=a;
                }
            }

            return g;

        }
}
