package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
    private static boolean draw = false;
    private String passantLocation;
    private String gameLog = "";
    public static String promoteTo = "Q";
    public static ImageView changedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        b.initBoard();
        whiteKing = b.getSquare("e1");
        blackKing = b.getSquare("e8");
    }


    // TODO: what if promotion, what if castling? ADD MORE!!!
    public void undoClick(View v) {
        b.getSquare(pep).getPiece().move(pep, psp, b);
        ImageView starting = (ImageView) findViewById(pend);
        ImageView ending = (ImageView) findViewById(pstart);
        Drawable draw = starting.getDrawable();
        starting.setImageResource(android.R.color.transparent); // make it transparent
        ending.setImageDrawable(draw);
        whiteTurn = !whiteTurn;
        gameLog = gameLog.substring(gameLog.length() - 6);
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
                    passantLocation = "";
                    refresh(b);
                }
                if ((startPos.equals("e1")) || startPos.equals("e8")) {
                    refresh(b);
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
                    makeToast("invalid move fow white player, select your own piece");
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
                        refresh(b);

                    } else if (b.getSquare(endPos).getPiece() != null && b.getSquare(endPos).getPieceType().equals("K")) {

                        if (b.getSquare(endPos).getPiece().isWhite() && !(whiteTurn)) {
                            whiteCap = true;
                        } else if (b.getSquare(endPos).getPiece().isWhite() == false && whiteTurn) {
                            blackCap = true;
                        }
                        refresh(b);
                    }

                    String type = b.getSquare(startPos).getPieceType();
                    Log.i("Position + Type", "End:" + endPos + "---" + type);
                    b.getSquare(startPos).getPiece().move(startPos, endPos, b);


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
        Square[][] s = board.getBoard();
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
                } else if (temp.getPieceType().equals("R")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whiterook);
                    } else {
                        img.setImageResource(R.drawable.blackrook);
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
}