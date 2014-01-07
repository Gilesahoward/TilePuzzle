
public class Result {

	public Result(String path, long nodesExpanded, long timeTaken) {
		this.path = path;
		this.nodesExpanded = nodesExpanded;
		this.timeTaken = timeTaken;
	}
	
	String path;
	long nodesExpanded;
	long timeTaken;
	
	public String getPath() {
		return path;
	}
	public long getNodesExpanded() {
		return nodesExpanded;
	}
	public long getTimeTaken() {
		return timeTaken;
	}
		
	
	
}
