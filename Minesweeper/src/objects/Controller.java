package objects;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Controller {
	private static boolean DEBUG = true;
	
	private Square[][] grid;
	private Window window;
	private int columns;
	private int rows;
	private int remainingSquares;
	private boolean flagClick;
	private boolean firstClick;
	private List<Integer> reserveMineLocations;
	
	
	public Controller(int columns, int rows, int numMines)
	{
		this.columns = columns;
		this.rows = rows;
		remainingSquares = rows * columns - numMines;
		flagClick = false;
		firstClick = true;
		reserveMineLocations = new ArrayList<Integer>();
		
		grid = new Square[rows][columns];
		
		placeMines(columns, rows, numMines);
		
		if (DEBUG)
		{
			System.out.println(this);
		}
	}
	
	private void placeMines(int columns, int rows, int numMines)
	{
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < columns * rows; i++)
		{
			numbers.add(i);
			int x = i % columns;
			int y = i / columns;
			grid[y][x] = new Square(this, x, y);
		}
		
		Collections.shuffle(numbers);
		List<Integer> mineLocations = numbers.subList(0, numMines);
		reserveMineLocations = numbers.subList(numMines, numbers.size());
		
		if (DEBUG)
		{
			System.out.println(mineLocations);
			System.out.println(reserveMineLocations);
		}
		
		for (int i = 0; i < mineLocations.size(); i++)
		{
			int mineLocation = mineLocations.get(i);
			int x = mineLocation % columns;
			int y = mineLocation / columns;
			setMine(grid[y][x], x, y);
		}
	}
	
	private void setMine(Square square, int x, int y)
	{
		square.setMine();
		
		//increment northern squares
		if (y > 0)
		{
			grid[y-1][x].incrementNumber(true);
			if (x > 0){grid[y-1][x-1].incrementNumber(true);}
			if (x < columns-1){grid[y-1][x+1].incrementNumber(true);}
		}
		
		//increment eastern and western squares
		if (x > 0){grid[y][x-1].incrementNumber(true);}
		if (x < columns-1){grid[y][x+1].incrementNumber(true);}
		
		//increment southern squares
		if (y < rows-1)
		{
			grid[y+1][x].incrementNumber(true);
			if (x > 0){grid[y+1][x-1].incrementNumber(true);}
			if (x < columns-1){grid[y+1][x+1].incrementNumber(true);}
		}
	}
	
	public void rearrange(int x, int y)
	{
		int count = 0;
		boolean northernEdge = y == 0;
		boolean southernEdge = y == rows - 1;
		boolean westernEdge = x == 0;
		boolean easternEdge = x == columns - 1;
		
		if (rearrangeMineCheck(x, y))
		{
			count++;
			removeMine(grid[y][x], x, y);
		}
		
		if (!northernEdge)
		{
			if (rearrangeMineCheck(x, y-1))
			{
				count++;
				removeMine(grid[y-1][x], x, y-1);
			}
		}
		
		if (!southernEdge)
		{
			if (rearrangeMineCheck(x, y+1))
			{
				count++;
				removeMine(grid[y+1][x], x, y+1);
			}
		}
		
		if (!westernEdge)
		{
			if (rearrangeMineCheck(x-1, y))
			{
				count++;
				removeMine(grid[y][x-1], x-1, y);
			}
			if (!northernEdge)
			{
				if (rearrangeMineCheck(x-1, y-1))
				{
					count++;
					removeMine(grid[y-1][x-1], x-1, y-1);
				}
			}
			if (!southernEdge)
			{
				if (rearrangeMineCheck(x-1, y+1))
				{
					count++;
					removeMine(grid[y+1][x-1], x-1, y+1);
				}
			}
		}
		
		if (!easternEdge)
		{
			if (rearrangeMineCheck(x+1, y))
			{
				count++;
				removeMine(grid[y][x+1], x+1, y);
			}
			if (!northernEdge)
			{
				if (rearrangeMineCheck(x+1, y-1))
				{
					count++;
					removeMine(grid[y-1][x+1], x+1, y-1);
				}
			}
			if (!southernEdge)
			{
				if (rearrangeMineCheck(x+1, y+1))
				{
					count++;
					removeMine(grid[y+1][x+1], x+1, y+1);
				}
			}
		}
		
		if (DEBUG)
		{
			System.out.println(this);
		}
		
		moveMines(x, y, count);
	}
	
	private boolean rearrangeMineCheck(int x, int y)
	{
		return grid[y][x].isMine();
	}
	
	private void moveMines(int x, int y, int num)
	{
		while (num > 0)
		{
			int mineLocation = reserveMineLocations.get(0);
			int xLoc = mineLocation % columns;
			int yLoc = mineLocation / columns;
			
			if (Math.abs(x-xLoc) > 1 || 
					Math.abs(y-yLoc) > 1) //chosen location is intial click or adjacent square
			{
				setMine(grid[yLoc][xLoc], xLoc, yLoc);
				num--;
			}
			reserveMineLocations = 
					reserveMineLocations.subList(1, reserveMineLocations.size());
		}
	}
	
	private void removeMine(Square square, int x, int y)
	{
		square.removeMine();
		
		//increment northern squares
		if (y > 0)
		{
			grid[y-1][x].incrementNumber(false);
			if (x > 0){grid[y-1][x-1].incrementNumber(false);}
			if (x < columns-1){grid[y-1][x+1].incrementNumber(false);}
		}
		
		//increment eastern and western squares
		if (x > 0){grid[y][x-1].incrementNumber(false);}
		if (x < columns-1){grid[y][x+1].incrementNumber(false);}
		
		//increment southern squares
		if (y < rows-1)
		{
			grid[y+1][x].incrementNumber(false);
			if (x > 0){grid[y+1][x-1].incrementNumber(false);}
			if (x < columns-1){grid[y+1][x+1].incrementNumber(false);}
		}
	}
	
	public void setFlagClick(boolean b)
	{
		flagClick = b;
	}
	
	public boolean getFlagClick()
	{
		return flagClick;
	}
	
	public Square[][] getGrid()
	{
		return grid;
	}
	
	public void setWindow(Window window)
	{
		this.window = window;
	}
	
	public void setFirstClick(boolean b)
	{
		firstClick = b;
	}
	
	public boolean getFirstClick()
	{
		return firstClick;
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
	
	public void decreaseRemainingSquares()
	{
		remainingSquares--;
		checkWin();
	}
	
	private void checkWin()
	{
		if (remainingSquares <= 0)
		{
			gameEnd(true);
		}
	}
	
	public void gameOver()
	{
		for (int i = 0; i < grid[0].length; i++)
		{
			for (int j = 0; j < grid.length; j++)
			{
				grid[j][i].reveal();
			}
		}
		gameEnd(false);
	}
	
	private void gameEnd(boolean win)
	{
		EndWindow ender = new EndWindow(this);
		ender.gameEnd(win);
	}
	
	public void revealNeighbors(int x, int y)
	{
		boolean northernEdge = y == 0;
		boolean southernEdge = y == rows - 1;
		boolean westernEdge = x == 0;
		boolean easternEdge = x == columns - 1;
		
		if (!northernEdge)
		{
			grid[y-1][x].reveal();
		}
		
		if (!southernEdge)
		{
			grid[y+1][x].reveal();
		}
		
		if (!westernEdge)
		{
			grid[y][x-1].reveal();
			
			if (!northernEdge)
			{
				grid[y-1][x-1].reveal();
			}
			
			if (!southernEdge)
			{
				grid[y+1][x-1].reveal();
			}
		}
		
		if (!easternEdge)
		{
			grid[y][x+1].reveal();
			
			if (!northernEdge)
			{
				grid[y-1][x+1].reveal();
			}
			
			if (!southernEdge)
			{
				grid[y+1][x+1].reveal();
			}
		}
	}
	
	public String toString()
	{
		String toReturn = "";
		
		for (int y = 0; y < rows; y++)
		{
			toReturn += "| ";
			for (int x = 0; x < columns; x++)
			{
				toReturn += grid[y][x].toString();
				toReturn += " | ";
			}
			toReturn += "\n";
		}
		return toReturn;
	}
}
