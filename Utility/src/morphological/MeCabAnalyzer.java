package morphological;

import java.util.ArrayList;
import java.util.List;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;

public class MeCabAnalyzer {

	StandardTagger tagger;
	Lattice lattice;
	Node node;

	public MeCabAnalyzer()
	{
		tagger = new StandardTagger("");
		lattice = tagger.createLattice();
	}

	public Morphorm[] analyze(String text)
	{
		List<Morphorm> morphormList = new ArrayList<Morphorm>();
		lattice.setSentence(text);
		tagger.parse(lattice);
		Node node = lattice.bosNode().next();
		System.out.println(lattice.toString());

		while(node != null)
		{
			Morphorm morphorm = new Morphorm();
			String feature = node.feature();
			String[] param = feature.split(",");
			if(param != null && param.length == 9)
			{
				morphorm.setText(node.surface());
				morphorm.setPos(param[0]);
				morphorm.setPosDetail1(param[1]);
				morphorm.setPosDetail2(param[2]);
				morphorm.setPosDetail3(param[3]);
				morphorm.setType(param[4]);
				morphorm.setForm(param[5]);
				morphorm.setBaseText(param[6]);
				morphorm.setReadText(param[7]);
				morphorm.setPronounceText(param[8]);
				morphormList.add(morphorm);
			}
			node = node.next();
		}
		lattice.destroy();
		tagger.destroy();

		return morphormList.toArray(new Morphorm[0]);
	}
}
