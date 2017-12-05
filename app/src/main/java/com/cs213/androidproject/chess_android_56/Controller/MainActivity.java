package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cs213.androidproject.chess_android_56.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start a new game
        Button newGame_button = (Button)findViewById(R.id.newGame);
        newGame_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchNewGameActivity();
            }
        });

        // Start a new game
        Button history_button = (Button)findViewById(R.id.history);
        history_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchHistoryActivity();
            }
        });


        // Exit
        Button out =(Button)findViewById(R.id.exit);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });



    }

    private void launchNewGameActivity() {
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
    }

    private void launchHistoryActivity() {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
