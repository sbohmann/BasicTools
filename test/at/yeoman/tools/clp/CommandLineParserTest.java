
package at.yeoman.tools.clp;

import org.junit.Assert;
import org.junit.Test;

public class CommandLineParserTest
{
	@Test
	public void basicBehavior()
	throws Exception
	{
		CommandLineParser parser = new CommandLineParser();
		Key<String> hostKey = parser.addStringArgument("host");
		Key<Integer> portKey = parser.addIntArgument("port");
		Key<Boolean> connectKey = parser.addOption("connect");
		
		parser.parse(new String[] { "-port", "12345", "-host", "localhost" });
		
		String host = parser.get(hostKey);
		int port = parser.get(portKey);
		boolean connect = parser.get(connectKey);
		
		System.out.println(host + ":" + port + ", connect: " + connect);
	}
	
	@Test
	public void basicBehaviorWithOptionSet()
	throws Exception
	{
		CommandLineParser parser = new CommandLineParser();
		Key<String> hostKey = parser.addStringArgument("host");
		Key<Integer> portKey = parser.addIntArgument("port");
		Key<Boolean> connectKey = parser.addOption("connect");
		
		parser.parse(new String[] { "-connect", "-port", "12345", "-host", "localhost" });
		
		String host = parser.get(hostKey);
		int port = parser.get(portKey);
		boolean connect = parser.get(connectKey);
		
		System.out.println(host + ":" + port + ", connect: " + connect);
	}
	
	@Test
	public void basicBehaviorWithOptionDoublySet()
	throws Exception
	{
		CommandLineParser parser = new CommandLineParser();
		parser.addStringArgument("host");
		parser.addIntArgument("port");
		parser.addOption("connect");
		
		try
		{
			parser.parse(new String[] { "-connect", "-port", "12345", "-host", "localhost", "-connect" });
		}
		catch(ParsingException exc)
		{
			System.err.println(exc.getMessage());
			
			return;
		}
		
		Assert.fail();
	}
	
	@Test
	public void missingHostArgument()
	throws Exception
	{
		CommandLineParser parser = new CommandLineParser();
		parser.addStringArgument("host");
		parser.addIntArgument("port");
		
		try
		{
			parser.parse(new String[] { "-port", "12345" });
		}
		catch(ParsingException exc)
		{
			System.err.println(exc.getMessage());
			
			return;
		}
		
		Assert.fail();
	}
	
	@Test
	public void missingPortArgument()
	throws Exception
	{
		CommandLineParser parser = new CommandLineParser();
		parser.addStringArgument("host");
		parser.addIntArgument("port");
		
		try
		{
			parser.parse(new String[] { "-host", "localhost" });
		}
		catch(ParsingException exc)
		{
			System.err.println(exc.getMessage());
			
			return;
		}
		
		Assert.fail();
	}
}
