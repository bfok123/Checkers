import java.util.ArrayList;

public class Player {
	private ArrayList<Piece> pieces;
	private int kills;
	private int piecesRemaining;
	private int playerNum;
	
	public Player(int playerNum) {
		pieces = new ArrayList<Piece>();
		kills = 0;
		this.playerNum = playerNum;
		setPiecesRemaining(12);
	}
	
	public Piece removePieceAt(int row, int col) {
		for(Piece piece : pieces) {
			if(piece.getRow() == row && piece.getCol() == col) {
				pieces.remove(piece);
				return piece;
			}
		}
		return null;
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
	
	public int getPlayer() {
		return playerNum;
	}
	
	// check which piece is selected, return that piece
	public Piece pieceSelected() {
		for(Piece piece : pieces) {
			if(piece.isSelected()) {
				return piece;
			}
		}
		return null;
	}
	
	public int getKills() {
		return kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getPiecesRemaining() {
		return piecesRemaining;
	}

	public void setPiecesRemaining(int piecesRemaining) {
		this.piecesRemaining = piecesRemaining;
	}
}
