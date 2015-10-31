package eval;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class EvalTest {

	public EvalTest()
	{

	}

	public void execute()
	{
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");

		String[] num = {"0","1","2","3","4","5","6","7","8","9"};
		String[] calcuration = {"","*"};

		try {
			for(int i = 1; i < num.length; ++i)
			{
				for(int ix = 0; ix < calcuration.length; ++ix)
				{
					for(int j = 0; j < num.length; ++j)
					{
						for(int jx = 0; jx < calcuration.length; ++jx)
						{
							for(int k = 0; k < num.length; ++k)
							{
								for(int kx = 0; kx < calcuration.length; ++kx)
								{
									for(int l = 1; l < num.length; ++l)
									{
										String original = num[i] + num[j] + num[k] + num[l];
										String formula = (num[i] + calcuration[ix] + num[j] + calcuration[jx] + num[k] + calcuration[kx] + num[l]);
										if(formula.length() > 4)
										{
											String result = engine.eval(num[i] + calcuration[ix] + num[j] + calcuration[jx] + num[k] + calcuration[kx] + num[l]).toString();
											if(result.length() == 4 && original.equals(result.charAt(3) + result.charAt(2) + result.charAt(1) + result.charAt(0)))
											{
												System.out.println(String.format("%s → %s", original, formula));
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		EvalTest tool = new EvalTest();
		tool.execute();
	}
}
