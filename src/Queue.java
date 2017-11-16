import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Queue
{
	PriorityQueue<Node> pq;
	
	public Queue(SearchStrategies strategy) 
	{
		Comparator<Node> comparator = new Comparator<Node>()
		{	
			@Override
			public int compare(Node o1, Node o2)
			{
				switch (strategy) 
				{
					case DF:  return o2.getDepth() - o1.getDepth();
					case BE:  return o1.getDepth() - o2.getDepth();
					case UC:  return o1.getCost() - o2.getCost();
					case ID:  return o2.getDepth() - o1.getDepth();
					case GR1:
					case GR2: return o1.getHeuristicCost() - o2.getHeuristicCost();
					case AS1:
					case AS2: return o1.getCost() + o1.getHeuristicCost() - (o2.getCost() + o2.getHeuristicCost());	
				}
				return -1;
			}
		};
		pq = new PriorityQueue<>(comparator);
	}
	
	public Node removeFirst()
	{
		return pq.remove();
	}
	
	public Node getFirst()
	{
		return pq.peek();
	}
	
	public void add(Node node)
	{
		pq.add(node);
	}
	
	public boolean isEmpty()
	{
		return pq.isEmpty();
	}
	
	public boolean addAll(ArrayList<Node> nodes)
	{
		return pq.addAll(nodes);
	}
}
