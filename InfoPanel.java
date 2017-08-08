import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	private JLabel playerTurnLabel;
	private JButton showInstructionsBtn;
	private Checkers checkers;
	
	public InfoPanel(Checkers checkers) {
		this.checkers = checkers;
		this.setLayout(new GridLayout(1, 2));
		playerTurnLabel = new JLabel("<html>Current Turn: <font color='red'>RED</font></html>");
		showInstructionsBtn = new JButton("Instructions");
		addInstructionsBtnListener();
		
		this.add(playerTurnLabel);
		this.add(showInstructionsBtn);
	}
	
	public void addInstructionsBtnListener() {
		showInstructionsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(checkers,
					    "Click on a piece that you want to move. You can only click on pieces that have legal moves or jumps available.\n "
					    + "After clicking a piece, there will be \"ghost\" pieces that appear in the squares that you can move the selected piece to.\n"
					    + "Click on one of the ghost pieces, and the selected piece will be moved to that ghost piece.\n"
					    + "You win if the opposing player has no legal moves or jumps left, or the opposing player runs out of pieces.",
					    "Instructions",
					    JOptionPane.PLAIN_MESSAGE);
			}
			
		});
	}
	
	public void onSwitchPlayerTurn(int newPlayerTurn) {
		if(newPlayerTurn == -1) {
			playerTurnLabel.setText("<html>Current Turn: <font color='red'>RED</font></html>");
		} else {
			playerTurnLabel.setText("<html>Current Turn: <font color='gray'>GRAY</font></html>");
		}
	}
}
