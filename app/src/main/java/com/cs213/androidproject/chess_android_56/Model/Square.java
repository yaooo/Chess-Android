/**
 *
 */
package com.cs213.androidproject.chess_android_56.Model;

public class Square implements Cloneable {

    /**
     * The piece that occupied this square
     */
	private Piece occupyingPiece;

    /**
     * Indicate if it si black
     */
	private boolean isBlack;

	/**
	 * Constructor of Square
	 * @param color Color of the piece, should be "w" or "b"
	 */
	public Square(String color) {
		this(null, color);
	}

	/**
	 * @param piece The piece
	 * @param color Color of the piece, should be "w" or "b"
	 */
	public Square(Piece piece, String color) {
		occupyingPiece = piece;
		isBlack = color.equals("black");
	}

	/**
	 * Constructor of Square
	 * @param piece The piece
	 * @param isBlack If the piece is black
	 */
	public Square(Piece piece, boolean isBlack){
		this.occupyingPiece = piece;
		this.isBlack = isBlack;
	}

	/**
	 * Sets the occupying piece of the square to the passed parameter
	 * @param piece The piece
	 * @return void
	 */
	public void setPiece(Piece piece) {
		occupyingPiece = piece;
	}

	/**
	 * getPiece()
	 * @return Piece
	 * Returns piece occupying the instance of the square on which this method is invoked
	 */
	public Piece getPiece()
	{
		return occupyingPiece;
	}

	
	/**
	 * getPieceType()
	 * @return String
	 * Returns class type of the piece occupying the instance of the square on which this method is invoked
	 */
	public String getPieceType() {
		if(occupyingPiece==null) {
			return null;
		}
		switch(occupyingPiece.type)
		{
			case "rook":
				return "R";
			case "knight":
				return "N";
			case "bishop":
				return "B";
			case "pawn":
				return "p";
			case "king":
				return "K";
			case "queen":
				return "Q";
			default:
				return "";
		}
	}

	/**
	 * getPieceColor()
	 * @return String
	 * Returns color of piece occupying the instance of the square on which this method is invoked
	 */
	public String getPieceColor()
	{

		if(occupyingPiece.isWhite)
			return "w";
		else
			return "b";
	}

	/**
	 * isSquareBlack()
	 * @return boolean
	 * Returns true if square is black
	 */
	public boolean isSquareBlack()
	{
		return isBlack;
	}

	/**
	 * toString()
	 * @return String
	 * Returns string of piece color and type of piece occupying the square
     * */
	public String toString() {
		return getPieceColor() + getPieceType();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}