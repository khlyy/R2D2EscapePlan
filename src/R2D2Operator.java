import java.util.ArrayList;

/**
 * @author Ahmed Ashraf, Mohamed Khaled, Omar Radwan.
 *
 */
public class R2D2Operator implements Operator
{
	/**
	 * The change in the position of row, control the moving up and down in the grid.
	 */
	int dx;
	/**
	 * The change in the position of columns, control the moving left and right in the grid.
	 */
	int dy;
	/**
	 * The cost of the operator if it leads to move actions.
	 */
	int moveCost;
	/**
	 * The cost of the operator if it leads to push actions.
	 */
	int pushCost;
	
	/**
	 * @param dx
	 * @param dy
	 * @param moveCost
	 * @param pushCost
	 */
	public R2D2Operator(int dx, int dy, int moveCost, int pushCost)
	{
		this.dx = dx;
		this.dy = dy;
		this.moveCost = moveCost;
		this.pushCost = pushCost;
	}
	
	/* (non-Javadoc)
	 * @see Operator#operate(Node)
	 * The function tries to move the robot in the operator's direction.
	 * If the updated position is occupied by a rock, then the rock position 
	 * will be updated in the same direction and assign the cost of the operator 
	 * as the push cost to the new node, Otherwise assign the move cost to new node.
	 * 
	 */
	@Override
	public Node operate(Node node)
	{
		R2D2State curState = (R2D2State) node.getState();
		Point curCell = curState.getCurCell();
		ArrayList<Point> rocksCells = new ArrayList<Point>();
		rocksCells.addAll(curState.getRocksCells());
		Point nxtCell = updatePoint(curCell);
		int rockIndex = rocksCells.indexOf(nxtCell);
//		System.out.println("Rock Index: "+ rockIndex + " CurrCell: " + nxtCell + " Rock cells: " + rocksCells);
		if(rockIndex != -1)
			rocksCells.set(rockIndex, updatePoint(rocksCells.get(rockIndex)));

		R2D2State nxtState = new R2D2State(nxtCell, rocksCells); 
		return new Node(nxtState, node.getCost() + (rockIndex == -1? moveCost: pushCost), node.getDepth() + 1, node);
	}

	/**
	 * @param point
	 * @return The updated point.
	 */
	public Point updatePoint(Point point)
	{
		return new Point(point.x + dx, point.y + dy);
	}
}
