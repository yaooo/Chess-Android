package com.cs213.androidproject.chess_android_56.Model;

/**
 * Created by Yao on 12/11/2017.
 */

public class ListOfPos {
    private static String[] listOfPos = new String [64];
    private static boolean ini = false;

    private static void create(){
        char[] temp = {'a','b','c','d','e','f','g','h'};
        for (int i = 0; i < 64; i++){
            String pos = temp[i%8] + "" + (i%8);
            listOfPos[i] = pos;
        }
    }

    public static String[] getPos(){
        if(!ini) create();
        return listOfPos;
    }
}
