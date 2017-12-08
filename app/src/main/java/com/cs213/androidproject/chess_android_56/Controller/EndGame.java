package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs213.androidproject.chess_android_56.R;

public class EndGame extends AppCompatActivity {

    public static boolean blackWins = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Button back = (Button)findViewById(R.id.goBack);
        TextView title = (TextView)findViewById(R.id.whoWins);

        if(!blackWins){
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

    private void launchNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        blackWins = true;
        startActivity(intent);
    }
}
