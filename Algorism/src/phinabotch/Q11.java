package phinabotch;

import java.util.ArrayList;
import java.util.List;

public class Q11
{
	private List<String> array;

	public Q11()
	{
		array = new ArrayList<>();
	}

	public void execute()
	{
		int counter = 0;
		int i = 1;
		while(true)
		{
			if(array.size() == 0 || array.size() == 1)
			{
				array.add("1");
			}
			else
			{
				String value =String.valueOf((Long.valueOf(array.get(i - 3))) + Long.valueOf(array.get(i - 2)));
				array.add(value);
				String[] param = value.split("|");
				Long denominator = 0l;
				for(String a : param)
				{
					denominator += Integer.valueOf(a);
				}
				if(Long.valueOf(value) % Long.valueOf(denominator) == 0)
				{
					System.out.println(value);
					++counter;
				}
				if(counter == 11)
				{
					break;
				}
			}
			++i;
		}
	}

	public static void main(String[] args)
	{
		Q11 q = new Q11();
		q.execute();
	}
}
