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
	State2 initialState;
	State2 finalState;
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

		initialState = new State2(state, dimension, squared - 1);

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

		this.finalState = new State2(finalState, dimension, squared - 1);

		System.out.println(Arrays.toString(state));
		System.out.println(Arrays.toString(finalState));

		initial = new Node(initialState);

	}


	public Node dfs()
	{
		Deque<Node> stack = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();


		stack.addFirst(initial);
		//	set.add(initial);


		while(!stack.isEmpty())
		{

			Node temp = stack.removeFirst();
			//System.out.println(Arrays.toString(temp.state.state));
			/*	System.out.println("Set contains: " + set.size());
			System.out.println("Queue contains: " + queue.size());*/
			if(temp.getState().equals(finalState))
			{
				System.out.println("Success, ending");
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
				return temp;
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


	public Node bfs()
	{
		Queue<Node> queue = new ArrayDeque<Node>();
		Set<Node> set = new HashSet<Node>();

		queue.add(initial);
		set.add(initial);


		while(!queue.isEmpty())
		{

			Node temp = queue.poll();
			//System.out.println(Arrays.toString(temp.state.state));
			/*	System.out.println("Set contains: " + set.size());
			System.out.println("Queue contains: " + queue.size());*/
			if(temp.getState().equals(finalState))
			{
				System.out.println("Success, ending");
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
				return temp;
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

	public Node iddfs_helper(Integer i)
	{		

		Deque<Node> stack = new ArrayDeque<Node>();
		//Set<Node> set = new HashSet<Node>();


		stack.addFirst(initial);

		while(!stack.isEmpty())
		{
			Node temp = stack.removeFirst();

			if(temp.cost <= i)
			{

				if(temp.getState().equals(finalState)) return temp;
				else
				{
					//set.add(temp);
					for(Node n: temp.successors())
					{
						stack.addFirst(n);
					}
				}
			}
		}

		return null;

	}

	public Node iddfs()
	{
		for(int i = 0; i < Integer.MAX_VALUE; i++)
		{
			Node answer;
			if((answer = iddfs_helper(i)) != null) return answer;
		}
		return null;
	}
	
	public ArrayList<Node> astar()
	{
		Set<State2> seen = new HashSet<State2>();
		Queue<HeuristicNode> toConsider = new PriorityQueue<HeuristicNode>();
		Map<Node, Node> routesTaken = new HashMap<Node, Node>();
		
		toConsider.add(new HeuristicNode(initial, heuristic(initial)));
		
		while(!toConsider.isEmpty())
		{
			HeuristicNode current = toConsider.poll();
			System.out.println(Arrays.toString(current.state.state));
			System.out.println(Arrays.toString(finalState.state));
			if(current.state.equals(finalState))
			{
				System.out.println("map size: " + routesTaken.size());
				System.out.println("current node cost: " + current.cost);
				return reconstruct_route(routesTaken, new Node(current.state, current.parent, current.cost, current.direction));
			}
			
			seen.add(current.state);
			
			for(Node n : current.successors())
			{
				if(!seen.contains(n.state))
				{
					HeuristicNode newN = new HeuristicNode(n, current.cost + heuristic(n));
					routesTaken.put(current, n);
					toConsider.add(newN);
				}
			}
		}
		return null;
		
	}
	
	public ArrayList<Node> reconstruct_route(Map<Node, Node> map, Node to)
	{
		if(map.containsKey(to))
		{
			ArrayList<Node> list = reconstruct_route(map, map.get(to));
			list.add(to);
			return list;
		}
		else
		{
			ArrayList<Node> list = new ArrayList<Node>();
			list.add(to);
			return list;
		}
	}
	 
	public int heuristic(Node n)
	{
		int total = 0;
		for(int i = 1; i <= 3; i++)
		{
			
			
			int current = -1;
			int goalstate = -1;
			for(int z = 0; z < n.state.state.length; z++)
			{
				if(n.state.state[z] == i) 
					{ current = z; break; }
			}
			
			for(int z = 0; z < finalState.state.length; z++)
			{
				if(finalState.state[z] == i) { goalstate = z; break; }
			}
			
			
			int currentx = current % width;
			int currenty = (int) Math.floor(current/width);
			
			
			
			int goalx = goalstate % width;
			int goaly = (int) Math.floor(goalstate/width);
		
			
			total += Math.abs(currentx-goalx ) + Math.abs(currenty - goaly);
			
			
		}
		total += n.cost;
		return total;
	}

	public static void main(String args[])
	{
		Grid four = new Grid(4);
		ArrayList<Node> blah = four.astar();
		System.out.println(blah.size());
		//System.out.println(Arrays.toString(four.initialState.state));
/*		long start =System.currentTimeMillis();
		System.out.println("Cost of bfs: " + four.bfs().cost);
		System.out.println("Time taken in ms for bfs: " + (System.currentTimeMillis() - start));

		long start2 = System.currentTimeMillis();
		System.out.println("Cost of dfs: " + four.dfs().cost);
		System.out.println("Time taken in ms for dfs: " + (System.currentTimeMillis() - start2));

		long start3 = System.currentTimeMillis();
		System.out.println("Cost of iddfs: " + four.iddfs().cost);
		System.out.println("Time taken in ms for iddfs: " + (System.currentTimeMillis() - start3));
		
		
		*/
		
		/*		Grid.initSquareGrid(5);

		long start =System.currentTimeMillis();
		Node n = Grid.bfs(Grid.generateBaseNode(), Grid.generateFinalState());
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(n.getState().toString());
		System.out.println(n.getCost());
		 */
	}

	public enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}
}



