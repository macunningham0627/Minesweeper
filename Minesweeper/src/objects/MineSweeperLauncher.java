package objects;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperLauncher {
	public static void main(String[] args)
	{
		JFrame launcherWindow = new JFrame();
		launcherWindow.setTitle("Minesweeper");
		launcherWindow.setSize(800, 250);
		launcherWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		launcherWindow.setLocationRelativeTo(null);
		launcherWindow.setResizable(false);
		
		JButton startButton = new JButton("Start game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				startGame();
				launcherWindow.dispose();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		launcherWindow.add(buttonPanel, BorderLayout.SOUTH);
		
		String instructions = "<html><body>";
		instructions += "Welcome to Minesweeper!<br><br>";
		instructions += "The goal of this game is to "
				+ "clear out every safe square by clicking "
				+ "on them, while avoiding clicking on squares "
				+ "with mines underneath.<br>";
		instructions += "When a safe square is cleared out, "
				+ "it will show a number that indicates "
				+ "how many mines are in the neighboring "
				+ "squares.<br>";
		instructions += "Use this information to "
				+ "determine which squares to click on "
				+ "and which ones to avoid.<br><br>";
		instructions += "Clicking on the button at the "
				+ "top of the game window will "
				+ "toggle flagging.<br>";
		instructions += "When flagging is on, clicking "
				+ "on a square will not reveal it; "
				+ "instead, a \"!\" will be placed on "
				+ "the square.<br>";
		instructions += "You can use this to mark the "
				+ "locations where you think there are "
				+ "mines.<br><br>";
		instructions += "Click the button below to "
				+ "begin playing.";
		instructions += "</body></html>";
		JLabel label = new JLabel(instructions);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel labelPanel = new JPanel();
		labelPanel.add(label);
		launcherWindow.add(label, BorderLayout.CENTER);
		
		launcherWindow.setVisible(true);
	}
	
	public static void startGame()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				Controller controller = new Controller(25, 20, 99);
				Window window = new Window(controller);
				window.addGrid(controller.getGrid());
				controller.setWindow(window);
			}
		});
	}
}
