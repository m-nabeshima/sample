package prefix;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class PrefixSpan {

	final String DELIMITER = "|";

	public PrefixSpan()
	{

	}

	public void scan(String alpha, String[] data, int minlLength, int minCount)
	{
		Map<String, Integer> ret = new HashMap<String, Integer>();
		Map<String, Integer> counterMap = new HashMap<String, Integer>();
		counterMap = extract(data, minCount);
		Iterator<Map.Entry<String, Integer>> ite = counterMap.entrySet().iterator();
		while(ite.hasNext())
		{
			Entry<String, Integer> entry = ite.next();
			String prefix = alpha != null ? (alpha + entry.getKey()) : entry.getKey();
			ret.put(prefix, entry.getValue());

			scan(prefix, project(data, entry.getKey()), minlLength, minCount);
		}
		ret = rejectLessLength(ret, minlLength);
		show(ret);
	}

	public Map<String, Integer> extract(String[] data, int minCount)
	{
		Map<String, Integer> ret = new HashMap<String, Integer>();

		for(String d : data)
		{
			HashSet<String> set = new HashSet<String>();
			String[] tokens = d.split(DELIMITER);
			for(String token : tokens)
			{
				set.add(token);
			}
			Iterator<String> ite = set.iterator();
			while(ite.hasNext())
			{
				String s = ite.next();
				Integer n = ret.get(s) != null ? (ret.get(s) + 1) : 1;
				ret.put(s, n);
			}
		}
		Iterator<Map.Entry<String, Integer>> ite = ret.entrySet().iterator();
		LinkedList<String> list = new LinkedList<String>();
		while(ite.hasNext())
		{
			Entry<String, Integer> entry = ite.next();
			if(entry.getValue() < minCount)
			{
				list.add(entry.getKey());
			}
		}
		for(String s : list)
		{
			ret.remove(s);
		}
		return ret;
	}

	public void show(Map<String, Integer> map)
	{
		Iterator<Map.Entry<String, Integer>> ite = map.entrySet().iterator();
		while(ite.hasNext())
		{
			Entry<String, Integer> entry = ite.next();
			System.out.println(String.format("%s\t%s", entry.getKey(), entry.getValue()));
		}
	}

	public Map<String, Integer> rejectLessLength(Map<String, Integer> map, int minlLength)
	{
		Map<String, Integer> ret = new HashMap<String, Integer>();
		Iterator<Map.Entry<String, Integer>> ite = map.entrySet().iterator();
		while(ite.hasNext())
		{
			Entry<String, Integer> entry = ite.next();
			if(entry.getKey().length() >= minlLength)
			{
				ret.put(entry.getKey(), entry.getValue());
			}
		}
		return ret;
	}

	public String[] project(String[] data, String token)
	{
		LinkedList<String> list = new LinkedList<String>();
		for(String d : data)
		{
			if(d.indexOf(token) != -1 && d.indexOf(token) < d.length())
			{
				list.add(d.substring(d.indexOf(token) + 1));
			}
		}
		return list.toArray(new String[0]);
	}

	public static void main(String[] args)
	{
		PrefixSpan tool = new PrefixSpan();
//		String[] data = {"acd", "abc", "cba", "aab"};
		String[] data = {"おじいさん", "おばあさん", "むすめさん", "むすこさん"};
		tool.scan(null, data, 2, 2);


	}
}
