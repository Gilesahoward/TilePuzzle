import java.util.ArrayList;


public class Node{

	State currentState;
	Node parent;
	int cost;
	Grid.Direction direction;
	
	
	public Node(State currentState)
	{
		this(currentState, null, 0, null);
	}

	public Node(State currentState, Node parent, int cost, Grid.Direction direction)
	{
		this.currentState = currentState;
		this.parent = parent;
		this.cost = cost;
		this.direction = direction;
	}
	
	public ArrayList<Node> successors()
	{
		ArrayList<Node> temp = new ArrayList<Node>(4);
		State left = currentState.goLeft();
		State up = currentState.goUp();
		State right = currentState.goRight();
		State down = currentState.goDown();
		
		if(up != null)
		{
			temp.add(new Node(up, this, cost+1, Grid.Direction.UP));
		}
		if(left != null)
		{
			temp.add(new Node(left, this, cost+1, Grid.Direction.LEFT));
		}
		if(down != null)
		{
			temp.add(new Node(down, this, cost+1, Grid.Direction.DOWN));
		}
		if(right != null)
		{
			temp.add(new Node(right, this, cost+1, Grid.Direction.RIGHT));
		}


		
		return temp;
	}
	
	public State getState()
	{
		return currentState;
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
		final int prime = 372111;
		int result = 1;
		result = prime * result
				+ ((currentState == null) ? 0 : currentState.hashCode());
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
		if (currentState == null) {
			if (other.currentState != null)
				return false;
		} else if (!currentState.equals(other.currentState))
			return false;
		return true;
	}


}
