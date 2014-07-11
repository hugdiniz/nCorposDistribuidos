package util;

public class Util
{
	private static Long id = new Long(0);
	public static Long gerarId()
	{
		return ++id;
	}

}
