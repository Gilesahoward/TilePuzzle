import java.util.Arrays;


public class State2 {

	int[] state;
	Integer dimension;
	int agent;

	public State2(int[] state, Integer dimension, int agent)
	{
		this.state = state;
		this.dimension = dimension;
		this.agent = agent;
	}

	public State2(State2 old)
	{
		this.state = old.state;
		this.dimension = old.dimension;
		this.agent = old.agent;
	}

	public State2 goLeft()
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

			return new State2(newState, dimension, agent-1);
		}
	}

	public State2 goUp()
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
			return new State2(newState, dimension, agent-dimension);
		}

	}

	public State2 goRight()
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

			return new State2(newState, dimension, agent+1);
		}

	}

	public State2 goDown()
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

			return new State2(newState, dimension, agent+dimension);
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
		State2 other = (State2) obj;
		if (agent != other.agent)
			return false;
		if (!Arrays.equals(state, other.state))
			return false;
		return true;
	}



}
