import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

public class Piece extends JButton implements ActionListener{
	private int row;
	private int col;
	private Player player;
	private boolean selected;
	private BufferedImage selectedPieceImage;
	private CheckerBoard checkerBoard;
	private boolean isKing;
	
	public Piece(int r, int c, Player p, CheckerBoard checkerBoard) {
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setPreferredSize(new Dimension(80, 80));
		this.addActionListener(this);
		selectedPieceImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
		setRow(r);
		setCol(c);
		player = p;
		this.checkerBoard = checkerBoard;
		isKing = false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(selectedPieceImage, 0, 0, this);
		
		if(player.getPlayer() == 1) {
			g.setColor(Color.WHITE);
		} else if(player.getPlayer() == 2) {
			g.setColor(Color.GREEN);
		}
		g.fillOval(5, 5, 73, 73);
		
		if(selected) {
			g.drawImage(selectedPieceImage, 0, 0, this);
		}
		
		// draw a "K" on the piece if king
		if(isKing) {
			g.setColor(Color.BLACK);
			g.drawLine(25, 20, 25, 60);
			g.drawLine(26, 20, 26, 60);
			g.drawLine(26, 40, 55, 20);
			g.drawLine(26, 41, 55, 21);
			g.drawLine(26, 40, 55, 59);
			g.drawLine(26, 41, 55, 60);
		}
	}

	// when a piece is clicked on
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Graphics gBuff = selectedPieceImage.createGraphics();
		
		// if it is the piece's player's turn
		if(checkerBoard.getPlayerTurn() == player.getPlayer()) {
			// if a piece has already been selected, deselect it
			if(player.pieceSelected() != null) {
				player.pieceSelected().deselect();
			}
			gBuff.setColor(Color.YELLOW);
			gBuff.fillOval(5, 5, 73, 73);
			repaint();
			selected = true;
		}
	}
	
	// move to given square
	public void move(Square newSquare) {
		int newRow = newSquare.getRow();
		int newCol = newSquare.getCol();
		
		if(selected) {
			if(checkerBoard.validMoveSquare(this, newRow, newCol)) {
				checkerBoard.moveToSquare(this, newRow, newCol);
				this.row = newRow;
				this.col = newCol;
				deselect();
				becomeKing(newSquare);
				checkerBoard.switchPlayerTurn();
			} else if(checkerBoard.validCaptureSquare(this, newRow, newCol)) {
				checkerBoard.capture(this, newRow, newCol);
				this.row = newRow;
				this.col = newCol;
				deselect();
				becomeKing(newSquare);
				checkerBoard.switchPlayerTurn();
			}
		}
	}
	
	// change to king if piece reaches opposite end of board (given square that player is moving to)
	public void becomeKing(Square newSquare) {
		// if piece belongs to player one, piece has to be at row 7 to become king
		if(player.getPlayer() == 1) {
			if(newSquare.getRow() == 7) {
				isKing = true;
			}
		// if piece belongs to player two, piece has to be at row 0 to become king
		} else if(player.getPlayer() == 2) {
			if(newSquare.getRow() == 0) {
				isKing = true;
			}
		}
	}
	
	public boolean getIsKing() {
		return isKing;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void deselect() {
		selected = false;
		repaint();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public Player getPlayer() {
		return player;
	}


}
