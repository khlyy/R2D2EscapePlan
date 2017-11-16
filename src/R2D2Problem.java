import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Ahmed Ashraf, Mohamed Khaled, Omar Radwan
 *
 */
public class R2D2Problem extends SearchProblem
{
	/**
	 * The cost of taking one step to an empty cell in one of the four directions.
	 */
	final int moveCost = 1;
	/**
	 * The cost of pushing a rock one step.
	 */
	final int pushCost = 5;

	/**
	 * Creates an instance of R2D2Problem.
	 * @param grid Represents the state search space/graph.
	 * @param initialCell The robot's initial cell.
	 * @param rocksCells The position of the rocks cells.
	 */
	public R2D2Problem(Grid grid, Point initialCell, ArrayList<Point> rocksCells)
	{
		this.operators = creatOperators();
		R2D2State state = new R2D2State(initialCell, rocksCells);
		this.root = new Node(state, 0, 0, null);
		this.graph = grid;
	}
	

	/**
	 * Creates the available actions for the robot. 
	 * The actions are either moving or pushing the rock in one of the four directions.
	 * @return list of available operations.
	 */
	public ArrayList<Operator> creatOperators()
	{
		ArrayList<Operator> operators = new ArrayList<>();
		int dx[] = {-1, 0, 1, 0};
		int dy[] = {0, 1, 0, -1};
		
		for(int i = 0; i < 4; i++)
			operators.add(new R2D2Operator(dx[i], dy[i], moveCost, pushCost));
		
		return operators;
	}
	
	/**
	 * @return Grid that represents the state space of R2D2 problem.
	 */
	public Grid castGrid()
	{
		return ((Grid) this.graph);
	}
	
	/* (non-Javadoc)
	 * @see SearchProblem#goalTest(Node)
	 * Make each rock is positioned on a pressure pad and the robot current position is the teleportal cell. 
	 */
	@Override
	public boolean goalTest(Node node) 
	{	
		R2D2State curState = (R2D2State) node.getState();
		Point curCell = curState.getCurCell();
		ArrayList<Point> rocksCells = curState.getRocksCells();
		if(!this.inGrid(curCell) || castGrid().getCell(curCell.x, curCell.y) != 'G')
			return false;
		
		for(Point rock: rocksCells)
			if(!this.inGrid(rock) || castGrid().getCell(rock.x, rock.y) != 'X')
				return false;
		return true;
	}
	
	
	
	/* (non-Javadoc)
	 * @see SearchProblem#valid(Node)
	 * Checks that robot and the rocks positions are inside the grid, not an obstacle and not unactivated teleportal.
	 */
	@Override
	public boolean valid(Node node) 
	{
		R2D2State curState = (R2D2State) node.getState();
		Point curCell = curState.getCurCell();
		ArrayList<Point> rocksCells = new ArrayList<Point>();
		rocksCells.addAll(curState.getRocksCells());
		rocksCells.add(curCell);
		
		TreeSet<Point> distinctRockCells = new TreeSet<Point>(); 
		distinctRockCells.addAll(rocksCells);
		
		if(distinctRockCells.size() < rocksCells.size())
			return false;
		
		if(this.goalTest(node))
			return true;
		
		for(Point point: rocksCells)
			if(!inGrid(point) || castGrid().getCell(point.x, point.y) == '#' || castGrid().getCell(point.x, point.y) == 'G')
				return false;
	
		if(castGrid().getCell(curCell.x, curCell.y) == 'G' && !this.goalTest(node))
			return false;
		return true;
	}
	
	/**
	 * @param point
	 * @return True if the given point inside the grid.
	 */
	private boolean inGrid(Point point)
	{
		return point.x >= 0 && point.x < castGrid().getLength() && point.y >= 0 && point.y < castGrid().getWidth();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return this.castGrid().toString();
	}

	/* (non-Javadoc)
	 * @see SearchProblem#heuristicFunction0(Node)
	 * Compute the heuristic cost according to the distance between the robot and the teleportal.
	 */
	@Override
	public int heuristicFunction1(Node node) 
	{
		R2D2State curState = (R2D2State) node.getState();
		Point goal = this.castGrid().getTeleportal();
		ArrayList<Point> rocksCells = new ArrayList<>();
		rocksCells.addAll(curState.getRocksCells());
		ArrayList<Point> padsCells = this.castGrid().getPadsCells();
		int minDistance = 0, wrongPlacedRocks = 0;
		for(Point pad: padsCells)
		{
			Point closestRock = new Point(0, 0);
			int closestDist = Integer.MAX_VALUE;
			for(Point rock: rocksCells)
			{
				int curDist = getDistance(rock, pad);
				if(curDist < closestDist)
				{	
					closestRock = rock;
					closestDist = curDist;
				}
			}
			
			if(closestDist > 0)
			{
				minDistance = Math.min(closestDist, minDistance);
				wrongPlacedRocks++;
			}
			rocksCells.remove(closestRock);
		}
		
		return (minDistance * wrongPlacedRocks * this.pushCost) + getDistance(curState.getCurCell(), goal);
	}

	/* (non-Javadoc)
	 * @see SearchProblem#heuristicFunction1(Node)
	 * The heuristic cost is calculated as the summation of the distance 
	 * Between each rock and its closest pressure pad multiplied by the push cost. 
	 * in addition to the city block distance between the robot and the teleportal.
	 */
	@Override
	public int heuristicFunction2(Node node) 
	{
		R2D2State curState = (R2D2State) node.getState();
		Point goal = this.castGrid().getTeleportal();
		ArrayList<Point> rocksCells = new ArrayList<>();
		rocksCells.addAll(curState.getRocksCells());
		ArrayList<Point> padsCells = this.castGrid().getPadsCells();
		int totalDist = 0;
		for(Point pad: padsCells)
		{
			Point closestRock = new Point(0, 0);
			int closestDist = Integer.MAX_VALUE;
			for(Point rock: rocksCells)
			{
				int curDist = getDistance(rock, pad);
				if(curDist < closestDist)
				{	
					closestRock = rock;
					closestDist = curDist;
				}
			}
			totalDist += (closestDist * this.pushCost);
			rocksCells.remove(closestRock);
		}
		
		return totalDist + getDistance(curState.getCurCell(), goal);
	}
	
	/**
	 * @param point1
	 * @param point2
	 * @return The city block distance between two points. 
	 */
	public static int getDistance(Point point1, Point point2)
	{
		return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
	}

}
