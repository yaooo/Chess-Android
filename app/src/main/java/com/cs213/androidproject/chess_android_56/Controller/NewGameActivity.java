package com.cs213.androidproject.chess_android_56.Controller;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs213.androidproject.chess_android_56.R;

public class NewGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);



        // TODO for later usage
        GridLayout board = (GridLayout)findViewById(R.id.boardlayout);

        String temp = "a8";

        Resources res = getResources();
        int id = res.getIdentifier(temp, "id", NewGameActivity.this.getPackageName());

        findViewById(id);
    }


    // TODO: Try to find a easier way to add view, because we need to know how to do it for "move" anyway
    private void initializeGraphicBoard(GridLayout board){

    }
}
