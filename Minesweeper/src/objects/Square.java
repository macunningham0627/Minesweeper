package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Square extends JButton {
	private static boolean DEBUG = false;
	
	private static final long serialVersionUID = -6456288261730796790L;
	
	public static final int SQUARE_SIZE = 50;
	public static final int MINE_NUMBER = 9;
	
	private Controller controller;
	private int number;
	private boolean flagged;
	private int x;
	private int y;
	
	public Square(Controller cont, int xLoc, int yLoc)
	{
		controller = cont;
		number = 0;
		flagged = false;
		x = xLoc;
		y = yLoc;
		
		setFocusable(false);
		setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if (controller.getFlagClick())
				{
					if (flagged) {setText("");}
					else {setText("!");}
					flagged = !flagged;
				} else
				{
					if (DEBUG)
					{
						System.out.println(controller);
					}
					
					if (controller.getFirstClick())
					{
						controller.setFirstClick(false);
						controller.rearrange(x, y);
						reveal();
						controller.decreaseRemainingSquares();
					} else
					{
						if (isMine())
						{
							setBackground(Color.red);
							controller.gameOver();
						} else 
						{
							reveal();
							controller.decreaseRemainingSquares();
						}
					}
				}
			}
		});
	}
	
	public void reveal()
	{
		if (isEnabled())
		{
			String reveal = isMine() ? "X" : Integer.toString(number);
			setText(reveal);
			setEnabled(false);
			if (number == 0)
			{
				controller.revealNeighbors(x, y);
			}
		}
	}
	
	public void setMine()
	{
		number += MINE_NUMBER;
	}
	
	public void removeMine()
	{
		number -= MINE_NUMBER;
	}
	
	public void incrementNumber(boolean b)
	{
		if (b)
		{
			number++;
		} else
		{
			number--;
		}
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public boolean isMine()
	{
		return number >= MINE_NUMBER;
	}
	
	public String toString()
	{
		return Integer.toString(number);
	}
}
