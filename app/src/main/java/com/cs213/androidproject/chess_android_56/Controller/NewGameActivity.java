package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;


import com.cs213.androidproject.chess_android_56.Model.*;
import com.cs213.androidproject.chess_android_56.SubViews.*;

import com.cs213.androidproject.chess_android_56.R;

import java.lang.reflect.Field;


public class NewGameActivity extends AppCompatActivity {

    private static int start = -1;
    private static int end = -1;
    public static String startPos = "";
    public static String endPos = "";
    private static int pstart = -1;
    private static int pend = -1;
    private static String psp = "";
    private static String pep = "";
    public static Board b = new Board();
    public static Square whiteKing;
    public static Square blackKing;
    private boolean whiteTurn = true;
    private boolean blackCap = false;
    private boolean whiteCap = false;
    private boolean validMove = false;
    private boolean passantdraw = false;
    private boolean undoPassant=false;
    private boolean undoPromotion=false;
    private String prevPassant="";
    private boolean undid=false;
    private static boolean draw = false;
    private String passantLocation;
    private String gameLog = "";
    public static String promoteTo = "Q";
    public static ImageView changedImage;
    public Board prevBoard=new Board();
    private Piece caped ;
    String pieceSig="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        b.initBoard();
        prevBoard.initBoard();

