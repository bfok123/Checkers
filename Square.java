import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener{
	private int row;
	private int col;
	private boolean occupied;
	private int color;
	private CheckerBoard checkerBoard;
	
	public Square(int r, int c, int color, CheckerBoard checkerBoard) {
		if(color == -1) {
			this.setBackground(Color.BLACK);
		} else if(color == 1) {
			this.setBackground(Color.RED);
		}
		this.setPreferredSize(new Dimension(100, 100));
		this.row = r;
		this.col = c;
		occupied = false;
		this.checkerBoard = checkerBoard;
		this.addMouseListener(this);
	}
	
	public Piece getPiece() {
		if(occupied) {
			return (Piece)this.getComponent(0);
		}
		return null;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return occupied;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Player playerOne = checkerBoard.getPlayerOne();
		Player playerTwo = checkerBoard.getPlayerTwo();
		
		// if there is no piece in this square
		if(!occupied) {
			if(checkerBoard.getPlayerTurn() == 1 && playerOne.pieceSelected() != null) {
				Piece piece = playerOne.pieceSelected();
				piece.move(this);
			} else if(checkerBoard.getPlayerTurn() == 2 && playerTwo.pieceSelected() != null) {
				Piece piece = playerTwo.pieceSelected();
				piece.move(this);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
