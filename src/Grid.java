import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ahmed Ashraf, Mohamed Khaled, Omar Radwan.
 *
 */
public class Grid implements Graph
{	
	/**
	 * List of rocks positions in the grid.
	 */
	private ArrayList<Point> rocksCells;
	/**
	 * The current position of the robot.
	 */
	private Point currCell;
	/**
	 * Defines the dimensions of the grid.
	 */
	private int length, width; 
	/**
	 * Represent the grid.
	 */
	char[][] gridCells;	
	
	/**
	 * @param length
	 * @param width
	 */
	public Grid(int length, int width)
	{
		this.length = length;
		this.width = width;
		gridCells = new char[length][width];
		rocksCells = new ArrayList<Point>();
		currCell = new Point(0, 0);
	}
	
	/**
	 * Create a grid with a random dominations and fill it with random components.
	 */
	public Grid()
	{
		this.length = (int) (Math.random() * 6 + 2);
		this.width = (int) (Math.random() * 6 + 2);
		gridCells = new char[length][width];
		rocksCells = new ArrayList<Point>();
		currCell = new Point(0, 0);
	}

	/**	
	 * @param input
	 */
	public void fillInput(String input)
	{
		
		for(int i = 0; i < this.length; i++)
			Arrays.fill(gridCells[i], '.');

		String[] split = input.split("\n");
		for(int i = 0; i < split.length; i++)
			for(int j = 0; j < split[i].length(); j++)
				if(split[i].charAt(j) == '^')
					this.currCell = new Point(i, j);
				else if(split[i].charAt(j) == '*')
					this.rocksCells.add(new Point(i, j));
				else this.gridCells[i][j] = split[i].charAt(j);
	}
	
	/**
	 * Fill the grid with random number of rocks [1, 4] and number of obstacles [1, 4].
	 */
	public void fillRandom()
	{
		this.fillRandom((int) (Math.random() * 4 + 1), (int) (Math.random() * 4 + 1), 1);
	}
	
	/**
	 * Firstly, Iterate over grid cell to choose for each cell whether it is free,  pressure pad, obstacle or teleportal.
	 * Secondly, Iterate over the free cells and the pressure pad cells to choose for each cell whether a rock, robot 
	 * or nothing will be positioned.
	 * @param numPads Number of pressure pads.
	 * @param numObstacles Number of obstacles.
	 * @param numTeleportal Number of teleportals.
	 * 
	 */
	public void fillRandom(int numPads, int numObstacles, int numTeleportal)
	{
		int total = length * width, startCell = 1, currNumRocks = numPads;
		int currNumObstacles = numObstacles, currNumTeleportal = numTeleportal, currNumPads = numPads;
		for(int i = 0; i < this.length; i++)
			for(int j = 0; j < this.width; j++)
			{
				int rnd = (int) (Math.random() * total + 1);
				if(rnd <= currNumTeleportal)
				{
					gridCells[i][j] = 'G';
					currNumTeleportal--;
				}
				else if(rnd <= currNumPads + currNumTeleportal)
				{
					gridCells[i][j] = 'X';
					currNumPads--;
				}
				else if(rnd <= currNumObstacles + currNumPads + currNumTeleportal)
				{
					gridCells[i][j] = '#';
					currNumObstacles--;
				}
				else
					gridCells[i][j] = '.';
				
				total--;
			}
		
		total = length * width - numObstacles - numTeleportal;
		
		for(int i = 0; i < this.length; i++)
			for(int j = 0; j < this.width; j++)
			{
				if(gridCells[i][j] == '#' || gridCells[i][j] == 'G') continue;
				int rnd = (int) (Math.random() * total + 1);
				if(rnd <= startCell)
				{
					currCell = new Point(i, j);
					startCell--;
				}
				else if(rnd <= currNumRocks + startCell)
				{
					rocksCells.add(new Point(i, j));
					currNumRocks--;
				}
				total--;
			}
	}
	
	/**
	 * @param grid
	 * @return 
	 */
	public String charArrayToString(char[][] grid)
	{
		String gridString = "";
		for(int i = 0; i < length; i++)
		{
			for(int j = 0; j < width; j++)
				gridString += grid[i][j];
			
			gridString += '\n';
		}
		return gridString;
	}
	
	/**
	 * Update the grid with new robot and rocks positions.
	 * @param state
	 * @return String of the updated grid.
	 */
	public String updateGrid(R2D2State state)
	{	
		this.setCurrCell(state.getCurCell());
		this.setRocksCells(state.getRocksCells());
		return this.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		char[][] grid = new char[length][width];
		
		for(int i = 0; i < length; i++)
			for(int j = 0; j < width; j++)
				grid[i][j] = gridCells[i][j];
			
		
		for(Point rock: rocksCells)
			grid[rock.x][rock.y] = '*';
		
		grid[this.currCell.x][this.currCell.y] = '^';
		
		return "Length: " + this.length + " Width: "+ this.width + "\n" + this.charArrayToString(grid);
	}
	
	/**
	 * @return
	 */
	public Point getCurrCell() 
	{
		return currCell;
	}

	/**
	 * @param rocksCells
	 */
	public void setRocksCells(ArrayList<Point> rocksCells) 
	{
		this.rocksCells = rocksCells;
	}

	/**
	 * @param currCell
	 */
	public void setCurrCell(Point currCell)
	{
		this.currCell = currCell;
	}

	/**
	 * @return
	 */
	public ArrayList<Point> getRockscells() 
	{
		return rocksCells;
	}

	/**
	 * @return
	 */
	public char[][] getGraphCells() 
	{
		return gridCells;
	}

	/**
	 * @return
	 */
	public int getLength()
	{
		return length;
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getTeleportal()
	{
		for(int i = 0; i < this.length; i++)
			for(int j = 0; j < this.width; j++)
				if(this.gridCells[i][j] == 'G')
					return new Point(i, j);
		
		return null;
	}
	
	/**
	 * @return
	 */
	public ArrayList<Point> getPadsCells()
	{
		ArrayList<Point> pads = new ArrayList<>();
		for(int i = 0; i < this.length; i++)
			for(int j = 0; j < this.width; j++)
				if(this.gridCells[i][j] == 'X')
					pads.add(new Point(i, j));
		return pads;
	}
	
	/* (non-Javadoc)
	 * @see Graph#printPath(Node)
	 * 
	 */
	@Override
	public String printPath(Node node) 
	{
		if(node == null) return "";
		return printPath(node.parent) + node.toString() + "\n" + this.updateGrid((R2D2State) node.getState()) + '\n';
	}

	/**
	 * @return
	 */
	public int getWidth() 
	{
		return width;
	}
	
	/**
	 * @param i
	 * @param j
	 * @return
	 */
	public char getCell(int i, int j)
	{
		return this.gridCells[i][j];
	}

}
