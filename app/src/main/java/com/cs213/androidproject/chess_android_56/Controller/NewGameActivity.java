package com.cs213.androidproject.chess_android_56.Controller;

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

        GridLayout board = (GridLayout)findViewById(R.id.boardlayout);
        initializeGraphicBoard(board);
    }


    // TODO: Try to find a easier way to add view, because we need to know how to do it for "move" anyway
    private void initializeGraphicBoard(GridLayout board){
        ImageView myImage = new ImageView(this);
        myImage.setImageResource(R.drawable.blackrook);
        board.addView(myImage);
        ImageView myImage1 = new ImageView(this);

        myImage1.setImageResource(R.drawable.blackknight);
        board.addView(myImage1);

//        myImage.setImageResource(R.drawable.blackrook);
//        board.addView(myImage);

//        myImage.setImageResource(R.drawable.blackknight);
//        board.addView(myImage,1);
//        board.addView(myImage,6);
//
//        myImage.setImageResource(R.drawable.blackbishop);
//        board.addView(myImage,2);
//        board.addView(myImage,5);
//
//        myImage.setImageResource(R.drawable.blackqueen);
//        board.addView(myImage,4);
//        myImage.setImageResource(R.drawable.blackking);
//        board.addView(myImage,3);
//
//        myImage.setImageResource(R.drawable.blackpawn);
//        for(int i = 0; i < 8 ; i++){
//            board.addView(myImage,i+8);
//        }
//
//        myImage.setImageResource(R.drawable.whitepawn);
//        for(int i = 0; i < 8 ; i++){
//            board.addView(myImage,i+48);
//        }
//
//        myImage.setImageResource(R.drawable.whiterook);
//        board.addView(myImage,56);
//        board.addView(myImage,63);
//
//        myImage.setImageResource(R.drawable.whiteknight);
//        board.addView(myImage,57);
//        board.addView(myImage,62);
//
//        myImage.setImageResource(R.drawable.whitebishop);
//        board.addView(myImage,58);
//        board.addView(myImage,61);
//
//        myImage.setImageResource(R.drawable.whitequeen);
//        board.addView(myImage,60);
//        myImage.setImageResource(R.drawable.whiteking);
//        board.addView(myImage,59);

    }
}
