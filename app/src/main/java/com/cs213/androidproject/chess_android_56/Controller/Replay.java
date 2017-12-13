package com.cs213.androidproject.chess_android_56.Controller;

import android.os.Bundle;

import com.cs213.androidproject.chess_android_56.Model.Board;
import com.cs213.androidproject.chess_android_56.Model.Square;
import com.cs213.androidproject.chess_android_56.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class Replay extends AppCompatActivity {
    public static String gameName;
    public ArrayList<String> moves = new ArrayList<String>();
    Board b= new Board();
    private int ia=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);
        ia=0;

        System.out.println(gameName);
        String ret="";
        String game = "";
        Button btn = (Button)findViewById(R.id.next);
        btn.setEnabled(true);
        Context context = this.getApplicationContext();
        System.out.println(ia);
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
        game=getGame(ret);
        moves=getMoves(game);
        System.out.println(game);
        for(int i=0;i<moves.size();i++){
            System.out.println(moves.get(i));
        }
        b.initBoard();
        System.out.println(moves.get(0));

    }


    public String getGame(String s){
        ArrayList<String> L=new ArrayList<>();
        int begin=0;
        int movebegin;
        String q="";
        if(s.contains(gameName)){
            begin=s.indexOf(gameName);
           // System.out.println(begin);
        }
        movebegin=s.indexOf('~',begin);
        boolean parse=false;
        for(int i=movebegin+1;i<s.length();i++){
            if(s.charAt(i)==':'){
                break ;
            }
            q+=s.charAt(i);

        }
        //System.out.println(q);
        return q;
    }

    public ArrayList<String> getMoves(String s){
        ArrayList<String>m=new ArrayList<>();
        String move="" ;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)=='|'){
               // System.out.println(move);
                m.add(move);
                move="";
            }
            else {
                move += s.charAt(i);
            }
        }
        return m ;
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
                //Log.i("Position:", "" + file + rank);
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
                else if(temp.getPieceType().equals("K")){
                    if(temp.getPiece().isWhite()){
                        img.setImageResource(R.drawable.whiteking);
                    }
                    else{
                        img.setImageResource(R.drawable.blackking);
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

    public void onClickNextButton(View v){
        if(ia==moves.size()-1){
            Button btn = (Button)findViewById(R.id.next);
            btn.setEnabled(false);
            CharSequence text = "End of the game...";
            int duration = Toast.LENGTH_SHORT;
            Context context = this.getApplicationContext();
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
        System.out.println(ia);
        String parts[]=moves.get(ia).split(",");
        System.out.println(parts[0]+","+parts[1]);
        b.getSquare(parts[0]).getPiece().move(parts[0],parts[1],b);
        b.printBoard();
        ia++;
        refresh(b);
    }
}
