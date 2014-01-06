import java.util.ArrayList;


public class Node{

	private State2 state;
	private Node parent;
	private int cost;
	private Grid.Direction direction;
	
	
	public Node(State2 currentState)
	{
		this(currentState, null, 0, null);
	}

	public Node(State2 currentState, Node parent, int cost, Grid.Direction direction)
	{
		this.state = currentState;
		this.parent = parent;
		this.cost = cost;
		this.direction = direction;
	}
	
	public ArrayList<Node> successors()
	{
		ArrayList<Node> temp = new ArrayList<Node>();
		State2 left = state.goLeft();
		State2 up = state.goUp();
		State2 right = state.goRight();
		State2 down = state.goDown();

		if(up != null)
		{
			temp.add(new Node(up, this, cost+1, Grid.Direction.UP));
		}
		if(down != null)
		{
			temp.add(new Node(down, this, cost+1, Grid.Direction.DOWN));
		}

		if(left != null)
		{
			temp.add(new Node(left, this, cost+1, Grid.Direction.LEFT));
		}


		if(right != null)
		{
			temp.add(new Node(right, this, cost+1, Grid.Direction.RIGHT));
		}
		return temp;

	
	}
	
	public State2 getState()
	{
		return state;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public Grid.Direction getDirection()
	{
		return direction;
	}

	@Override
	public int hashCode() {
		final int prime = 57777;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}


}
