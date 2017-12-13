package com.cs213.androidproject.chess_android_56.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;

import com.cs213.androidproject.chess_android_56.Model.Board;
import com.cs213.androidproject.chess_android_56.Model.Square;
import com.cs213.androidproject.chess_android_56.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;

public class EndGame extends AppCompatActivity {

    public static boolean blackWins = true;
    public static String log = "";
    public static boolean gameSaved = false;
    public String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Button back = (Button) findViewById(R.id.goBack);
        TextView title = (TextView) findViewById(R.id.whoWins);

        display();
        if (!blackWins) {
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

    public void saveGame(View v) throws IOException {
        if (!(gameSaved)) {
            final ArrayList<String> titles = getTitles();
            final Context context = getApplication().getApplicationContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title");

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();

                    try {
                        if (m_Text.length() < 3) {
                            makeToast("please enter a title with more than 3 characters");
                            return;
                        } else if (titles.contains(m_Text)) {
                            makeToast("title name taken please enter another title name");
                            return;
                        } else {
                            Context context = getApplicationContext();
                            CharSequence text = "Going back to Main Menu...";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            launchNewActivity();
                        }
                        Date d = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String df = sdf.format(d);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("gameHistory.txt", Context.MODE_APPEND));
                        log = m_Text + "+" + df + "~" + log;
                        log += ":|";
                        outputStreamWriter.write(log);
                        outputStreamWriter.close();
                        makeToast("game saved");
                        gameSaved = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        } else {
            makeToast("game already saved");

        }

    }

    private void launchNewActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        blackWins = true;
        startActivity(intent);
    }

    public void makeToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public ArrayList<String> getTitles() {
        Context context = this.getApplicationContext();
        String ret = "";
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
        ArrayList<String> g = new ArrayList<String>();
        String current = "";
        boolean title = true;
        for (int i = 0; i < ret.length(); i++) {
            char a = ret.charAt(i);
            if (a == '+' && title == true) {
                title = false;
                g.add(current);
                current = "";
            } else if (a == ':' && title == false) {
                i = i + 2;
                if (i >= ret.length()) {
                    break;
                }
                title = true;
                a = ret.charAt(i);
            }

            if (title == true) {
                current += a;
            }
        }

        return g;
    }

    private void display() {
        Board b = NewGameActivity.b;
        refresh(b);
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
                } else if (temp.getPieceType().equals("N")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whiteknight);
                    } else {
                        img.setImageResource(R.drawable.blackknight);
                    }
                } else if (temp.getPieceType().equals("R")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whiterook);
                    } else {
                        img.setImageResource(R.drawable.blackrook);
                    }
                } else if (temp.getPieceType().equals("B")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whitebishop);
                    } else {
                        img.setImageResource(R.drawable.blackbishop);
                    }
                } else if (temp.getPieceType().equals("Q")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whitequeen);
                    } else {
                        img.setImageResource(R.drawable.blackqueen);
                    }
                } else if (temp.getPieceType().equals("p")) {
                    if (temp.getPiece().isWhite()) {
                        img.setImageResource(R.drawable.whitepawn);
                    } else {
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


}
