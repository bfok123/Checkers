import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Checkers extends JFrame {
	private Square[][] squares;
	private Player playerTop;
	private Player playerBottom;
	private int playerTurn;
	private JPanel board;
	private InfoPanel topPanel;
	
	public Checkers() {
		this.setTitle("Checkers V.2");
		this.setSize(490, 520);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		board = new JPanel();
		board.setLayout(new GridLayout(8, 8));
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.9;
		c.gridy = 0;
		this.add(board, c);
		
		topPanel = new InfoPanel(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 1;
		c.weighty = 0.1;
		this.add(topPanel, c);
		
		playerTurn = -1;
		playerTop = new Player(1);
		playerBottom = new Player(-1);
		squares = new Square[8][8];
		addSquaresToBoard();
		addPiecesToSquares();
		
		this.setVisible(true);
	}
	
	// removes a specified piece from a specified player
	public void playerRemovePiece(int playerNum, Piece p) {
		if(playerNum == 1) {
			playerTop.removePiece(p);
		}
		else if(playerNum == -1) {
			playerBottom.removePiece(p);
		}
	}
	
	// called right after a piece is moved
	public void onMove() {
		if(playerTop.hasNoPieces() || playerTop.hasNoMove()) {
			onWin(playerBottom);
			return; // stop the game
		}
		else if(playerBottom.hasNoPieces() || playerBottom.hasNoMove()) {
			onWin(playerTop);
			return; // stop the game
		}
		
		playerTop.updateSelectablePieces();
		playerBottom.updateSelectablePieces();
	}
	
	// called if any player has no remaining pieces
	public void onWin(Player winningPlayer) {
		// if top player wins
		if(winningPlayer.getPlayerNum() == 1) JOptionPane.showMessageDialog(this, "Gray Wins!");
		// if bottom player wins
		else if(winningPlayer.getPlayerNum() == -1) JOptionPane.showMessageDialog(this, "Red Wins!");
		System.exit(0); // close the game
	}
	
	// switches player turn (1 to -1 or vice versa)
	public void switchPlayerTurn() {
		playerTurn = -playerTurn;
		topPanel.onSwitchPlayerTurn(playerTurn);
	}
	
	// returns playerTurn
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	// if the current player (whoever's turn it is) has a selected piece, returns that piece
	public Piece getSelectedPieceFromCurrPlayer() {
		if(playerTurn == -1) {
			if(playerBottom.getSelectedPiece() != null) {
				return playerBottom.getSelectedPiece();
			}
		}
		else if(playerTurn == 1) {
			if(playerTop.getSelectedPiece() != null) {
				return playerTop.getSelectedPiece();
			}
		}
		
		return null;
	}
	
	// returns the square at specified row and column
	public Square getSquareAt(int row, int col) {
		return squares[row][col];
	}
	
	// adds Squares (JPanels) to the checker board (JFrame with GridLayout)
	public void addSquaresToBoard() {
		for(int r = 0; r < squares.length; r++) {
			for(int c = 0; c < squares[r].length; c++) {
				Square square = new Square(r, c);
				squares[r][c] = square;
				board.add(square);
			}
		}
	}
	
	// adds Pieces (JButtons) to Squares (JPanels) at start of game
	public void addPiecesToSquares() {
		// top player's pieces
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 8; c++) {
				if(r % 2 == 0 && c % 2 == 1) {
					Piece piece = new Piece(r, c, playerTop, this);
					squares[r][c].addPiece(piece);
					playerTop.addPiece(piece);
				}
				else if(r % 2 == 1 && c % 2 == 0) {
					Piece piece = new Piece(r, c, playerTop, this);
					squares[r][c].addPiece(piece);
					playerTop.addPiece(piece);
				}
			}
		}
		
		// bottom player's pieces
		for(int r = 5; r < 8; r++) {
			for(int c = 0; c < 8; c++) {
				if(r % 2 == 1 && c % 2 == 0) {
					Piece piece = new Piece(r, c, playerBottom, this);
					squares[r][c].addPiece(piece);
					playerBottom.addPiece(piece);
				}
				else if(r % 2 == 0 && c % 2 == 1) {
					Piece piece = new Piece(r, c, playerBottom, this);
					squares[r][c].addPiece(piece);
					playerBottom.addPiece(piece);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Checkers();
	}

}
