package com.cs213.androidproject.chess_android_56.Model;

import android.util.Log;

/**
 * Created by Yao on 12/11/2017.
 */

public class ListOfPos {
    private static String[] listOfPos = new String [64];
    private static boolean ini = false;

    private static void create(){
        char[] temp = {'a','b','c','d','e','f','g','h'};
        int t = 0;
        int counter = 0;

        for (int i = 0; i < 64; i++){
            counter ++;
            String pos = temp[t] + "" + (i%8 + 1);
            listOfPos[i] = pos;
            if(counter %8 == 0)
                t ++;
        }
    }

    public static String[] getPos(){
        if(!ini) create();

        Log.i("List of Pos:", listOfPos.toString());
        return listOfPos;
    }
}
