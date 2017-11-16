import java.util.ArrayList;

public class R2D2State extends State
{

	Point curCell;
	ArrayList<Point> rocksCells;
	
	public R2D2State(Point curCell, ArrayList<Point> rocksCells)
	{
		this.curCell = curCell;
		this.rocksCells = rocksCells;
	}
	
	public Point getCurCell() 
	{
		return curCell;
	}
	
	public ArrayList<Point> getRocksCells()
	{
		return rocksCells;
	}

	public String toString()
	{
		return rocksCells.toString() + "\n" + curCell.toString() + "\n";
	}

	@Override
	public int compareTo(State o) {
		R2D2State state = (R2D2State) o;
		if(state.curCell .equals(this.curCell))
		{
			for (int i = 0; i < rocksCells.size(); i++)
			{	
				Point p1 = rocksCells.get(i);
				Point p2 = state.getRocksCells().get(i);
				if(!p1.equals(p2))
					return p1.compareTo(p2);
			}
			
		}
		return this.curCell.compareTo(state.getCurCell());
	}

}
