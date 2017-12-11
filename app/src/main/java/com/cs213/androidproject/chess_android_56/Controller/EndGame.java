package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import com.cs213.androidproject.chess_android_56.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;

public class EndGame extends AppCompatActivity {

    public static boolean blackWins = true;
    public static String log="";
    public static boolean gameSaved=false;
    public String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Button back = (Button) findViewById(R.id.goBack);
        TextView title = (TextView) findViewById(R.id.whoWins);

        if (!blackWins) {
            title.setText(R.string.WhiteWins);
        }
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Going back to Main Menu...";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                launchNewActivity();
            }
        });

    }

    public void saveGame(View v) throws IOException{
        if(!(gameSaved)){
            final ArrayList<String> titles=getTitles();
            final Context context= getApplication().getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);



            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();

                    try {
                        if(m_Text.length()<3){
                            makeToast("please enter a title with more than 3 characters");
                            return ;
                        }
                        else if(titles.contains(m_Text)){
                            makeToast("title name taken please enter another title name");
                            return;
                        }
                        Date d = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                        String df=sdf.format(d);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("gameHistory.txt", Context.MODE_APPEND));
                        log=m_Text+"+"+df+"~"+log;
                        log+=":|";
                        outputStreamWriter.write(log);
                        outputStreamWriter.close();
                        makeToast("game saved");
                        gameSaved = true;
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        }
        else{
            makeToast("game already saved");

        }

    }

    private void launchNewActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        blackWins = true;
        startActivity(intent);
    }

    public void makeToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public ArrayList<String> getTitles(){
        Context context = this.getApplicationContext();
        String ret="";
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


}
