import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Piece extends JButton implements ActionListener{
	private int row;
	private int col;
	private Player player;
	private boolean selected;
	private BufferedImage outlinePiece;
	private CheckerBoard checkerBoard;
	
	public Piece(int r, int c, Player p, CheckerBoard checkerBoard) {
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setPreferredSize(new Dimension(80, 80));
		this.addActionListener(this);
		outlinePiece = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
		setRow(r);
		setCol(c);
		player = p;
		this.checkerBoard = checkerBoard;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(outlinePiece, 0, 0, this);
		
		if(player.getPlayer() == 1) {
			g.setColor(Color.WHITE);
		} else if(player.getPlayer() == 2) {
			g.setColor(Color.GREEN);
		}
		g.fillOval(5, 5, 73, 73);
		if(selected) {
			g.drawImage(outlinePiece, 0, 0, this);
		}
	}

	// when a piece is clicked on
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Graphics gBuff = outlinePiece.createGraphics();
		
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
		if(selected && checkerBoard.validSquare(this, newSquare.getRow(), newSquare.getCol())) {
			checkerBoard.moveToSquare(this, newSquare.getRow(), newSquare.getCol());
			this.row = newSquare.getRow();
			this.col = newSquare.getCol();
			deselect();
			checkerBoard.switchPlayerTurn();
		}
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


}
