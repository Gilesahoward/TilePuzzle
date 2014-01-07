import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Grid {

	int width;
	int height;
	State initialState;
	State finalState;
	Node initial;

	public Grid(int dimension)
	{
		width = height = dimension;
		int squared = dimension*dimension;
		int squaredMinus = squared - dimension;
		int[] state = new int[squared];

		for(int i = 0; i < squaredMinus; i++)
		{
			state[i] = 0;
		}
		int j = 1;
		for(int i = squaredMinus; i < squaredMinus+3; i++)
		{
			state[i] = j;
			j++;
		}
		state[squared-1] = -1;

		initialState = new State(state, dimension, squared - 1);

		int[] finalState = new int[squared];

		ArrayList<Integer> columns = new ArrayList<Integer>();
		for(int i = 0; i < dimension*dimension; i++)
		{
			if((i % dimension)== 1 && i > dimension)
			{
				columns.add(i);
			}
		}

		int count = 3;
		while(!columns.isEmpty() && count != 0)
		{
			finalState[columns.get(columns.size()-1)] = count;
			columns.remove(columns.size()-1);
			count--;
		}



		finalState[squared-1] = -1;

		this.finalState = new State(finalState, dimension, squared - 1);

		System.out.println(Arrays.toString(state));
		System.out.println(Arrays.toString(finalState));

		initial = new Node(initialState);

	}


	public Result dfs()
	{
		long startTime = System.currentTimeMillis();
		Deque<Node> stack = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();

		int nodesProcessed = 0;

		stack.addFirst(initial);
		//	set.add(initial);


		while(!stack.isEmpty())
		{

			Node temp = stack.removeFirst();
			nodesProcessed++;
			//System.out.println(Arrays.toString(temp.state.state));
			/*	System.out.println("Set contains: " + set.size());
			System.out.println("Queue contains: " + queue.size());*/
			if(temp.getState().equals(finalState))
			{
				long endTime = System.currentTimeMillis();
				//System.out.println("Success, ending dfs: " + set.size());

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

				//return temp;
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
			//System.out.println(Arrays.toString(temp.state.state));
			/*	System.out.println("Set contains: " + set.size());
			System.out.println("Queue contains: " + queue.size());*/
			if(temp.getState().equals(finalState))
			{
				long endTime = System.currentTimeMillis();
				//System.out.println("Success, ending bfs: " + set.size());
				/*				Deque<Grid.Direction> stack = new ArrayDeque<Grid.Direction>();
				stack.add(temp.getDirection());
				Node temp2 = temp.parent;

				while(temp2.getParent() != null)
				{
					stack.addFirst(temp2.getDirection());
					temp2 = temp2.getParent();
				}

				for(Grid.Direction d : stack)
				{
					System.out.println(d);
				}*/
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
				//System.out.println(temp.successors().size());
				if(!(set.contains(child)))
				{//System.out.println("State not already seen!");
					set.add(child);
					queue.add(child);
				}
				else
				{
					//System.out.println("State already seen!");
				}
			}
		}
		return null;

	}

	public Result iddfs_helper(Integer i)
	{		
		Deque<Node> stack = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();

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

					return new Result(pathTaken.toString(), nodesProcessed, endTime);
				}

				else
				{
					set.add(temp);
					for(Node n: temp.successors())
					{
						if(!set.contains(n))
						{
							stack.addFirst(n);
							set.add(n);
						}
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
		Set<Node> seen = new HashSet<Node>();
		Queue<HeuristicNode> toConsider = new PriorityQueue<HeuristicNode>();
		//Map<Node, Node> routesTaken = new HashMap<Node, Node>();

		int nodesProcessed = 0;

		toConsider.add(new HeuristicNode(initial, heuristic(initial)));

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

				seen.add(current.getNode());

				for(Node n : current.getNode().successors())
				{
					if(!seen.contains(n))
					{
						HeuristicNode newN = new HeuristicNode(n, heuristic(n));
						//routesTaken.put(current, n);
						toConsider.add(newN);
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
		total += n.getCost();
		return total;
	}

	public static void main(String args[])
	{
		try {
			PrintWriter dfsWriter = new PrintWriter(new FileWriter("dfs.txt", true));
			PrintWriter iddfsWriter = new PrintWriter(new FileWriter("iddfs.txt", true));
			PrintWriter bfsWriter = new PrintWriter(new FileWriter("bfs.txt", true));
			PrintWriter astarWriter = new PrintWriter(new FileWriter("astar.txt", true));

			for(int i = 4; i < 20; i++)
			{
				long[] iddfs = new long[10];
				long[] bfs = new long[10];
				long[] dfs = new long[10];
				long[] astar = new long[10];

				for(int z = 0; z < 10; z++)
				{
					Grid test = new Grid(i);
					Result r1 = test.iddfs();
					iddfs[z] = r1.nodesExpanded;

					Result r2 = test.bfs();
					bfs[z] = r2.nodesExpanded;

					Result r3 = test.dfs();
					dfs[z] = r3.nodesExpanded;

					Result r4 = test.astar();
					astar[z] = r4.nodesExpanded;
				}


				StringBuilder dfsOutput = new StringBuilder();
				dfsOutput.append("DFS for square grid of size: " + i);
				for(int loop = 0; loop < 10; loop++)
				{
					dfsOutput.append(","+dfs[0]);
				}

				dfsWriter.println(dfsOutput.toString());
				dfsWriter.flush();

				StringBuilder iddfsOutput = new StringBuilder();
				iddfsOutput.append("IDDFS for square grid of size: " + i);
				for(int loop = 0; loop < 10; loop++)
				{
					iddfsOutput.append(","+iddfs[0]);
				}

				iddfsWriter.println(iddfsOutput.toString());
				iddfsWriter.flush();

				StringBuilder bfsOutput = new StringBuilder();
				bfsOutput.append("bfs for square grid of size: " + i);
				for(int loop = 0; loop < 10; loop++)
				{
					bfsOutput.append(","+bfs[0]);
				}

				bfsWriter.println(bfsOutput.toString());
				bfsWriter.flush();

				StringBuilder astarOutput = new StringBuilder();
				astarOutput.append("astar for square grid of size: " + i);
				for(int loop = 0; loop < 10; loop++)
				{
					astarOutput.append(","+astar[0]);
				}

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



