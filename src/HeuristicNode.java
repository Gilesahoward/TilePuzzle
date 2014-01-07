import java.util.ArrayList;




public class HeuristicNode implements Comparable<HeuristicNode> {

	Node node;
	int fscore;
	
	public HeuristicNode(Node n, int fscore) {
		this.node = n;
		this.fscore = fscore;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(HeuristicNode arg0) {
		return fscore - arg0.fscore;
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
