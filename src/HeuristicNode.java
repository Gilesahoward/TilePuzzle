import java.util.ArrayList;




public class HeuristicNode implements Comparable<HeuristicNode> {

	Node node;
	int fscore;
	
	public HeuristicNode(Node n, int fscore) {
		this.node = n;
		this.fscore = n.getCost() + fscore;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(HeuristicNode arg0) {
		return fscore - arg0.fscore;
	}

	public ArrayList<Node> successors()
	{
		ArrayList<Node> temp = new ArrayList<Node>();
		State s = node.getState();
		int oldCost = node.getCost();
		State left = s.goLeft();
		State up = s.goUp();
		State right = s.goRight();
		State down = s.goDown();

		if(up != null)
		{
			temp.add(new Node(up, node, oldCost+1, Grid.Direction.UP));
		}
		if(down != null)
		{
			temp.add(new Node(down, node, oldCost+1, Grid.Direction.DOWN));
		}

		if(left != null)
		{
			temp.add(new Node(left, node, oldCost+1, Grid.Direction.LEFT));
		}


		if(right != null)
		{
			temp.add(new Node(right, node, oldCost+1, Grid.Direction.RIGHT));
		}
		return temp;


	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public int getFscore() {
		return fscore;
	}
	public void setFscore(int fscore) {
		this.fscore = fscore;
	}

}
