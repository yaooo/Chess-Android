package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.cs213.androidproject.chess_android_56.Model.*;
import com.cs213.androidproject.chess_android_56.R;

import java.util.Scanner;

import static java.security.AccessController.getContext;

public class NewGameActivity extends AppCompatActivity {

    private static int start = -1;
    private static int end = -1;
    private static String startPos = "";
    private static String endPos = "";
    private static int pstart=-1;
    private static int pend=-1;
    private static String psp="";
    private static String pep="";
    Board b = new Board();
    private static Square whiteKing;
    private static Square blackKing;
    private boolean whiteTurn = true;
    private boolean blackCap = false;
    private boolean whiteCap = false;
    private boolean validMove = false;
    private boolean passantdraw=false;
    private String passantLocation;
    private String gameLog="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        b.initBoard();
        whiteKing = b.getSquare("e1");
        blackKing = b.getSquare("e8");
        
       /* // TODO for later usage
        GridLayout board = (GridLayout)findViewById(R.id.boardLayout);

        String temp = "a8";

        Resources res = getResources();
        int id = res.getIdentifier(temp, "id", NewGameActivity.this.getPackageName());

        findViewById(id);*/
    }
    public void undoClick(View v){
        b.getSquare(pep).getPiece().move(pep,psp,b);
        ImageView starting = (ImageView) findViewById(pend);
        ImageView ending = (ImageView) findViewById(pstart);
        Drawable draw = starting.getDrawable();
        starting.setImageResource(android.R.color.transparent); // make it transparent
        ending.setImageDrawable(draw);
        whiteTurn=!whiteTurn;
        gameLog=gameLog.substring(gameLog.length()-6);
        return;
    }
    public void drawButton(View v){
        AlertDialog.Builder builder;
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
        alertDialogBuilder.setTitle("Draw?");
        alertDialogBuilder.setMessage("Are you sure you want to end the game in a draw?");
        alertDialogBuilder
                .setMessage("End on a draw?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
    public void resignButton(View v){
        AlertDialog.Builder builder;
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

        if(whiteTurn) {
            alertDialogBuilder.setTitle("Are you sure you want to concede the game white player?");
        }
        else{
            alertDialogBuilder.setTitle("Are you sure you want to concede the game black player?");
        }
        alertDialogBuilder
                .setMessage("Concede?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    public void ImageOnClick(View v) {
        int id = v.getId();
        if (start != -1 && end != -1) {
            start = -1;
            end = -1;
            startPos = "";
            endPos = "";
        }

        if (start == -1) {
            start = id;
            ImageView starting = (ImageView) findViewById(start);
            starting.setBackgroundColor(Color.LTGRAY);
            startPos = getId(v);
            return;
        } else if (end == -1) {
            end = id;
            ImageView starting = (ImageView) findViewById(start);
            starting.setBackgroundColor(Color.TRANSPARENT);
            endPos = getId(v);
        }

        if (start != -1 && end != -1) {
            ImageView starting = (ImageView) findViewById(start);
            ImageView ending = (ImageView) findViewById(end);
            game();
            if (validMove) {
                Drawable draw = starting.getDrawable();
                starting.setImageResource(android.R.color.transparent); // make it transparent
                ending.setImageDrawable(draw);
                if(passantdraw){
                    Resources res = getResources();
                    int id2 = res.getIdentifier(passantLocation, "id", NewGameActivity.this.getPackageName());
                    ImageView op=(ImageView)findViewById(id2);
                    op.setImageResource(android.R.color.transparent);
                    passantdraw=false;
                    passantLocation="";
                }
                pstart=start;
                pend=end;
                psp=startPos;
                pep=endPos;
                gameLog+= psp+","+pep+"|";
                System.out.println(gameLog);
            } else {
            }


        }
        Log.i("The onClick id is:", "" + v.getId());
    }

    /**
     * @return "[package]:id/[xml-id]"
     * where [package] is your package and [xml-id] is id of view
     * or "no-id" if there is no id
     */
    private String getId(View view) {
        String id = view.getResources().getResourceName(view.getId());
        id = id.substring(id.length() - 2);
        Log.i("The ID in string is ", id);
        return id;
    }

    private void makeToast(String a){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,a,duration);
        toast.show();
    }
    private static boolean draw = false;

    private void game() {

        if (startPos.length() == 2 && endPos.length() == 2) {

            validMove=false;
            //TODO: do the moves

            Pawn PassantTrack = null;
            while (!(whiteKing.getPiece().checkMate(b)) && !(blackKing.getPiece().checkMate(b))) {
                if(b.getSquare(startPos).getPiece()==null){
                    validMove=false;
                    if(whiteTurn){
                        makeToast("illegal move. please select a piece white player");
                        return;
                    }
                    else{
                        makeToast("illegal move. please select a piece black player");
                        return;
                    }
                }
                else if (b.getSquare(startPos).getPieceColor().equals("b") && whiteTurn == true) {
                    makeToast("invalid move fow white player, select your own piece");
                    validMove = false;
                    return;
                } else if (b.getSquare(startPos).getPieceColor().equals("w") && whiteTurn == false) {
                    makeToast("invalid move for black player, select your own piece");
                    validMove = false;
                    return;
                } else if (b.getSquare(startPos).getPiece().isValidMove(startPos, endPos, b)) {
                    b.getSquare(startPos).getPiece().move(startPos, endPos, b);
                    validMove = true;
                    if (b.getSquare(endPos).getPieceType().equals("K")) {
                        if (b.getSquare(endPos).getPiece().isWhite() == true && whiteTurn) {
                            whiteKing = b.getSquare(endPos);
                        } else if (b.getSquare(endPos).getPiece().isWhite() == false && !(whiteTurn)) {
                            blackKing = b.getSquare(endPos);
                        } else if (b.getSquare(endPos).getPiece().isWhite() == false && whiteTurn) {
                            whiteCap = true;
                            break;
                        } else if (b.getSquare(endPos).getPiece().isWhite() == true && !(whiteTurn)) {
                            blackCap = true;
                            break;
                        }

                    }

                    if (b.getSquare(endPos).getPieceType().equals("p")) {
                        if(b.getSquare(endPos).getPiece().getPas()!=null){
                            passantdraw=true;
                            passantLocation=b.getSquare(endPos).getPiece().getPas();
                            b.getSquare(endPos).getPiece().setPas();
                        }
                        else if (PassantTrack == null) {
                            PassantTrack = (Pawn) b.getSquare(endPos).getPiece();
                        } else {
                            PassantTrack.setEnpassant();
                            PassantTrack = (Pawn) b.getSquare(endPos).getPiece();
                        }
                    } else if (PassantTrack != null && !(b.getSquare(endPos).getPieceType().equals("p"))) {
                        PassantTrack.setEnpassant();
                        PassantTrack = null;
                    }
                    System.out.println();
                    b.printBoard();
                    if (whiteKing.getPiece().inCheck(b)) {
                        System.out.println("white player in check.");
                    }
                    if (blackKing.getPiece().inCheck(b)) {
                        System.out.println("black player in check.");
                    }
                    whiteTurn = !whiteTurn;

                    if (whiteKing.getPiece().checkMate(b)) {
                        makeToast("Black wins");
                        finishGame(true);
                    } else if (blackKing.getPiece().checkMate(b)) {
                        makeToast("White wins");
                        finishGame(false);

                    }

                    if(validMove=true){
                        return;
                    }

                } else {
                    if (whiteTurn) {
                        makeToast("illegal move for white player, try again");
                        validMove=false;
                        return;
                    } else {
                        makeToast("illegal move for black player, try again");
                        validMove=false;
                        return;
                    }
                }
            }
        }
    }
    private void finishGame(boolean blackWins) {
        Intent intent = new Intent(this, EndGame.class);
        EndGame.blackWins = blackWins;
        startActivity(intent);
        finish();
    }

}