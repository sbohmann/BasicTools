
package at.yeoman.tools.io;

import java.io.Closeable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

public final class Closer implements AutoCloseable
{
	Deque<Closeable> closeables = new LinkedList<>();
	
	Consumer<Exception> errorHandler = null;
	
	/**
	 * Creates a Closer without an error handler - will print exceptions from calls to close() to stderr
	 */
	public Closer()
	{
	}
	
	/**
	 * Creates a Closer with an error handler that is informed of all exception from calls to close()
	 * @param errorhandler
	 */
	public Closer(Consumer<Exception> errorhandler)
	{
		this.errorHandler = errorhandler;
	}
	
	/**
	 * Adds closeable and returns it for easy call chaining"
	 * @param closeable
	 * @return
	 */
	public <T extends Closeable> T add(T closeable)
	{
		closeables.addFirst(closeable);
		
		return closeable;
	}
	
	/**
	 * Calls close() on all added Closeable instances
	 */
	public void close()
	{
		for (Closeable closeable : closeables)
		{
			try
			{
				closeable.close();
			}
			catch(Exception exc)
			{
				if (errorHandler != null)
				{
					errorHandler.accept(exc);
				}
				else
				{
					exc.printStackTrace();
				}
			}
		}
	}
}
