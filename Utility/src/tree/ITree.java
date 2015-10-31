package tree;

import java.util.List;
import java.util.Map;

public interface ITree {

	String TOKEN_DELIMITER = "|";

	List<String> seekFromFile(String filePath);
	List<String> seek(String str);
	Map<String,Integer> seekCount(List<String> list);

	void loadTree(String filePath);
	void loadTree(List<String> list);
}
