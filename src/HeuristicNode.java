import java.util.ArrayList;




public class HeuristicNode extends Node implements Comparable<HeuristicNode> {

	Node node;
	 int fscore;
	public HeuristicNode(Node n, int fscore) {
		super(n.state, n.parent, n.cost, n.direction);
		this.fscore = n.cost + fscore;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(HeuristicNode arg0) {
		return fscore - arg0.fscore;
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

}
