/**
 *
 */
package com.cs213.androidproject.chess_android_56.Model;

public abstract class Piece implements Cloneable {

    /**
     * If it is white
     */
    boolean isWhite;

    /**
     * The type of the piece
     */
    String type;

    /**
     * boolean that indicates if the piece has moved
     */
    boolean hasMoved;

    /**
     * The piece
     *
     * @param color The color
     */
    public Piece(String color) {
        isWhite = color.equals("white");
        type = null;
        hasMoved = false;
    }

    public String getType() {
        return type;
    }

    /**
     * isWhite()
     * Checks to see whether a piece is white, returns true if white
     *
     * @return boolean
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * hasMoved()
     * Checks to see whether a piece has moved previous, returns true if yes
     *
     * @return boolean
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * moved()
     * Updates hasMoved boolean as true to indicated a piece has moved
     *
     * @return void
     */
    public void moved() {
        hasMoved = true;
    }


    /**
     * isValidMove
     *
     * @param input
     * @param board
     * @return if the move is valid
     */
    public abstract boolean isValidMove(String start, String input, Board board);

    /**
     * @param start
     * @param end
     * @param board
     * @return the board after moving
     */
    public abstract void move(String start, String end, Board board);

    /**
     * canEnpassant()
     *
     * @return boolean
     * <p>
     * Returns boolean indicating whether this piece can enpassant
     */
    public boolean canEnpassant() {
        return false;
    }

    /**
     * getEnpassant()
     *
     * @return boolean
     * Returns boolean indicating whether this piece can be captured by enpassant
     */
    public boolean getEnpassant() {
        return false;
    }

    /**
     * setHasMoved
     *
     * @param moved Set true if moved
     */
    public void setHasMoved(boolean moved) {
        hasMoved = moved;
    }

    /**
     * inCheck(board)
     *
     * @param board The board
     * @return If in check
     */
    public boolean inCheck(Board board) {
        return false;
    }

    /**
     * If it is checkMate
     *
     * @param board The board
     * @return If checkMate exists
     */
    public boolean checkMate(Board board) {
        return false;
    }

    /**
     * getKingPos
     *
     * @return The king's position
     */
    public String getKingPos() {
        return "";
    }

    public boolean stalemate(Board board) {
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getPas() {
        return "";
    }

    public void setPas() {
        return;
    }
}
