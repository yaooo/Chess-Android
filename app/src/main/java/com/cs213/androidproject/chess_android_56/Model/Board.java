package com.cs213.androidproject.chess_android_56.Model;
/**
 * @author Sagar Patel
 * @author Yao Shi
 * @version 1.0
 */

public class Board {

	private Square[][] board;

	/**
	 * Constructor of the board
	 */
	public Board() {
		this.board = new Square[8][8];
	}

    /**
     * Initialize the board
     */
	public void initBoard() {
		int whiteTile=0;
		for(int i=0;i<2;i++) {
			for(int j=0;j<board[i].length;j++) {
				if((j==0 || j==7) && i==0 ) {
					Rook temp = new Rook("black");
					if(j==0){
						board[i][j]=new Square(temp,"white");
					}
					else {
						board[i][j]=new Square(temp,"black");
					}
				}
				else if((j==1 || j==6) && i==0){
					Knight temp = new Knight("black");
					if(j==1) {
						board[i][j]=new Square(temp,"black");
					}
					else {
						board[i][j]=new Square(temp,"white");
					}

				}
				else if((j==2 || j==5) && i==0){
					Bishop temp = new Bishop("black");
					if(j==2) {
						board[i][j]=new Square(temp,"white");
					}
					else {
						board[i][j]=new Square(temp,"black");
					}

				}
				else if(i==0 && (j==3)) {
					Queen temp = new Queen("black");
					board[i][j]=new Square(temp,"black");
				}
				else if( i==0 && j==4) {
					King temp =new King ("black");
					board[i][j]=new Square(temp,"white");
				}
				else if(i==1) {
					Pawn temp = new Pawn("black");
					if(j%2==0) {
						board[i][j]=new Square(temp,"black");
					}
					else {
						board[i][j]=new Square(temp,"white");
					}
				}
			}
		}
		for(int i=2;i<board.length-2;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(whiteTile==0){
					board[i][j]= new Square("white");
					whiteTile=1;
				}
				else {
					board[i][j]=new Square("black");
					whiteTile=0;
				}
			}
			if(whiteTile==0) {
				whiteTile=1;
			}
			else if(whiteTile==1) {
				whiteTile=0;
			}
		}
		for(int i=6;i<8;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(i==7 && (j==0 || j==7)) {
					Rook temp = new Rook ("white");
					if(j==0) {
						board[i][j]=new Square(temp,"black");
					}
					else {
						board[i][j]=new Square(temp,"white");
					}
				}
				else if(i==7 && (j==1 || j==6)) {
					Knight temp = new Knight("white");
					if(j==1) {
						board[i][j]=new Square(temp,"white");
					}
					else {
						board[i][j]=new Square(temp,"black");
					}
				}
				else if(i==7 && (j==2 ||j==5)) {
					Bishop temp = new Bishop("white");
					if(j==2) {
						board[i][j]=new Square(temp,"black");
					}
					else {
						board[i][j]=new Square(temp,"white");
					}
				}
				else if(i==7 && j==3) {
					Queen temp = new Queen("white");
					board[i][j]= new Square(temp,"white");
				}
				else if(i==7 && j==4) {
					King temp = new King("white");
					board[i][j]= new Square(temp,"black");
				}
				else if(i==6) {
					Pawn temp = new Pawn("white");
					if(j%2==0){
						board[i][j]=new Square(temp,"white");
					}
					else{
						board[i][j]=new Square(temp,"black");
					}
				}
			}
		}
	}

	public void initClearBoard() {
		int whiteTile=0;
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(whiteTile==0){
					board[i][j]= new Square("white");
					whiteTile=1;
				}
				else {
					board[i][j]=new Square("black");
					whiteTile=0;
				}
			}
			if(whiteTile==0) {
				whiteTile=1;
			}
			else if(whiteTile==1) {
				whiteTile=0;
			}
		}
	}
    /**

     * Print the board
     */
	public void printBoard(){

		int Vaxis=8;
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[i].length;j++) {
				if(board[i][j].getPieceType()==null){
					if(board[i][j].isSquareBlack()){
						System.out.print("## ");
					}
					else {
						System.out.print("   ");
					}
				}
				else {
					System.out.print(board[i][j].toString()+" ");
				}
			}
			System.out.print(Vaxis);
			Vaxis--;
			System.out.println("");
		}
		System.out.println(" a  b  c  d  e  f  g  h\n");
	}

    /**
     * Get the square based on the given position
     * @param pos The position of the piece
     * @return The Square based on the given position
     */
	public Square getSquare(String pos) {
		String temp=pos.charAt(1)+"";
		int k=99;
		int q=99;
		k=getX(pos.charAt(0));
		if(!(Character.isDigit(pos.charAt(1)))) {
			return null;
		}
		if(!(Character.isLetter(pos.charAt(0)))) {
			return null;
		}
		q=getY(Integer.parseInt(temp));
		if(k==-1 || q==-1) {
			return null;
		}
		return board[q][k];
		
	}

    /**
     * Get board
     * @return A 2D array with the type Square
     */
	public Square[][] getBoard(){
		return this.board;
	}

    /**
     * Set the board to the given board
     * @param b A 2D array with the type Square
     */
	public void setBoard(Square[][] b){
		this.board = b;
	}

    /**
     * Get the x coordinate
     * @param i A letter indicate the location
     * @return The x coordinate the piece
     */
	public int getX (char i) {
		int k=99;
		switch(i) {
		case 'a':
			k=0;
			break;
		case 'b':
			k=1;
			break;
		case 'c':
			k=2;
			break;
		case 'd':
			k=3;
			break;
		case 'e':
			k=4;
			break;
		case 'f':
			k=5;
			break;
		case 'g':
			k=6;
			break;
		case 'h':
			k=7;
			break;
		default:
			k=-1;
			break;
		}
			return k;
	
	}

    /**
     * Get the y coordinate
     * @param j A letter indicate the location
     * @return The y coordinate the piece
     */
	public int getY(int j) {
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