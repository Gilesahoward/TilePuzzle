import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Grid {

	static int width;
	static int height;

	static LinkedHashMap <String, Position> initialBlockPositions;
	static Position initialAgentPosition;

	static LinkedHashMap <String, Position> finalBlockPositions;


	public static void initSquareGrid(int n)
	{
		initGrid(n, n);
	}

	public static void initGrid(int width, int height)
	{
		Grid.width = width;
		Grid.height = height;

		Grid.initialBlockPositions = new LinkedHashMap<String, Position>();
		Grid.initialAgentPosition = new Position(width-1, 0);
		Grid.finalBlockPositions = new LinkedHashMap<String, Position>();

		char[] blocks = "abcdefghijklmnopqrstuvwxyz".toCharArray();

		for(int i = 0; i < width-1; i++)
		{
			initialBlockPositions.put(String.valueOf(blocks[i]), new Position(i, 0));
		}


		int currentElement = 1;
		for(String s : initialBlockPositions.keySet())
		{	
			finalBlockPositions.put(s, new Position(1, height - 1 - currentElement));
			currentElement += 1;

		}



	}

	public static void printInitialPositions()
	{
		for(String s : initialBlockPositions.keySet())
		{
			Position temp = initialBlockPositions.get(s);
			System.out.println("Block: " + s + " occupies position (" + temp.getX() + "," + temp.getY() + ")");
		}
	}

	public static void printFinalPositions()
	{
		for(String s : finalBlockPositions.keySet())
		{
			Position temp = finalBlockPositions.get(s);
			System.out.println("Block: " + s + " occupies position (" + temp.getX() + "," + temp.getY() + ")");
		}
	}

	public static State generateBaseState()
	{
		return new State(new LinkedHashMap<String, Position>(initialBlockPositions), new Position(initialAgentPosition));
	}

	public static Node generateBaseNode()
	{
		return new Node(generateBaseState());
	}

	public static State generateFinalState()
	{
		return new State(new LinkedHashMap<String, Position>(finalBlockPositions), new Position(initialAgentPosition));
	}

	public static Node bfs(Node n, State goal)
	{
		Queue<Node> queue = new LinkedList<Node>();
		HashSet<State> set = new HashSet<State>();

		queue.add(n);
		set.add(n.getState());

		while(!queue.isEmpty())
		{
			Node temp = queue.poll();
			if(temp.getState().equals(goal))
			{
				Deque<Grid.Direction> stack = new ArrayDeque<Grid.Direction>();
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
				}
				return temp;
			}
			for(Node child : temp.successors())
			{
				if(!set.contains(child.getState()))
				{
					set.add(child.getState());
					queue.add(child);
				}
			}
		}
		return null;

	}

	public static void main(String args[])
	{
		Grid.initSquareGrid(5);

		long start =System.currentTimeMillis();
		Node n = Grid.bfs(Grid.generateBaseNode(), Grid.generateFinalState());
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(n.getState().toString());
		System.out.println(n.getCost());

	}

	public enum Direction
	{
		UP, DOWN, LEFT, RIGHT
	}
}



