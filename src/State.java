import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class State {

	LinkedHashMap<String, Position> blockLocations;
	Position agentLocation;
	
	public State(LinkedHashMap<String, Position> LinkedHashMap, Position agentLocation)
	{
		this.blockLocations = LinkedHashMap;
		this.agentLocation = agentLocation;
	}
	
	public LinkedHashMap<String, Position> getBlockLocations()
	{
		return blockLocations;
	}
	
	public void setBlockLocations(LinkedHashMap<String, Position> blockLocations)
	{
		this.blockLocations = blockLocations;
	}
	
	public Position getAgentLocation()
	{
		return agentLocation;
	}

	public void setAgentLocation(Position agentLocation)
	{
		this.agentLocation = agentLocation;
	}
	
	public State goLeft()
	{
		Position left = agentLocation.goLeft();
		if(left == null)
		{
			return null;
		}
		
		LinkedHashMap<String, Position> newPositions = new LinkedHashMap<String, Position>(blockLocations);
		
		for(Entry<String, Position> entry : newPositions.entrySet())
		{
			if(entry.getValue().equals(left))
			{
				//System.out.println("Positions equal. Forcing tile to the right.");
				entry.setValue(entry.getValue().goRight());
				//System.out.println("New position for tile: " + entry.getValue().toString());
			}
		}
		
		return new State(newPositions, left);
	}
	
	public State goUp()
	{
		Position up = agentLocation.goUp();
		if(up == null)
		{
			return null;
		}
		
		LinkedHashMap<String, Position> newPositions = new LinkedHashMap<String, Position>(blockLocations);
		
		for(Entry<String, Position> entry : newPositions.entrySet())
		{
			if(entry.getValue().equals(up))
			{
				//System.out.println("Positions equal. Forcing tile down.");
				entry.setValue(entry.getValue().goDown());
				//System.out.println("New position for tile: " + entry.getValue().toString());
			}
		}
		
		return new State(newPositions, up);
	}
	
	public State goRight()
	{
		Position right = agentLocation.goRight();
		if(right == null)
		{
			return null;
		}
		
		LinkedHashMap<String, Position> newPositions = new LinkedHashMap<String, Position>(blockLocations);
		
		for(Entry<String, Position> entry : newPositions.entrySet())
		{
			if(entry.getValue().equals(right))
			{
			//	System.out.println("Positions equal. Forcing tile to the left.");
				entry.setValue(entry.getValue().goLeft());
			//	System.out.println("New position for tile: " + entry.getValue().toString());
				
			}
		}
		
		return new State(newPositions, right);
	}
	
	public State goDown()
	{
		Position down = agentLocation.goDown();
		if(down == null)
		{
			return null;
		}
		
		LinkedHashMap<String, Position> newPositions = new LinkedHashMap<String, Position>(blockLocations);
		
		for(Entry<String, Position> entry : newPositions.entrySet())
		{
			if(entry.getValue().equals(down))
			{
				//System.out.println("Positions equal. Forcing tile up.");
				entry.setValue(entry.getValue().goUp());
				//System.out.println("New position for tile: " + entry.getValue().toString());
				
			}
		}
		
		return new State(newPositions, down);
	}

	
	public String toString()
	{
		String toReturn = "State representation. ";
		for(Entry<String, Position> e : blockLocations.entrySet())
		{
			toReturn = toReturn + " " + "Block " + e.getKey().toString() + " located at Position " + e.getValue().toString(); 
		}
		toReturn = toReturn + "Agent Location: " + agentLocation.toString();
		return toReturn;
	}


	@Override
	public int hashCode() {
		final int prime = 372111;
		int result = 1;
		result = prime * result
				+ ((agentLocation == null) ? 0 : agentLocation.hashCode());
		result = prime * result
				+ ((blockLocations == null) ? 0 : blockLocations.hashCode());
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
		State other = (State) obj;
		if (agentLocation == null) {
			if (other.agentLocation != null)
				return false;
		} else if (!agentLocation.equals(other.agentLocation))
			return false;
		if (blockLocations == null) {
			if (other.blockLocations != null)
				return false;
		} else if (!blockLocations.equals(other.blockLocations))
			return false;
		return true;
	}
	
}
