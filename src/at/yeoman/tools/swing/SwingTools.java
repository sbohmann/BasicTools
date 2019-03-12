
package at.yeoman.tools.swing;

import java.io.PrintWriter;

import javax.swing.SwingUtilities;

import at.yeoman.tools.io.IoTools;

public class SwingTools
{
	public interface RoughRunnable
	{
		void run()
		throws Exception;
	}
	
	/**
	 * Logs errors to stderr
	 */
	public static void run(RoughRunnable code)
	{
		run(code, IoTools.stderr());
	}
	
	public static void run(RoughRunnable code, PrintWriter errorLog)
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						code.run();
					}
					catch(Exception exc)
					{
						exc.printStackTrace(errorLog);
					}
				}
			});
		}
		catch (Exception exc)
		{
			exc.printStackTrace(errorLog);
		}
	}
}
