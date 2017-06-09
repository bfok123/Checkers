import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;

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

	// move a piece to specified row and column
	public void moveToSquare(Piece piece, int newRow, int newCol) {
		squares[piece.getRow()][piece.getCol()].remove(piece); // remove piece from square
		squares[piece.getRow()][piece.getCol()].repaint();
		squares[piece.getRow()][piece.getCol()].setOccupied(false);
		squares[newRow][newCol].add(piece); // add that piece to square that was clicked on
		squares[newRow][newCol].setOccupied(true);
	}
	
	// move a piece to specified row and column and capture enemy that is jumped over
	public void capture(Piece piece, int newRow, int newCol) {
		int currRow = piece.getRow();
		int currCol = piece.getCol();
		int enemyRow = (currRow + newRow) / 2;
		int enemyCol = (currCol + newCol) / 2;
		Player enemyPlayer = getEnemyPlayer();
		
		moveToSquare(piece, newRow, newCol);
		
		Piece enemyPiece = enemyPlayer.removePieceAt(enemyRow, enemyCol); // remove enemy piece that is jumped over from player array list thing
		squares[enemyRow][enemyCol].remove(enemyPiece); // remove enemy piece from square
		squares[enemyRow][enemyCol].repaint();
		squares[enemyRow][enemyCol].setOccupied(false);
		// change player's data
	}
	
	public boolean validCaptureSquare(Piece piece, int newRow, int newCol) {
		int currRow = piece.getRow();
		int currCol = piece.getCol();
		int enemyRow = (currRow + newRow) / 2;
		int enemyCol = (currCol + newCol) / 2;
		Square enemySquare = squares[enemyRow][enemyCol];
		
		// if square that enemy is in is occupied and it is occupied by the opposite player and the square that the player is jumping to is unoccupied
		if(enemySquare.getOccupied() == true && enemySquare.getPiece().getPlayer() != piece.getPlayer() && !squares[newRow][newCol].getOccupied()) {
			if(newCol == (currCol - 2) || newCol == (currCol + 2)) {
				// if piece is king, it can capture backwards or forwards diagonally
				if(piece.getIsKing()) {
					if(newRow == (currRow + 2) || newRow == (currRow - 2)) {
						return true;
					}
				}
				// if piece is player one's, it can capture downwards diagonally
				if(piece.getPlayer() == playerOne && newRow == (currRow + 2)) {
					return true;
				// if piece is player two's, it can capture upwards diagonally
				} else if(piece.getPlayer() == playerTwo && newRow == (currRow - 2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// check to see if the move is valid (not captures)
	public boolean validMoveSquare(Piece piece, int newRow, int newCol) {
		int currRow = piece.getRow();
		int currCol = piece.getCol();
		
		if(newCol == (currCol - 1) || newCol == (currCol + 1)) {
			// if piece is king, it can move backwards or forwards diagonally
			if(piece.getIsKing()) {
				if(newRow == (currRow + 1) || newRow == (currRow - 1)) {
					return true;
				}
			}
			// if piece is player one's, it can move downwards diagonally
			if(piece.getPlayer() == playerOne && newRow == (currRow + 1)) {
				return true;
			// if piece is player two's, it can move upwards diagonally
			} else if(piece.getPlayer() == playerTwo && newRow == (currRow - 1)) {
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
	
	public Player getEnemyPlayer() {
		if(playerTurn == 1) {
			return playerTwo;
		} else {
			return playerOne;
		}
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
