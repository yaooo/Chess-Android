package com.cs213.androidproject.chess_android_56.Model;

public class Bishop extends Piece {

    /**
     * Constructor for Bishop
     * @param color Color of the piece
     */
	public Bishop(String color) {
		super(color);
		type="bishop";
		// TODO Auto-generated constructor stub
	}

    /**
     * If the move is valid
     * @param start The starting position
     * @param input The destination
     * @param board The board
     * @return True for valid, false for invalid
     */
	@Override
	public boolean isValidMove(String start,String input, Board board) {
		/// TODO Auto-generated method stub
		int start_file = start.charAt(0) - 'a';
		int dest_file = input.charAt(0) - 'a';
		int start_rank= Movement.getRank(Integer.parseInt(start.charAt(1)+""));
		int dest_rank= Movement.getRank(Integer.parseInt(input.charAt(1)+""));

		Square[][] b = board.getBoard();    //board is 8x8 matrix
		int diff_rank = dest_rank - start_rank;
		int diff_file = dest_file - start_file;

		// Check the input parameters
		if(start.equals(input))
			return false;

		if(start_file < 0 || start_file > 7 || dest_file < 0 || dest_file > 7)
			return false;

		if(dest_rank < 0 || start_rank > 7 || dest_rank < 0 || dest_rank > 7)
			return false;

		// If it does not move straight
		if(Math.abs(diff_file) != Math.abs(diff_rank))
			return false;

		Piece dest = board.getSquare(input).getPiece();

		boolean bColor = this.isWhite();
		boolean hasPieceInBetween = Movement.hasPiecesInBetween(start,input,board);

		if(hasPieceInBetween)
			return false;
		if(dest != null) {
			boolean destColor = dest.isWhite();
			if (bColor == destColor)
				return false;
		}
		return true;
	}

    /**
     * Move the piece from the starting position to its destination
     * @param start The starting position
     * @param end The destination
     * @param board The board
     */
	@Override
	public void move(String start , String end, Board board) {
		// TODO Auto-generated method stub
		int start_file = start.charAt(0) - 'a';
		int dest_file = end.charAt(0) - 'a';
		int start_rank= Movement.getRank(Integer.parseInt(start.charAt(1)+""));
		int dest_rank= Movement.getRank(Integer.parseInt(end.charAt(1)+""));

		if(isValidMove(start,end, board)){
			this.moved();
			board.getSquare(end).setPiece(this);
			board.getSquare(start).setPiece(null);
		}else{
			System.out.println("Non-valid move");
		}
		
	}

	
}
