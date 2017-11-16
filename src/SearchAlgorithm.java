import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author Ahmed Ashraf, Mohamed Khaled, Omar Radwan.
 *
 */
public class SearchAlgorithm 
{
	/**
	 * 
	 */
	SearchProblem searchProblem;
	/**
	 * 
	 */
	SearchStrategies searchStrategy;
	/**
	 * 
	 */
	TreeSet<Node> visited;
	/**
	 * 
	 */
	int NoOfExpandedNodes;
	
	/**
	 * @param searchProblem
	 * @param searchStrategy
	 */
	public SearchAlgorithm(SearchProblem searchProblem, SearchStrategies searchStrategy) 
	{
		this.searchProblem = searchProblem;
		this.searchStrategy = searchStrategy;
		visited = new TreeSet<>();
		NoOfExpandedNodes = 0;
	}
		
	/**
	 * Keep discovering nodes of the search tree by intily queuing the root node of the problem
	 * 
	 * Case ID:
	 * @return Goal node if a solution exist, null otherwise.
	 */
	public Node run()
	{
		int iterations = this.searchStrategy == SearchStrategies.ID ? 1000000000 : 1;
		for(int maxDepth = 1; maxDepth <= iterations; maxDepth++)
		{
			boolean isStopped = true;
			Queue queue = new Queue(this.searchStrategy);
			queue.add(this.searchProblem.getRoot());
			visited.clear();
			visited.add(this.searchProblem.getRoot());
			while(!queue.isEmpty())
			{
				Node curNode = queue.getFirst();
				
				if(this.searchStrategy == SearchStrategies.ID && curNode.depth > maxDepth)
				{
					queue.removeFirst();
					isStopped = false;
					continue;
				}
				
				if(this.searchProblem.goalTest(curNode))
					return curNode;
				
				queue = queuingFunction(queue);
			}
			
			if(isStopped) break;
		}
		return null;
	}
	
	/**
	 * @param queue
	 * @return
	 */
	public Queue queuingFunction(Queue queue)
	{
		ArrayList<Node> nxtNodes = Expand(queue.removeFirst());
		queue.addAll(nxtNodes);
		return queue;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String searchAlgorithm = "Search strategy: " + this.searchStrategy;
		searchAlgorithm += ", Number of expanded nodes: "+ this.NoOfExpandedNodes;
		return searchAlgorithm;
		
	}
	/**
	 * @param node
	 * @return
	 */
	public ArrayList<Node> Expand(Node node) 
	{
		ArrayList<Node> nxtNodes = new ArrayList<>();
		ArrayList<Operator> operators = this.searchProblem.getOperators();
		for (Operator operator : operators) 
		{
			Node nxtNode = operator.operate(node);
			if(this.searchProblem.valid(nxtNode) && !visited.contains(nxtNode))
			{
				nxtNode.setHeuristicCost(getHeuristicCost(nxtNode));
				nxtNodes.add(nxtNode);
				visited.add(nxtNode);
			}
		}
		NoOfExpandedNodes+= nxtNodes.size();
		return nxtNodes;
	}
	/**
	 * @return
	 */
	public int getNoOfExapandedNodes()
	{
		return this.NoOfExpandedNodes;
		
	}
	/**
	 * @param node
	 * @return
	 * 
	 */
	public int getHeuristicCost(Node node)
	{
		switch(this.searchStrategy)
		{
			case GR1:
			case AS1: return this.searchProblem.heuristicFunction1(node);
			case GR2:
			case AS2: return this.searchProblem.heuristicFunction2(node);
		}
		return 0;
	}
}
