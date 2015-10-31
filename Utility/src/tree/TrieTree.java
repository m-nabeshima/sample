package tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import morphological.MeCabAnalyzer;
import morphological.Morphorm;

public class TrieTree implements ITree
{
	private MeCabAnalyzer analyzer;
	private Map<String,TrieTree> child;
	private String text;
	private boolean end = false;
	private boolean match = false;
	private static StringBuilder matchWord;

	public TrieTree()
	{
		analyzer = new MeCabAnalyzer();
		child = new HashMap<String,TrieTree>();
	}

	public List<String> seekFromFile(String filePath)
	{
		File file = new File(filePath);
		List<String> ret = new ArrayList<String>();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = new String();
			StringBuilder lines = new StringBuilder();
			while((line = br.readLine()) != null)
			{
				lines.append(line);
			}
			br.close();
			ret = seek(lines.toString());
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Map<String,Integer> seekCount(List<String> list)
	{
		Map<String,Integer> ret = new HashMap<String, Integer>();
		Integer counter = 0;
		for(String word : list)
		{
			if((counter = ret.get(word)) != null)
			{
				ret.put(word, ++counter);
			}
			else{
				ret.put(word, 1);
			}
		}
		return ret;
	}

	public List<String> seek(String str)
	{
		List<String> ret = new ArrayList<String>();
		Morphorm[] morphorms = analyzer.analyze(str);

//		String[] param = str.split(TOKEN_DELIMITER);
		String[] words = new String[morphorms.length];
		for(int i = 0; i < morphorms.length; ++i)
		{
			words[i] = morphorms[i].getText();
		}
		if(words != null && words.length > 0)
		{
			while(words.length > 0)
			{
				matchWord = new StringBuilder();

				String[] param = words[0].split(TOKEN_DELIMITER);
				if(seekChild(param, this))
				{
					ret.add(matchWord.toString());
					words = Arrays.copyOfRange(words, 1, words.length);
				}
				else
				{
					words = Arrays.copyOfRange(words, 1, words.length);
				}
			}
		}
		return ret;
	}

	public boolean seekChild(String[] param, TrieTree node)
	{
		setMatch(false);
		String[] next;
		TrieTree current;
		if(param != null && param.length > 0)
		{
			String token = param[0].split(TOKEN_DELIMITER)[0];
			if(node.getChild(token) != null)
			{
				matchWord.append(token);
				if(param.length == 1)
				{
					if(node.getChild(token).isEnd())
					{
						setMatch(true);
						return getMatch();
					}
				}
				current = node.getChild(token);
				next = Arrays.copyOfRange(param, 1, param.length);
				seekChild(next, current);
			}
			else
			{
				return getMatch();
			}
		}
		return getMatch();
	}

	public boolean getMatch()
	{
		return match;
	}

	public void setMatch(boolean flag)
	{
		match = flag;
	}

	private boolean isEnd()
	{
		return end;
	}

	public void setText(String key)
	{
		this.text = key;
	}

	public void addChild(String key, TrieTree currentNode)
	{
		TrieTree node = new TrieTree();
		currentNode.child.put(key, node);
		currentNode.child.get(key).setText(key);
	}



	public void addEnd(String key, TrieTree currentNode)
	{
		currentNode.child.get(key).setEndOfNode();
	}

	public void setEndOfNode()
	{
		end = true;
	}

	public void loadTree(String filePath)
	{
		File file = new File(filePath);
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = new String();
			while((line = br.readLine()) != null)
			{
				String[] param = line.split(TOKEN_DELIMITER);
				addNode(param, this);
			}

			br.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void loadTree(List<String> list)
	{
		for(String line : list)
		{
			String[] param = line.split(TOKEN_DELIMITER);
			addNode(param,this);
		}
	}

	public String getText()
	{
		return text;
	}

	private void addNode(String[] param, TrieTree node)
	{
		TrieTree current;
		String[] next;
		if(param.length > 0)
		{
			String token = param[0];
			if(node.getChild(token) != null)
			{
				next = Arrays.copyOfRange(param, 1, param.length);
				if(next.length > 0)
				{
					current = node.getChild(token);
					addNode(next,current);
				}
				else
				{
					addEnd(token, node);
				}
			}
			else
			{
				addChild(token,node);
				next = Arrays.copyOfRange(param, 1, param.length);
				if(next.length > 0)
				{
					current = node.getChild(token);
					addNode(next,current);
				}
				else
				{
					addEnd(token, node);
				}
			}
		}
	}

	public TrieTree getChild(String key)
	{
		return child.get(key);
	}
}
