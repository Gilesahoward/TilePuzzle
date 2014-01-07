import java.util.Arrays;


public class State {

	private int[] state;
	private Integer dimension;
	private int agent;

	public State(int[] state, Integer dimension, int agent)
	{
		this.state = state;
		this.dimension = dimension;
		this.agent = agent;
	}

	public State(State old)
	{
		this.state = old.state;
		this.dimension = old.dimension;
		this.agent = old.agent;
	}
	

	public State goLeft()
	{
		if(agent % dimension == 0)
		{
			return null;
		}
		else
		{
			int[] newState = Arrays.copyOf(state, state.length);
			if(newState[agent-1] != 0)
			{
				int temp = newState[agent-1];
				newState[agent-1] = newState[agent];
				newState[agent] = temp;
			}
			else
			{
				newState[agent-1] = -1;
				newState[agent] = 0;
			}

			return new State(newState, dimension, agent-1);
		}
	}

	public State goUp()
	{
		if(agent-dimension < 0)
		{
			return null;
		}
		else
		{
			int[] newState = Arrays.copyOf(state, state.length);
			if(newState[agent-dimension] != 0)
			{
				//System.out.println("Positions equal. Forcing tile up.");
				int temp = newState[agent-dimension];
				newState[agent-dimension] = newState[agent];
				newState[agent] = temp;
			}

			else
			{
				newState[agent-dimension] = -1;
				newState[agent] = 0;
			}
			return new State(newState, dimension, agent-dimension);
		}

	}

	public State goRight()
	{
		if(agent % dimension == dimension-1)
		{
			return null;
		}
		else
		{
			int[] newState = Arrays.copyOf(state, state.length);
			
			if(newState[agent+1] != 0)
			{
				//System.out.println("Positions equal. Forcing tile right.");
				int temp = newState[agent+1];
				newState[agent+1] = newState[agent];
				newState[agent] = temp;
			}
			else
			{
				newState[agent+1] = -1;
				newState[agent] = 0;
			}

			return new State(newState, dimension, agent+1);
		}

	}

	public State goDown()
	{
		if(agent+dimension > (dimension*dimension)-1 )
		{
			return null;
		}
		else
		{
			int[] newState = Arrays.copyOf(state, state.length);
			if(newState[agent+dimension] != 0)
			{
				int temp = newState[agent+dimension];
				newState[agent+dimension] = newState[agent];
				newState[agent] = temp;
			}
			else
			{
				newState[agent+dimension] = -1;
				newState[agent] = 0;
			}

			return new State(newState, dimension, agent+dimension);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + agent;
		result = prime * result + Arrays.hashCode(state);
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
		if (agent != other.agent)
			return false;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}

	public int getAgent() {
		return agent;
	}

	public void setAgent(int agent) {
		this.agent = agent;
	}



}
