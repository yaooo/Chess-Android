package com.cs213.androidproject.chess_android_56.Model;

public class Movement {

    /**
     * Assume the move and the inputs are valid, decide if there are pieces in between two locations
     * Check vertically, horizontally, diagonally
     * @param start The starting position
     * @param destination   The finishing position
     * @param chessBoard    The chess board
     * @return If there are pieces in between two locations. If two locations are aborted, return true
     */
	public static boolean hasPiecesInBetween(String start, String destination, Board chessBoard) {
        int start_file = start.charAt(0) - 'a';
        int start_rank ;
        int dest_file = destination.charAt(0) - 'a';
        int dest_rank ;
        start_rank=getRank(Integer.parseInt(start.charAt(1)+""));
        dest_rank=getRank(Integer.parseInt(destination.charAt(1)+""));
        Square[][] board = chessBoard.getBoard();    //board is 8x8 matrix
        int diff_rank = dest_rank - start_rank;
        int diff_file = dest_file - start_file;

        if (start.equals(destination)) {
            //TODO: the piece does not move(invalid move)
        }
        // if the files are the same
        if (start_file == dest_file) {
            if (dest_rank - start_rank < 0) {
                for (int i = start_rank - 1; i > dest_rank; i--) {
                    if (board[i][start_file].getPieceType() != null) {
                        return true;
                    }
                }
            } else if (dest_rank - start_rank > 0) {
                for (int i = start_rank + 1; i < dest_rank; i++) {
                    if (board[i][start_file].getPieceType() != null) {
                        return true;
                    }
                }
            }
        }

        // if the ranks are the same
        if (dest_rank == start_rank) {
            if (dest_file - start_file < 0) {
                for (int i = start_file - 1; i > dest_file; i--) {
                    if (board[start_rank][i].getPieceType() != null)
                        return true;
                }
            } else if (dest_file - start_file > 0) {
                for (int i = start_file + 1; i < dest_file ; i++) {
                    if (board[start_rank][i].getPieceType() != null)
                        return true;
                }
            }
        }

        // check diagonally, assume diff_file = diff_rank
        if (Math.abs(start_file - dest_file) == Math.abs(start_rank - dest_rank)) {
            int distance = Math.abs(start_file - dest_file);

            if(start_file < dest_file && start_rank < dest_rank){
                for(int i = 1; i < distance; i++){
                    if(board[start_rank+i][start_file+i].getPieceType() != null)
                        return true;
                }
            }
            if(start_file > dest_file && start_rank > dest_rank){
                for(int i = 1; i < distance; i++){
                    if(board[start_rank-i][start_file-i].getPieceType() != null)
                        return true;
                }
            }
            if(start_file < dest_file && start_rank > dest_rank){
                for(int i = 1; i < distance; i++){
                    if(board[start_rank-i][start_file+i].getPieceType() != null)
                        return true;
                }
            }
            if(start_file > dest_file && start_rank < dest_rank){
                for(int i = 1; i < distance; i++){
                    if(board[start_rank+i][start_file-i].getPieceType() != null)
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * getRank()
     * @param j A integer
     * @return The rank coordinate based on the given integer
     */
	public static int getRank(int j) {
		int q=99;
		switch(j) {
		case 8:
			q=0;
			break;
		case 7:
			q=1;
			break;
		case 6:
			q=2;
			break;
		case 5:
			q=3;
			break;
		case 4:
			q=4;
			break;
		case 3:
			q=5;
			break;
		case 2:
			q=6;
			break;
		case 1:
			q=7;
			break;
		default:
			q=-1;
		}
		return q;
	}

}
