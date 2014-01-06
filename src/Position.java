/*
public class Position {

	private int x;
	private int y;

	public Position(int x, int y)
	{
		this.y = y;
		this.x = x;
	}
	
	public Position(Position old) {
		this.x = old.getX();
		this.y = old.getY();
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}

	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	public Position goLeft()
	{
		if(x == 0)
		{
			return null;
		}
		else
		{
			return new Position(x-1, y);
		}
	}
	
	public Position goUp()
	{
		if(y == Grid.height-1)
		{
			return null;
		}
		else
		{
			return new Position(x, y+1);
		}
	}
	
	public Position goRight()
	{
		if(x == Grid.width-1)
		{
			return null;
		}
		else
		{
			return new Position(x+1, y);
		}
	}
	
	public Position goDown()
	{
		if(y == 0)
		{
			return null;
		}
		else
		{
			return new Position(x, y-1);
		}
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
*/