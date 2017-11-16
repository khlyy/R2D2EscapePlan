
public class Point implements Comparable<Point>
{
	int x, y;
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int compareTo(Point o)
	{
		if(this.x == o.x)
			return this.y - o.y;
		
		return this.x - o.x;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null) return false;
		Point p = (Point) o;
		return x == p.x && y == p.y;
	}
	
	public String toString()
	{
		return x + " , " + y;
	}
}
