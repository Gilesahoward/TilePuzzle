public class HeuristicNode implements Comparable<HeuristicNode> {

	Node node;
	int fscore;
	
	public HeuristicNode(Node n, int fscore) {
		this.node = n;
		this.fscore = fscore;
	}

	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fscore;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
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
		HeuristicNode other = (HeuristicNode) obj;
		if (fscore != other.fscore)
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

}
