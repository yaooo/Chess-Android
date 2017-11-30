package com.cs213.androidproject.chess_android_56.Model;

public class Queen extends Piece {

    /**
     * Constructor for Queen
     * @param color Color of the piece
     */
    public Queen(String color) {
        super(color);
        type="queen";
        // TODO Auto-generated constructor stub
    }
    String location;

    /**
     * If the move is valid
     * @param start The starting position
     * @param input The destination
     * @param board The board
     * @return True for valid, false for invalid
     */
    @Override
    public boolean isValidMove(String start,String input, Board board) {
    	int start_file = start.charAt(0) - 'a';
        int dest_file = input.charAt(0) - 'a';
        int start_rank= Movement.getRank(Integer.parseInt(start.charAt(1)+""));
        int dest_rank= Movement.getRank(Integer.parseInt(input.charAt(1)+""));
        Square[][] b = board.getBoard();  
        int file_diff=start_file-dest_file;
        int rank_diff=start_rank-dest_rank;
        
        if(this.isWhite() && (b[dest_rank][dest_file].getPiece()==null || b[dest_rank][dest_file].getPieceColor().equals("b"))) {
	        if(file_diff==0 && !(Movement.hasPiecesInBetween(start, input, board))) {
	        	return true;
	        }
	        else if(rank_diff==0 && !(Movement.hasPiecesInBetween(start, input, board))) {
	        	return true;
	        }
	        else if(Math.abs(rank_diff)==Math.abs(file_diff) && !(Movement.hasPiecesInBetween(start,input,board))) {
	        	return true;
	        }
        }
        else if((b[dest_rank][dest_file].getPiece()==null || b[dest_rank][dest_file].getPieceColor().equals("w"))) {
        	if(file_diff==0 && !(Movement.hasPiecesInBetween(start, input, board))) {
	        	return true;
	        }
	        else if(rank_diff==0 && !(Movement.hasPiecesInBetween(start, input, board))) {
	        	return true;
	        }
	        else if(Math.abs(rank_diff)==Math.abs(file_diff) && !(Movement.hasPiecesInBetween(start,input,board))) {
	        	return true;
	        }
        }
    	
        return false;
    }

    /**
     * Move the piece from the starting position to its destination
     * @param start The starting position
     * @param end The destination
     * @param board The board
     */
    @Override
    public void move(String start, String end, Board board) {
    	this.moved();
        board.getSquare(end).setPiece(this);
        board.getSquare(start).setPiece(null);
    }

    /**
     * If the Queen is surrounded
     * @param startX The starting X
     * @param startY The starting Y
     * @param endX The end X
     * @param endY The end Y
     * @return If the queen is surrounded
     */
    public boolean surroundCheck(int startX, int startY,int endX,int endY){
    	for(int i=startY-1;i<=startY+1;i++) {
    		for(int j=startX-1;j<=startX+1;j++) {
    			if((endX==j && endY==i) && (i!=startY && j!=startX)) {
    				return true;
    			}
    		}
    	}
    	
    	return false ;
    }
    
}