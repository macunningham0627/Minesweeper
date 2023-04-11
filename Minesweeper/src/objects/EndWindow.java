package objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EndWindow {
	private JFrame frame;
	private Controller controller;
	
	public EndWindow(Controller cont)
	{
		controller = cont;
		frame = new JFrame();
		frame.setSize(100, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JButton restartButton = new JButton("Play again?");
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				MineSweeperLauncher.startGame();
				controller.closeWindow();
				frame.dispose();
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(restartButton);
		frame.add(panel, BorderLayout.CENTER);
	}
	
	public void gameEnd(boolean win)
	{
		JPanel panel = new JPanel();
		panel.add(new JLabel(win ? "You win!" : "You lose!"));
		frame.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
