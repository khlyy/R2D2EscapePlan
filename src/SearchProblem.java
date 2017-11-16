import java.util.ArrayList;
/*
 * The search problem consists five tuple which are the root node,
 * the state space, the collection of operators, 
 * a goal test function and a path cost function
 */
public abstract class SearchProblem 
{	
	/**
	 * List of available operations of the problem.
	 */
	ArrayList<Operator> operators;
	/**
	 * The root of the problem search tree that contains the intial state.
	 */
	Node root;
	/**
	 * The state space  that controls the transitions between states.
	 */
	Graph graph;
	
	/**
	 * @param node
	 * @return True if the given node is the goal node, false otherwise.
	 */
	public abstract boolean goalTest(Node node);
	
	/**
	 * @param node
	 * @return True if the given node is valid node.
	 */
	public abstract boolean valid(Node node);
		
	/**
	 * @param node
	 * @return heuristic cost according to a heuristicFunction1 for the given node.
	 */
	public abstract int heuristicFunction1(Node node);

	/**
	 * @param node
	 * @return heuristic cost according to a heuristicFunction2 for the given node.
	 */
	public abstract int heuristicFunction2(Node node);

	/**
	 * @param node
	 * @return path cost from the root to a given node.
	 */
	public int pathCost(Node node)
	{
		return node.getCost();
	}
	
	/**
	 * @return
	 */
	public ArrayList<Operator> getOperators() 
	{
		return operators;
	}
	
	/**
	 * @return
	 */
	public Node getRoot()
	{
		return root;
	}
	
	/**
	 * @return
	 */
	public Object getGraph()
	{
		return graph;
	}
	
	
}
