
package at.yeoman.tools.io;

import java.nio.charset.Charset;

public final class Charsets
{
	public static final Charset ASCII = Charset.forName("ASCII");
	
	public static final Charset UTF8 = Charset.forName("UTF-8");
	
	/**
	 * Successor of LATIN1 with euro sign
	 */
	public static final Charset LATIN9 = Charset.forName("ISO8859-15");
	
	/**
	 * Does not support the euro sign 
	 */
	public static final Charset LATIN1 = Charset.forName("ISO8859-1");
	
	private Charsets()
	{
	}
}
