import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Grid {

	int width;
	int height;
	State initialState;
	State finalState;
	Node initial;

	public Grid(short dimension)
	{
		width = height = dimension;
		short squared = (short) (dimension*dimension);
		short squaredMinus = (short) (squared - dimension);
		short[] state = new short[squared];

		for(short i = 0; i < squaredMinus; i++)
		{
			state[i] = 0;
		}
		short j = 1;
		for(short i = squaredMinus; i < squaredMinus+3; i++)
		{
			state[i] = j;
			j++;
		}
		state[squared-1] = -1;

		initialState = new State(state, dimension, (short) (squared - 1));

		short[] finalState = new short[squared];

		ArrayList<Integer> columns = new ArrayList<Integer>();
		for(short i = 0; i < dimension*dimension; i++)
		{
			if((i % dimension)== 1 && i > dimension)
			{
				columns.add((int) i);
			}
		}

		short count = 3;
		while(!columns.isEmpty() && count != 0)
		{
			finalState[columns.get(columns.size()-1)] = count;
			columns.remove(columns.size()-1);
			count--;
		}



		finalState[squared-1] = -1;

		this.finalState = new State(finalState, dimension, (short) (squared - 1));
		initial = new Node(initialState);

	}


	public Result dfs()
	{
		long startTime = System.currentTimeMillis();
		Deque<Node> stack = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();

		int nodesProcessed = 0;

		stack.addFirst(initial);


		while(!stack.isEmpty())
		{

			Node temp = stack.removeFirst();
			nodesProcessed++;

			if(temp.getState().equals(finalState))
			{
				long endTime = System.currentTimeMillis();


				Deque<Grid.Direction> pathStack = new ArrayDeque<Grid.Direction>();
				pathStack.add(temp.getDirection());
				Node temp2 = temp.getParent();

				while(temp2.getParent() != null)
				{
					pathStack.addFirst(temp2.getDirection());
					temp2 = temp2.getParent();
				}

				StringBuilder pathTaken = new StringBuilder(pathStack.size());

				if(pathStack.peek().toString().equals(null))
				{

				}
				else
				{
					while(!pathStack.isEmpty())
					{

						pathTaken.append(pathStack.removeFirst().toString());
					}
				}

				return new Result(pathTaken.toString(), nodesProcessed, startTime-endTime);
			}
			if(!set.contains(temp))
			{
				set.add(temp);
				for(Node child : temp.successors())
				{
					stack.addFirst(child);
				}

			}
		}
		return null;
	}


	public Result bfs()
	{
		long startTime = System.currentTimeMillis();
		Queue<Node> queue = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();

		int nodesProcessed = 0;

		queue.add(initial);
		set.add(initial);


		while(!queue.isEmpty())
		{

			Node temp = queue.poll();
			nodesProcessed++;
			
			if(temp.getState().equals(finalState))
			{
				long endTime = System.currentTimeMillis();
				Deque<Grid.Direction> pathStack = new ArrayDeque<Grid.Direction>();
				pathStack.add(temp.getDirection());
				Node temp2 = temp.getParent();

				while(temp2.getParent() != null)
				{
					pathStack.addFirst(temp2.getDirection());
					temp2 = temp2.getParent();
				}

				StringBuilder pathTaken = new StringBuilder(pathStack.size());

				if(pathStack.peek().toString().equals(null))
				{

				}
				else
				{
					while(!pathStack.isEmpty())
					{

						pathTaken.append(pathStack.removeFirst().toString());
					}
				}

				return new Result(pathTaken.toString(), nodesProcessed, startTime-endTime);
			}
			for(Node child : temp.successors())
			{
				if(!(set.contains(child)))
				{
					set.add(child);
					queue.add(child);
				}
			}
		}
		return null;

	}

	public Result iddfs_helper(Integer i)
	{		
		Deque<Node> stack = new ArrayDeque<Node>();

		int nodesProcessed = 0;

		stack.addFirst(initial);

		while(!stack.isEmpty())
		{

			Node temp = stack.removeFirst();
			nodesProcessed++;
			if(temp.getCost() <= i)
			{

				if(temp.getState().equals(finalState)) {
					long endTime = System.currentTimeMillis();

					Deque<Grid.Direction> pathStack = new ArrayDeque<Grid.Direction>();
					pathStack.add(temp.getDirection());
					Node temp2 = temp.getParent();

					while(temp2.getParent() != null)
					{
						pathStack.addFirst(temp2.getDirection());
						temp2 = temp2.getParent();
					}

					StringBuilder pathTaken = new StringBuilder(pathStack.size());

					if(!pathStack.peek().toString().equals(null))
					{
						while(!pathStack.isEmpty())
						{
							pathTaken.append(pathStack.removeFirst().toString());
						}
					}
					return new Result(pathTaken.toString(), nodesProcessed, endTime);
				}
				else
				{
					for(Node n: temp.successors())
					{
						stack.addFirst(n);
					}
				}
			}
		}

		return new Result("n", nodesProcessed, 0);

	}

	public Result iddfs()
	{
		long startTime = System.currentTimeMillis();

		int nodesProcessed = 0;
		for(int i = 1; i < Integer.MAX_VALUE; i += 1)
		{
			Result answer = iddfs_helper(i);
			if(!answer.getPath().equals("n")) 
			{
				return new Result(answer.getPath(), nodesProcessed += answer.getNodesExpanded(), startTime - answer.getTimeTaken());
			}
			else
			{
				nodesProcessed += answer.getNodesExpanded();
			}
		}
		return null;
	}

	public Result astar()
	{
		long startTime = System.currentTimeMillis();
		HashSet<HeuristicNode> seen = new HashSet<HeuristicNode>();
		Queue<HeuristicNode> toConsider = new PriorityQueue<HeuristicNode>();
		//Map<Node, Node> routesTaken = new HashMap<Node, Node>();

		int nodesProcessed = 0;

		toConsider.add(new HeuristicNode(initial, 0));

		while(!toConsider.isEmpty())
		{
			HeuristicNode current = toConsider.poll();
			nodesProcessed++;
			/*	System.out.println(Arrays.toString(current.state.state));
			System.out.println(Arrays.toString(finalState.state));*/
			if(current.getNode().getState().equals(finalState))
			{
				long endTime = System.currentTimeMillis();
				Deque<Grid.Direction> pathStack = new ArrayDeque<Grid.Direction>();
				pathStack.add(current.getNode().getDirection());
				Node temp2 = current.getNode().getParent();

				while(temp2.getParent() != null)
				{
					pathStack.addFirst(temp2.getDirection());
					temp2 = temp2.getParent();
				}

				StringBuilder pathTaken = new StringBuilder(pathStack.size());

				if(pathStack.peek().toString().equals(null))
				{

				}
				else
				{
					while(!pathStack.isEmpty())
					{

						pathTaken.append(pathStack.removeFirst().toString());
					}
				}

				return new Result(pathTaken.toString(), nodesProcessed, startTime-endTime);
			}
			else
			{
				seen.add(current);

				for(Node n : current.getNode().successors())
				{
					int childFscore = n.getCost() + heuristic(n);
					HeuristicNode newN = new HeuristicNode(n, childFscore);
					if(!seen.contains(newN))
					{
						if(!toConsider.contains(newN) || toConsider.peek().getFscore() > childFscore)
						{
							//routesTaken.put(current, n);
							toConsider.add(newN);
						}
					}
				}
			}
		}

		return null;

	}


	public int heuristic(Node n)
	{
		int total = 0;
		for(int i = 1; i <= 3; i++)
		{
			int current = -1;
			int goalstate = -1;
			for(int z = 0; z < n.getState().getState().length; z++)
			{
				if(n.getState().getState()[z] == i) 
				{ current = z; break; }
			}

			for(int z = 0; z < finalState.getState().length; z++)
			{
				if(finalState.getState()[z] == i) { goalstate = z; break; }
			}


			int currentx = current % width;
			int currenty = (int) Math.floor(current/width);



			int goalx = goalstate % width;
			int goaly = (int) Math.floor(goalstate/width);


			total += Math.abs(currentx-goalx ) + Math.abs(currenty - goaly);


		}
		return total;
	}

	public static void main(String args[])
	{
		try {
			String newline = System.getProperty("line.separator");
			PrintWriter dfsWriter = new PrintWriter(new FileWriter("dfs.txt", true));
			PrintWriter iddfsWriter = new PrintWriter(new FileWriter("iddfs.txt", true));
			PrintWriter bfsWriter = new PrintWriter(new FileWriter("bfs.txt", true));
			PrintWriter astarWriter = new PrintWriter(new FileWriter("astar.txt", true));

			for(short i = 4; i < 20; i++)
			{
				Grid test = new Grid(i);

				Result r1 = test.iddfs();
				long iddfs = r1.nodesExpanded;
				StringBuilder iddfsOutput = new StringBuilder();
				iddfsOutput.append("IDDFS for dimension " + i + newline + "depth: " + r1.path.length() + newline);
				iddfsOutput.append("path: " + r1.path + newline);
				iddfsOutput.append("nodes expanded: "+iddfs + newline);

				iddfsWriter.println(iddfsOutput.toString());
				iddfsWriter.flush();

				Result r2 = test.bfs();
				long bfs = r2.nodesExpanded;
				StringBuilder bfsOutput = new StringBuilder();
				bfsOutput.append("bfs for dimension " + i + newline + "depth: " + r2.path.length() + newline);
				bfsOutput.append("path: " + r2.path + newline);
				bfsOutput.append("nodes expanded: "+bfs + newline);

				bfsWriter.println(bfsOutput.toString());
				bfsWriter.flush();

				Result r3 = test.dfs();
				long dfs = r3.nodesExpanded;
				StringBuilder dfsOutput = new StringBuilder();
				dfsOutput.append("DFS for dimension " + i + newline + "depth: " + r3.path.length() + newline);
				dfsOutput.append(System.getProperty("line.separator"));
				dfsOutput.append("nodes expanded: "+dfs + newline);

				dfsWriter.println(dfsOutput.toString());
				dfsWriter.flush();

				Result r4 = test.astar();
				long astar = r4.nodesExpanded;
				StringBuilder astarOutput = new StringBuilder();
				astarOutput.append("astar for dimension " + i + newline + "depth: " + r4.path.length() + newline);
				astarOutput.append("path: " + r4.path + newline);
				astarOutput.append("nodes expanded: "+astar + newline);

				astarWriter.println(astarOutput.toString());
				astarWriter.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*Node blah = four.astar();
		System.out.println(blah.getCost());
		//System.out.println(Arrays.toString(four.initialState.state));
		long start =System.currentTimeMillis();
		four.bfs();
		System.out.println("Time taken in ms for bfs: " + (System.currentTimeMillis() - start));

		long start2 = System.currentTimeMillis();
		four.dfs();
		System.out.println("Time taken in ms for dfs: " + (System.currentTimeMillis() - start2));

		long start3 = System.currentTimeMillis();
		four.iddfs();
		System.out.println("Time taken in ms for iddfs: " + (System.currentTimeMillis() - start3));

		long start4 =System.currentTimeMillis();
		four.astar();
		System.out.println("Time taken in ms for astar: " + (System.currentTimeMillis() - start4));
	 */



	/*		Grid.initSquareGrid(5);

		long start =System.currentTimeMillis();
		Node n = Grid.bfs(Grid.generateBaseNode(), Grid.generateFinalState());
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(n.getState().toString());
		System.out.println(n.getCost());
	 */


	public enum Direction
	{
		UP, DOWN, LEFT, RIGHT;

		public String toString()
		{
			switch(this)
			{
			case UP: return "U"; 
			case DOWN: return "D"; 
			case LEFT: return "L";
			case RIGHT: return "R";
			}
			return null;
		}
	}
}



