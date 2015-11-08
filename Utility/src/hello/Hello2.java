package hello;

public class Hello2 extends Hello {

	public void hello()
	{
		super.hello();
	}

	public static void main(String[] args)
	{
		Hello2 hello = new Hello2();
		hello.hello();
	}
}