        whiteKing = b.getSquare("e1");
        blackKing = b.getSquare("e8");
    }


    // TODO: what if promotion, what if castling? ADD MORE!!!
    public void undoClick(View v) {
        if(undid==false){
            if(undoPromotion){
                ImageView starting = (ImageView) findViewById(pend);
                ImageView ending = (ImageView) findViewById(pstart);
                starting.setImageResource(android.R.color.transparent); // make it transparent
                if(b.getSquare(pep).getPieceColor().equals("w")){
                    Pawn temp = new Pawn("white");
                    b.getSquare(pep).setPiece(temp);
                    b.getSquare(psp).setPiece(null);
                    ending.setImageResource(R.drawable.whitepawn);

                }
                else{
                    Pawn temp = new Pawn("black");
                    b.getSquare(psp).setPiece(temp);
                    b.getSquare(psp).setPiece(null);
                    ending.setImageResource(R.drawable.blackpawn);
                }
                undoPromotion=false;
            }
            else if(undoPassant){
                ImageView starting = (ImageView) findViewById(pend);
                ImageView ending = (ImageView) findViewById(pstart);
                Drawable draw = starting.getDrawable();
                starting.setImageResource(android.R.color.transparent); // make it transparent
                ending.setImageDrawable(draw);
                b.getSquare(pep).getPiece().move(pep,psp,b);
                Resources res = getResources();
                System.out.println(prevPassant);
                int id2 = res.getIdentifier(prevPassant, "id", NewGameActivity.this.getPackageName());
                ImageView op = (ImageView) findViewById(id2);
                if(b.getSquare(psp).getPieceColor().equals("w")) {
                    Pawn temp = new Pawn("black");
                    b.getSquare(pep).setPiece(temp);
                    op.setImageResource(R.drawable.blackpawn);
                }
                else{
                    Pawn temp = new Pawn("white");
                    b.getSquare(pep).setPiece(temp);
                    op.setImageResource(R.drawable.whitepawn);
                }
                undoPassant=false;
            }
            else if(pieceSig.equals("")){
                ImageView starting = (ImageView) findViewById(pend);
                ImageView ending = (ImageView) findViewById(pstart);
                Drawable draw = starting.getDrawable();
                starting.setImageResource(android.R.color.transparent); // make it transparent
                ending.setImageDrawable(draw);
                b.getSquare(pep).getPiece().move(pep,psp,b);
            }
            else {
                ImageView img = (ImageView) findViewById(pend);
                ImageView ending = (ImageView) findViewById(pstart);
                Drawable draw = img.getDrawable();
                Piece temp=null;
                if (pieceSig.charAt(1)=='R') {
                    if (pieceSig.charAt(0)=='w') {
                        img.setImageResource(R.drawable.whiterook);
                        temp=new Rook("white");
                    } else {
                        img.setImageResource(R.drawable.blackrook);
                        temp=new Rook("black");
                    }
                }
                else if(pieceSig.charAt(1)=='N'){
                    if(pieceSig.charAt(0)=='w'){
                        img.setImageResource(R.drawable.whiteknight);
                        temp=new Knight("white");
                    }
                    else{
                        img.setImageResource(R.drawable.blackknight);
                        temp=new Knight("black");
                    }
                }
                else if(pieceSig.charAt(1)=='B'){
                    if(pieceSig.charAt(0)=='w'){
                        img.setImageResource(R.drawable.whitebishop);
                        temp=new Bishop("white");
                    }
                    else{
                        img.setImageResource(R.drawable.blackbishop);
                        temp=new Bishop("black");
                    }
                }
                else if(pieceSig.charAt(1)=='Q'){
                    if(pieceSig.charAt(0)=='w'){
                        img.setImageResource(R.drawable.whitequeen);
                        temp=new Queen("white");
                    }
                    else{
                        img.setImageResource(R.drawable.blackqueen);
                        temp=new Queen("black");
                    }
                }
                else if(pieceSig.charAt(1)=='p'){
                    if(pieceSig.charAt(0)=='w'){
                        img.setImageResource(R.drawable.whitepawn);
                        temp=new Pawn("white");
                    }
                    else{
                        img.setImageResource(R.drawable.blackpawn);
                        temp=new Pawn("black");
                    }
                }
                b.getSquare(pep).getPiece().move(pep,psp,b);
                b.getSquare(pep).setPiece(temp);
                ending.setImageDrawable(draw);
            }


            if(!(b.getSquare(psp).getPiece()==null) && b.getSquare(psp).getPieceType().equals("p")){
                b.getSquare(psp).getPiece().hasMoved=false;
            }
            whiteTurn = !whiteTurn;
            gameLog = gameLog.substring(gameLog.length() - 6);
            undid=true;
        }

    }

    public void drawButton(View v) {
        AlertDialog.Builder builder;
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
        alertDialogBuilder.setTitle("Draw?");
        alertDialogBuilder.setMessage("Are you sure you want to end the game in a draw?");
        alertDialogBuilder
                .setMessage("End on a draw?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void resignButton(View v) {
        AlertDialog.Builder builder;
        final Intent intent = new Intent(this, MainActivity.class);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());

        if (whiteTurn) {
            alertDialogBuilder.setTitle("Are you sure you want to concede the game white player?");
        } else {
            alertDialogBuilder.setTitle("Are you sure you want to concede the game black player?");
        }
        alertDialogBuilder
                .setMessage("Concede?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
                if (passantdraw) {
                    Resources res = getResources();
                    int id2 = res.getIdentifier(passantLocation, "id", NewGameActivity.this.getPackageName());
                    ImageView op = (ImageView) findViewById(id2);
                    op.setImageResource(android.R.color.transparent);
                    passantdraw = false;
                    prevPassant=passantLocation;
                    passantLocation = "";
                    undoPassant=true;
                   // refresh(b);
                }
                if ((startPos.equals("e1")) || startPos.equals("e8")) {
                   // refresh(b);
                }

                pstart = start;
                pend = end;
                psp = startPos;
                pep = endPos;
                gameLog += psp + "," + pep + "|";

                System.out.println(gameLog);
            } else {
            }
        }
        Log.i("The onClick id is:", "" + v.getId());
    }

    private String getId(View view) {
        String id = view.getResources().getResourceName(view.getId());
        id = id.substring(id.length() - 2);
        Log.i("The ID in string is ", id);
        return id;
    }

    private void makeToast(String a) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, a, duration);
        toast.show();
    }

    private void game() {

        if (startPos.length() == 2 && endPos.length() == 2) {

            validMove = false;
            Pawn PassantTrack = null;
            while (!(whiteKing.getPiece().checkMate(b)) && !(blackKing.getPiece().checkMate(b))) {
                if (b.getSquare(startPos).getPiece() == null) {
                    validMove = false;
                    if (whiteTurn) {
                        makeToast("illegal move. please select a piece white player");
                        return;
                    } else {
                        makeToast("illegal move. please select a piece black player");
                        return;
                    }
                } else if (b.getSquare(startPos).getPieceColor().equals("b") && whiteTurn) {
                    makeToast("invalid move for white player, select your own piece");
                    validMove = false;
                    return;
                } else if (b.getSquare(startPos).getPieceColor().equals("w") && whiteTurn == false) {
                    makeToast("invalid move for black player, select your own piece");
                    validMove = false;
                    return;
                } else if (b.getSquare(startPos).getPiece().isValidMove(startPos, endPos, b)) {
                    validMove = true;
                    if (b.getSquare(startPos).getPieceType().equals("K")) {
                        if (b.getSquare(startPos).getPiece().isWhite() && whiteTurn) {
                            whiteKing = b.getSquare(endPos);
                        } else if (b.getSquare(startPos).getPiece().isWhite() == false && !(whiteTurn)) {
                            blackKing = b.getSquare(endPos);
                        }
                        //refresh(b);

                    } else if (b.getSquare(endPos).getPiece() != null && b.getSquare(endPos).getPieceType().equals("K")) {

                        if (b.getSquare(endPos).getPiece().isWhite() && !(whiteTurn)) {
                            whiteCap = true;
                        } else if (b.getSquare(endPos).getPiece().isWhite() == false && whiteTurn) {
                            blackCap = true;
                        }
                        //refresh(b);
                    }

                    if(b.getSquare(endPos).getPiece()!=null){
                        caped=b.getSquare(endPos).getPiece();
                        pieceSig=b.getSquare(endPos).toString();
                    }
                    else if(b.getSquare(endPos).getPiece()==null){
                        caped=null;
                        pieceSig="";
                    }


                    String type = b.getSquare(startPos).getPieceType();
                    Log.i("Position + Type", "End:" + endPos + "---" + type);
                    b.getSquare(startPos).getPiece().move(startPos, endPos, b);
                    if(undid==true){
                        undid=false;
                    }
                    prevBoard.printBoard();
                    // TODO: Watch Out for later bugs
                    if (type != null && type.equals("p") && (endPos.charAt(1) == '8' || endPos.charAt(1) == '1')) {
                        if (b.getSquare(endPos).getPieceType().equals("p")) {
                            if (b.getSquare(endPos).getPiece().getPas() != null) {
                                passantdraw = true;
                                passantLocation = b.getSquare(endPos).getPiece().getPas();
                                b.getSquare(endPos).getPiece().setPas();
                            } else if (PassantTrack == null) {
                                PassantTrack = (Pawn) b.getSquare(endPos).getPiece();
                            } else {
                                PassantTrack.setEnpassant();
                                PassantTrack = (Pawn) b.getSquare(endPos).getPiece();
                            }
                        } else if (PassantTrack != null && !(b.getSquare(endPos).getPieceType().equals("p"))) {
                            PassantTrack.setEnpassant();
                            PassantTrack = null;
                        }
                        if (whiteCap) {
                            gameLog+= startPos + endPos +'|';
                            finishGame(true,gameLog);
                            return;
                        } else if (blackCap) {
                            gameLog+= startPos + endPos +'|';
                            finishGame(false,gameLog);
                            return;
                        }

                        int ID = NewGameActivity.getResId(endPos, R.id.class);
                        changedImage = (ImageView) findViewById(ID);
                        undoPromotion=true;
                        promotion();

                    } else {

                        if (b.getSquare(endPos).getPieceType().equals("p")) {
                            if (b.getSquare(endPos).getPiece().getPas() != null) {
                                passantdraw = true;
                                passantLocation = b.getSquare(endPos).getPiece().getPas();
                                b.getSquare(endPos).getPiece().setPas();
                            } else if (PassantTrack == null) {
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
                        if (whiteCap) {
                            gameLog+= startPos + endPos +'|';
                            finishGame(true,gameLog);
                            return;
                        } else if (blackCap) {
                            gameLog+= startPos + endPos +'|';
                            finishGame(false,gameLog);
                            return;
                        }

                        if (whiteKing.getPiece().inCheck(b)) {
                            makeToast("white player in check.");
                        } else if (blackKing.getPiece().inCheck(b)) {
                            makeToast("black player in check.");
                        }

                        if (whiteKing.getPiece().checkMate(b)) {
                            makeToast("Black wins");
                            finishGame(true,gameLog);
                            return;
                        } else if (blackKing.getPiece().checkMate(b)) {
                            makeToast("White wins");
                            finishGame(false,gameLog);
                            return;
                        }
                    }
                    whiteTurn = !whiteTurn;

                    if (validMove = true) {
                        return;
                    }

                } else {
                    if (whiteTurn) {
                        makeToast("illegal move for white player, try again");
                        validMove = false;
                        return;
                    } else {
                        makeToast("illegal move for black player, try again");
                        validMove = false;
                        return;
                    }
                }
            }
        }
    }

    public void finishGame(boolean blackWins,String log) {
        Intent intent = new Intent(this, EndGame.class);
        EndGame.blackWins = blackWins;
        EndGame.log=log;
        startActivity(intent);
        //TODO: Uncomment it later, keep it for testing purpose
        //finish();
    }

    private void refresh(Board board) {
        Square[][] s = prevBoard.getBoard();
        prevBoard.printBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square temp = s[i][j];
                char file = 'a';
                file += j;
                int rank = 8 - i;
                String id = file + "" + rank;
                int ID = getResId(id, R.id.class);
                Log.i("Position:", "" + file + rank);
                ImageView img = (ImageView) findViewById(ID);

                if (temp.getPieceType() == null || temp.getPieceType().length() == 0) {
                    img.setImageResource(android.R.color.transparent);
                }
                else if (temp.getPieceType().equals("R")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whiterook);
                    } else {
                        img.setImageResource(R.drawable.blackrook);
                    }
                }
                else if(temp.getPieceType().equals("N")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whiteknight);
                    }
                    else{
                        img.setImageResource(R.drawable.blackknight);
                    }
                }
                else if(temp.getPieceType().equals("R")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whiterook);
                    }
                    else{
                        img.setImageResource(R.drawable.blackrook);
                    }
                }
                else if(temp.getPieceType().equals("B")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whitebishop);
                    }
                    else{
                        img.setImageResource(R.drawable.blackbishop);
                    }
                }
                else if(temp.getPieceType().equals("Q")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whitequeen);
                    }
                    else{
                        img.setImageResource(R.drawable.blackqueen);
                    }
                }
                else if(temp.getPieceType().equals("p")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whitepawn);
                    }
                    else{
                        img.setImageResource(R.drawable.blackpawn);
                    }
                }
            }
        }
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void promotion() {
        Promotion p = new Promotion();
        p.show(getFragmentManager(), "Promotion");
        Context context = getApplicationContext();
        CharSequence text = "Please make a choice or this pawn will be promote to a Queen.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void AIButton(View v) {
        Square[][] s = b.getBoard();
        String[] pair = null;

        if (whiteTurn){
            for (int i = 0; i < 8; i++) {
                if(pair != null) {

                    //TODO: make moves

                    return;
                }
                for (int j = 0; j < 8; j++) {
                    Square temp = s[i][j];
                    char file = 'a';
                    file += j;
                    int rank = 8 - i;
                    String id = file + "" + rank;

                    if (temp.getPieceType() == null || temp.getPieceType().length() == 0) {
                       continue;
                    }else if (!temp.getPieceColor().equals("w")){
                        continue;
                    }else{
                        //Log.i("The starting:", id);

                        pair = possibleEndPos(id);
                        if(pair != null){
                            break;
                        }
                    }
                }
            }
        }else{
            for (int i = 0; i < 8; i++) {
                if(pair != null) {

                    //TODO: make moves

                    return;
                }
                for (int j = 0; j < 8; j++) {
                    Square temp = s[i][j];
                    char file = 'a';
                    file += j;
                    int rank = 8 - i;
                    String id = file + "" + rank;

                    if (temp.getPieceType() == null || temp.getPieceType().length() == 0) {
                        continue;
                    }else if (!temp.getPieceColor().equals("b")){
                        continue;
                    }else{
                        pair = possibleEndPos(id);
                        if(pair != null){
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Return a pair of location, {start, end}
     * @param start where the piece starts moving
     * @return null if cannot find any valid move, else return the pair
     */
    @Nullable
    private String[] possibleEndPos(String start){
        String[] pair = new String[2];
        pair[0] = start;

        Square startPiece = b.getSquare(start);
        String[] listOfPos = ListOfPos.getPos();

        for(int i = 0; i < 64; i++){
            if(startPiece.getPiece().isValidMove(start, listOfPos[i], b)) {
                pair[1] = listOfPos[i];
                b.printBoard();
                Log.i("Position:", pair[0]+" "+pair[1]);
                return pair;
            }
        }
        return null;
    }
}