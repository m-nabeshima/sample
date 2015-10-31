package junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import tree.ITree;
import tree.TrieTree;

public class TrieTreeTest {

	ITree tree;

	@Test
	@Ignore
	public void test() {
		tree = new TrieTree();
		//load from list
		List<String> list = new ArrayList<String>();
		list.add("竹やぶ焼けた");
		list.add("竹");
		list.add("竹やぶ");
		list.add("坂本");
		list.add("坂本竜馬");

//		tree.loadTree(list);

		//load from file
		String filePath = "C:\\pleiades\\workspace\\Utility\\files\\words.txt";
		tree.loadTree(filePath);

		System.out.println(tree.seek("佐々木健介と北斗昌は仲良しだ。"));
	}

	@Test
//	@Ignore
	public void seekFileTest()
	{
		tree = new TrieTree();
		String filePath = "C:\\pleiades\\workspace\\Utility\\files\\words.txt";
		String utterancePath = "C:\\pleiades\\workspace\\Utility\\files\\text.txt";
		tree.loadTree(filePath);

		tree.loadTree(filePath);

		System.out.println(tree.seekCount(tree.seekFromFile(utterancePath)));
	}

}
