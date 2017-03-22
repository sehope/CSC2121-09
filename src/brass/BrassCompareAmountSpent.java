package brass;

import java.util.Comparator;

public class BrassCompareAmountSpent implements Comparator<BrassPlayer>
{
	private boolean asc_or_desc;
	
	public BrassCompareAmountSpent(boolean a)
	{
		asc_or_desc = a;
	}
	
	public int compare(BrassPlayer b1, BrassPlayer b2)
	{
		int dif = 0;
		int b1_amount = b1.getAmountSpent();
		int b2_amount = b2.getAmountSpent();
		
		if(asc_or_desc)
		{
			dif = b1_amount - b2_amount; //if positive, b1 spent more, if negative, b2 spent more
		}
		else
		{
			dif = (b1_amount - b2_amount) * -1;
		}
		
		return dif;
	}
}