package com.cs213.androidproject.chess_android_56.SubViews;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs213.androidproject.chess_android_56.Controller.EndGame;
import com.cs213.androidproject.chess_android_56.Controller.NewGameActivity;
import com.cs213.androidproject.chess_android_56.Model.Bishop;
import com.cs213.androidproject.chess_android_56.Model.Board;
import com.cs213.androidproject.chess_android_56.Model.Knight;
import com.cs213.androidproject.chess_android_56.Model.Queen;
import com.cs213.androidproject.chess_android_56.Model.Rook;
import com.cs213.androidproject.chess_android_56.Model.Square;
import com.cs213.androidproject.chess_android_56.R;

public class Promotion extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        NewGameActivity.promoteTo = "Q";
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] list = {"Queen", "Bishop", "Knight", "Rook"};

        builder.setSingleChoiceItems(list, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        NewGameActivity.promoteTo = "Q";
                        break;
                    case 1:
                        NewGameActivity.promoteTo = "B";
                        break;
                    case 2:
                        NewGameActivity.promoteTo = "N";
                        break;
                    case 3:
                        NewGameActivity.promoteTo = "R";
                        break;
                }
            }
        });
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String endPos = NewGameActivity.endPos;
                Board b = NewGameActivity.b;
                ImageView img = NewGameActivity.changedImage;

                if (endPos.charAt(1) == '8') {
                    Log.d("Promote to:", "  " + NewGameActivity.promoteTo);
                    Log.d("End Pos", endPos);

                    switch (NewGameActivity.promoteTo.charAt(0)) {
                        case 'Q':
                            b.getSquare(endPos).setPiece(new Queen("white"));
                            img.setImageResource(R.drawable.whitequeen);
                            break;
                        case 'B':
                            b.getSquare(endPos).setPiece(new Bishop("white"));
                            img.setImageResource(R.drawable.whitebishop);

                            break;
                        case 'N':
                            b.getSquare(endPos).setPiece(new Knight("white"));
                            img.setImageResource(R.drawable.whiteknight);

                            break;
                        case 'R':
                            Log.d("here?", "WHITE ROOK");
                            b.getSquare(endPos).setPiece(new Rook("white"));
                            img.setImageResource(R.drawable.whiterook);
                            break;
                        default:
                            break;
                    }
                } else if (endPos.charAt(1) == '1') {
                    switch (NewGameActivity.promoteTo.charAt(0)) {
                        case 'Q':
                            b.getSquare(endPos).setPiece(new Queen("white"));
                            img.setImageResource(R.drawable.whitequeen);
                            break;
                        case 'B':
                            b.getSquare(endPos).setPiece(new Bishop("white"));
                            img.setImageResource(R.drawable.whitebishop);
                            break;
                        case 'N':
                            b.getSquare(endPos).setPiece(new Knight("white"));
                            img.setImageResource(R.drawable.whiteknight);
                            break;
                        case 'R':
                            b.getSquare(endPos).setPiece(new Rook("white"));
                            img.setImageResource(R.drawable.whiterook);
                            break;
                        default:
                            break;
                    }
                }
                Square whiteKing = NewGameActivity.whiteKing;
                Square blackKing = NewGameActivity.blackKing;
                if (whiteKing.getPiece().inCheck(b)) {
                    makeToast("white player in check.");
                } else if (blackKing.getPiece().inCheck(b)) {
                    makeToast("black player in check.");
                }

                if (whiteKing.getPiece().checkMate(b)) {
                    makeToast("Black wins");
                    finishGame(true);

                } else if (blackKing.getPiece().checkMate(b)) {
                    makeToast("White wins");
                    finishGame(false);
                }
            }
        });
        builder.setTitle("Promotion");
        builder.setCancelable(false);
        return builder.create();
    }

    private void makeToast(String a) {
        Context c = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(c, a, duration);
        toast.show();
    }

    private void finishGame(boolean blackWins) {
        Intent intent = new Intent(getContext(), EndGame.class);
        EndGame.blackWins = blackWins;
        startActivity(intent);
        //TODO: Uncomment it later, keep it for testing purpose
        //finish();
    }

}
