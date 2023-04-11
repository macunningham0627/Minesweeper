package objects;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Window {
	private JFrame frame;
	private Controller controller;
	
	public Window(Controller cont)
	{
		initialize(cont.getGrid()[0].length, cont.getGrid().length, cont);
	}
	
	private void initialize(int columns, int rows, Controller cont)
	{
		this.controller = cont;
		
		frame = new JFrame();
		frame.setTitle("Minesweeper");
		frame.setSize(columns * Square.SQUARE_SIZE, 
				rows * Square.SQUARE_SIZE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		JButton flagButton = new JButton("Flagging is OFF");
		flagButton.setFocusable(false);
		flagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				controller.setFlagClick(!controller.getFlagClick());
				if (controller.getFlagClick())
				{
					flagButton.setText("Flagging is ON");
				} else
				{
					flagButton.setText("Flagging is OFF");
				}
			}
		});
		panel.add(flagButton);
		
		frame.add(panel, BorderLayout.NORTH);
		
		frame.setVisible(true);
	}
	
	public void addGrid(Square[][] squares)
	{
		int columns = squares[0].length;
		int rows = squares.length;
		
		JPanel panel = new JPanel(new GridLayout(rows, columns));
		for (int j = 0; j < rows; j++)
		{
			for (int i = 0; i < columns; i++)
			{
				panel.add(squares[j][i]);
			}
		}
		
		frame.add(panel, BorderLayout.CENTER);
	}
	
	public void dispose()
	{
		frame.dispose();
	}
}
