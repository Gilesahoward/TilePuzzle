
public class Derp {

	
	public static void main(String args[])
	{
		String test = args[0];
		
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		
		for(int i = 0; i < test.length(); i++)
		{
			switch(test.charAt(i))
			{
			case 'U': up++; break;
			case 'D': down++; break;
			case 'L': left++; break;
			case 'R': right++; break;
			}
		}
		
		if( (up == down) && (left == right))
		{
			System.out.println("ALL OKAY");
		}
	}
}
