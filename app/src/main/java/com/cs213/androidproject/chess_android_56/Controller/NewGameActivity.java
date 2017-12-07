package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.res.Resources;
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

import com.cs213.androidproject.chess_android_56.Model.*;
import com.cs213.androidproject.chess_android_56.R;

import java.util.Scanner;

import static java.security.AccessController.getContext;

public class NewGameActivity extends AppCompatActivity {

    private static int start = -1;
    private static int end = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);



       /* // TODO for later usage
        GridLayout board = (GridLayout)findViewById(R.id.boardLayout);

        String temp = "a8";

        Resources res = getResources();
        int id = res.getIdentifier(temp, "id", NewGameActivity.this.getPackageName());

        findViewById(id);*/
    }

    public void ImageOnClick(View v) {
        int id = v.getId();
        if(start != -1 && end != -1){
            start = -1;
            end = -1;
        }

        if(start == -1){
            start = id;
        }else if(end == -1){
            end = id;
        }

        if(start != -1 && end != -1) {
            ImageView startPos = (ImageView)findViewById(start);
            ImageView endPos = (ImageView)findViewById(end);

            Drawable draw = startPos.getDrawable();
            startPos.setImageResource(android.R.color.transparent); // make it transparent

            endPos.setImageDrawable(draw);
        }
        Log.i("The onClick id is:", ""+v.getId());
    }



    private static boolean draw =false;

    private void game() {
        Board b = new Board();
        Scanner scn=new Scanner(System.in);
        b.initBoard();
        Square whiteKing=b.getSquare("e1");
        Square blackKing=b.getSquare("e8");
        b.printBoard();
        String input;
        boolean whiteTurn=true;
        boolean blackCap=false;
        boolean whiteCap=false;
        System.out.print("White player make your move:");
        input=scn.nextLine();
        String parts[]=input.split(" ");
        Pawn PassantTrack=null;
        while(!(parts[0].equals("resign")) &&  !(whiteKing.getPiece().checkMate(b)) &&  !(blackKing.getPiece().checkMate(b))){
            if(whiteKing.getPiece().stalemate(b) && whiteTurn) {
                draw=true;
                break;
            }
            else if(blackKing.getPiece().stalemate(b) && !(whiteTurn)) {
                draw=true;
                break;
            }

            if(parts.length==3 && parts[2].equals("draw?")) {
                draw=true;
            }

            else if(draw && parts.length==1 && parts[0].equals("draw")) {
                break;
            }else{
                draw = false;
            }
            if(!draw && parts.length!=2) {
                System.out.println("illegal move,try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }
            else if(b.getSquare(parts[0])==null || b.getSquare(parts[1])==null) {
                System.out.println("illegal move,try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }
            else if(b.getSquare(parts[0]).getPiece()==null) {
                System.out.println("illegal move try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }
            else if(b.getSquare(parts[0]).getPieceColor().equals("w") && whiteTurn==false) {
                System.out.println("illegal move,try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }
            else if(b.getSquare(parts[0]).getPieceColor().equals("b")&& whiteTurn==true) {
                System.out.println("illegal move,try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }
            else if(b.getSquare(parts[0]).getPiece().isValidMove(parts[0], parts[1], b)) {
                b.getSquare(parts[0]).getPiece().move(parts[0],parts[1],b);
                if (b.getSquare(parts[1]).getPieceType().equals("K")){
                    if(b.getSquare(parts[1]).getPiece().isWhite()==true && whiteTurn) {
                        whiteKing=b.getSquare(parts[1]);
                    }
                    else if(b.getSquare(parts[1]).getPiece().isWhite()==false && !(whiteTurn)) {
                        blackKing=b.getSquare(parts[1]);
                    }
                    else if(b.getSquare(parts[1]).getPiece().isWhite()==false && whiteTurn){
                        whiteCap=true;
                        break;
                    }
                    else if(b.getSquare(parts[1]).getPiece().isWhite()==true && !(whiteTurn)){
                        blackCap=true;
                        break;
                    }

                }
                if(b.getSquare(parts[1]).getPieceType().equals("p")){
                    if(PassantTrack==null) {
                        PassantTrack=(Pawn)b.getSquare(parts[1]).getPiece();
                    }
                    else{
                        PassantTrack.setEnpassant();
                        PassantTrack=(Pawn)b.getSquare(parts[1]).getPiece();
                    }
                }
                else if(PassantTrack!=null && !(b.getSquare(parts[1]).getPieceType().equals("p"))){
                    PassantTrack.setEnpassant();
                    PassantTrack=null;
                }
                System.out.println();
                b.printBoard();
                if(whiteKing.getPiece().inCheck(b)) {
                    System.out.println("white player in check.");
                }
                if(blackKing.getPiece().inCheck(b)) {
                    System.out.println("black player in check.");
                }
                whiteTurn=!whiteTurn;

                if(whiteTurn) {
                    System.out.print("White player make your move:");
                }else {
                    System.out.print("Black player make your move:");
                }
            }
            else {
                System.out.println("illegal move,try again");
                if(whiteTurn){
                    System.out.print("White player make your move:");
                }else{
                    System.out.print("Black player make your move:");
                }
            }


            input=scn.nextLine();
            parts=input.split(" ");
        }
        if(whiteCap==true) {
            System.out.println("White wins");
        }
        else if(blackCap==true) {
            System.out.println("Black wins");
        }
        else if(draw==true) {
            System.out.println("draw");
        }
        else if(parts[0].equals("resign")){
            if(whiteTurn)
                System.out.println("Black wins");
            else
                System.out.println("White wins");
        }
        else if(whiteKing.getPiece().checkMate(b)) {
            System.out.println("Black wins");
        }
        else if(blackKing.getPiece().checkMate(b)) {
            System.out.println("White wins");
        }

    }




}
