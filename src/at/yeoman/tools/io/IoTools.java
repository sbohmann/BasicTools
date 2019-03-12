
package at.yeoman.tools.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public final class IoTools
{
	/**
	 * Reads a text file using the UTF-8 charset
	 * 
	 * @param file
	 *            the text file to read
	 * @return the text file's content as a String
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException
	{
		return readTextFile(file, Charsets.UTF8);
	}
	
	/**
	 * Reads a text file using the specified charset
	 * 
	 * @param file
	 *            the text file to read
	 * @param charset
	 *            the charset to use, e.g. UTF-8, UTF-16, Latin-9, etc.
	 * @return the text file's content as a String
	 * @throws IOException
	 * @see {@link Charsets}
	 */
	public static String readTextFile(File file, Charset charset) throws IOException
	{
		FileInputStream in = new FileInputStream(file);
		try (InputStreamReader reader = new InputStreamReader(in, charset))
		{
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[1024];
			while (true)
			{
				int n = reader.read(buffer);
				if (n < 0)
				{
					reader.close();
					break;
				}
				sb.append(buffer, 0, n);
			}
			return sb.toString();
		}
	}
	
	public static void writeIfNecessary(File file, String content)
		throws IOException
	{
		writeIfNecessary(file, content, null);
	}
	
	public static void writeIfNecessary(File file, String content, PrintWriter debugWriter)
		throws IOException
	{
		if (file.exists() && file.isFile() == false)
		{
			throw new IOException("File exists but is not a file: [" + getCanonicalOrAbsolutePath(file) + "]");
		}
		
		if (file.exists() && readTextFile(file).equals(content))
		{
			if (debugWriter != null) debugWriter.println("Identical content - skipping file [" + getCanonicalOrAbsolutePath(file) + "]");
			return;
		}
		
		if (debugWriter != null) debugWriter.println("File missing or different content: [" + getCanonicalOrAbsolutePath(file) + "]");
		
		Writer writer = createWriter(file);
		writer.write(content);
		writer.close();
	}
	
	public static String readAll(Reader reader)
		throws IOException
	{
		StringBuilder sb = new StringBuilder();
		
		char[] buffer = new char[1024];
		
		while (true)
		{
			int n = reader.read(buffer);
			if (n < 0)
			{
				break;
			}
			sb.append(buffer, 0, n);
		}
		
		return sb.toString();
	}
	
	public static String readAllText(InputStream in)
		throws IOException
	{
		return readAll(createReader(in));
	}
	
	public static byte[] readAll(InputStream in)
		throws IOException
	{
		ByteBuffer bb = new ByteBuffer();
		
		byte[] buffer = new byte[1024];
		
		while (true)
		{
			int n = in.read(buffer);
			if (n < 0)
			{
				break;
			}
			bb.append(buffer, 0, n);
		}
		
		return bb.toByteArray();
	}
	
	public static boolean isPrintable(int codePoint)
	{
		if (Character.isDefined(codePoint) == false)
		{
			throw new IllegalArgumentException("Undefined code point: " + codePoint);
		}
		
		Character.UnicodeBlock block = Character.UnicodeBlock.of(codePoint);
		if (block == Character.UnicodeBlock.HIGH_PRIVATE_USE_SURROGATES ||
			block == Character.UnicodeBlock.HIGH_SURROGATES ||
			block == Character.UnicodeBlock.LOW_SURROGATES ||
			block == Character.UnicodeBlock.SPECIALS ||
			Character.isISOControl(codePoint))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static Reader createReader(InputStream in)
	{
		return new InputStreamReader(in, Charsets.UTF8);
	}
	
	public static Reader createReader(File file)
		throws IOException
	{
		return new InputStreamReader(new FileInputStream(file), Charsets.UTF8);
	}
	
	public static Reader createReader(URL url)
		throws IOException
	{
		return new InputStreamReader(url.openStream(), Charsets.UTF8);
	}
	
	public static Writer createWriter(OutputStream out)
	{
		return new OutputStreamWriter(out, Charsets.UTF8);
	}
	
	public static Writer createWriter(File file)
		throws IOException
	{
		return new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF8);
	}
	
	public static Writer createWriter(URL url)
		throws IOException
	{
		return new OutputStreamWriter(url.openConnection().getOutputStream(), Charsets.UTF8);
	}
	
	public static File getCanonicalFile(File file)
	{
		try
		{
			return file.getCanonicalFile();
		}
		catch (IOException exc)
		{
			System.err.println("file.getCanonicalFile() failed, returning file.getAbsolutePath.");
			exc.printStackTrace();
			
			return file.getAbsoluteFile();
		}
	}
	
	public static String getCanonicalOrAbsolutePath(File file)
	{
		try
		{
			return file.getCanonicalPath();
		}
		catch (IOException exc)
		{
			System.err.println("file.getCanonicalPath() failed, returning file.getAbsolutePath.");
			exc.printStackTrace();
			
			return file.getAbsolutePath();
		}
	}
	
	/**
	 * @param closeable
	 */
	public static void close(Closeable closeable, Consumer<IOException> exceptionHandler)
	{
		try
		{
			closeable.close();
		}
		catch (IOException exc)
		{
			if (exceptionHandler != null)
			{
				exceptionHandler.accept(exc);
			}
		}
	}
	
	public interface IoSupplier<T>
	{
		T get()
			throws IOException;
	}
	
	public static <T> T call(IoSupplier<T> fun)
	{
		try
		{
			return fun.get();
		}
		catch (IOException exc)
		{
			throw new RuntimeException(exc);
		}
	}
	
	public interface IoRunnable
	{
		void run() throws IOException;
	}
	
	public static void exec(IoRunnable function)
	{
		try
		{
			function.run();
		}
		catch (IOException exc)
		{
			throw new RuntimeException(exc);
		}
	}
	
	private static PrintWriter stdout = new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF8), true);
	private static PrintWriter stderr = new PrintWriter(new OutputStreamWriter(System.err, Charsets.UTF8), true);
	
	public static PrintWriter stdout()
	{
		return stdout;
	}
	
	public static PrintWriter stderr()
	{
		return stderr;
	}
}
