import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CheckerBoard extends JFrame {
	private Square[][] squares;
	private Player playerOne;
	private Player playerTwo;
	private int playerTurn;

	public CheckerBoard() {
		this.setTitle("Checkers by Brandon Fok");
		this.setBackground(Color.WHITE);
		this.setSize(828, 872);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(8, 8));
		
		squares = new Square[8][8];
		playerOne = new Player(1);
		playerTwo = new Player(2);
		playerTurn = 1;
		
		int color = -1; // -1 is black, 1 is red
		
		// instantiate and add Squares to CheckerBoard
		for(int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				squares[r][c] = new Square(r, c, color, this);
				this.add(squares[r][c]);
				color = -color;
			}
			color = -color;
		}
		
		// add pieces
		for(int r = 0; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				// if even row
				if((r % 2) == 0) {
					// if even column
					if((c % 2) == 0) {
						// if rows 0 to 2, pieces are for player one
						if(r >= 0 && r <= 2) {
							Piece p = new Piece(r, c, playerOne, this);
							playerOne.getPieces().add(p);
							squares[r][c].add(p);
							squares[r][c].setOccupied(true);
						// if rows 5 to 7, pieces are for player two
						} else if(r >= 5 && r <= 7) {
							Piece p = new Piece(r, c, playerTwo, this);
							playerTwo.getPieces().add(p);
							squares[r][c].add(p);
							squares[r][c].setOccupied(true);
						}
					}
				// if odd row
				} else {
					// if odd column
					if((c % 2) == 1) {
						if(r >= 0 && r <= 2) {
							Piece p = new Piece(r, c, playerOne, this);
							playerOne.getPieces().add(p);
							squares[r][c].add(p);
							squares[r][c].setOccupied(true);
						} else if(r >= 5 && r <= 7) {
							Piece p = new Piece(r, c, playerTwo, this);
							playerTwo.getPieces().add(p);
							squares[r][c].add(p);
							squares[r][c].setOccupied(true);
						}
					}
				}
			}
		}	
		
		this.setVisible(true);
	}
	/*
	public static void movePiece(int row, int col) {
		if(playerTurn == 1) {
			Piece pieceToMove = playerOne.pieceSelected();
			pieceToMove.deselect();
			
		}
	}
*/
	// move a piece to specified row and column
	public void moveToSquare(Piece piece, int newRow, int newCol) {
		if(validSquare(piece, newRow, newCol)) {
			squares[piece.getRow()][piece.getCol()].remove(piece); // remove piece from square
			squares[piece.getRow()][piece.getCol()].repaint();
			squares[piece.getRow()][piece.getCol()].setOccupied(false);
			squares[newRow][newCol].add(piece); // add that piece to square that was clicked on
			squares[newRow][newCol].setOccupied(true);
		}
	}
	
	// check to see if the move is valid
	public boolean validSquare(Piece piece, int newRow, int newCol) {
		int currRow = piece.getRow();
		int currCol = piece.getCol();
		if(newRow == (currRow - 1) || newRow == (currRow + 1)) {
			if(newCol == (currCol - 1) || newCol == (currCol + 1)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		CheckerBoard checkerBoard = new CheckerBoard();
	}
	
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	// switches playerTurn from 1 to 2 or 2 to 1
	public void switchPlayerTurn() {
		if(playerTurn == 1) {
			playerTurn = 2;
		} else if(playerTurn == 2) {
			playerTurn = 1;
		}
	}
	public Player getPlayerOne() {
		return playerOne;
	}
	public Player getPlayerTwo() {
		return playerTwo;
	}
}
