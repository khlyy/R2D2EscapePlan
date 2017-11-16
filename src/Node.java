
public class Node implements Comparable<Node> 
{
	int cost;
	int heuristicCost;
	int depth;
	State state;
	Node parent;
	
	public Node(State state, int cost, int depth, Node parent)
	{
		this.state = state;
		this.cost = cost;
		this.depth = depth;
		this.parent = parent;
	}

	public State getState() 
	{
		return state;
	}

	public int getCost() 
	{
		return cost;
	}

	public int getDepth() 
	{
		return depth;
	}
	
	public int getHeuristicCost()
	{
		return this.heuristicCost;
	}
	
	public void setHeuristicCost(int heuristicCost)
	{
		this.heuristicCost = heuristicCost;
	}
	
	@Override
	public int compareTo(Node o) 
	{	
		int compare = this.getState().compareTo(o.getState());
		return compare;
	}
	
	public String toString()
	{
		return "Cost: "+ this.cost + " HCost: "+ this.heuristicCost + " Depth: " + this.depth  ;
	}
}