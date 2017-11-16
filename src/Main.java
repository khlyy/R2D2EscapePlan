import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	
	public static Grid getGrid()
	{
		Grid grid = new Grid();
		grid.fillRandom();
		return grid;
	}
	
	public static String Search(Grid grid, String strategy, boolean visualize)
	{
		SearchStrategies searchStrategy = SearchStrategies.valueOf(strategy);
		R2D2Problem r2d2Problem = new R2D2Problem(grid, grid.getCurrCell(), grid.getRockscells());
		SearchAlgorithm searchAlgorithm = new SearchAlgorithm(r2d2Problem, searchStrategy);
		Node goal = searchAlgorithm.run();
		
		String searchResult = goal == null? "No solution found!\n": visualize? "The discovered solution:\n" + grid.printPath(goal): "";
		searchResult += goal != null? "\nThe total cost: " + goal.getCost() + ".": "";
		searchResult += "\nThe number of expanded nodes: " + searchAlgorithm.getNoOfExapandedNodes() + ".";
		
		return searchResult;
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please, choose one of the search strategies [BE, DF, ID, UC, GR1, GR2, AS1, AS2]");
		String searchStrategy = br.readLine().trim().toUpperCase();
		System.out.println("Do you want to visualize the solution path if exist?");
		String  visualize = br.readLine().trim().toLowerCase();
		Grid grid = getGrid();

		System.out.println(Search(grid, searchStrategy, visualize.equals("yes")));
		
	}
}
