
package at.yeoman.tools.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 * 
 * A buffer for collecting byte data.
 * 
 * This class is not thread-safe. It follows the speed-oriented example of Java's own StringBuilder.
 * 
 * @author seb
 *
 */
public final class ByteBuffer
{
	private byte[] data;
	private int size;
	
	public ByteBuffer()
	{
		this(32);
	}
	
	public ByteBuffer(int initialCapacity)
	{
		if (initialCapacity < 32)
		{
			initialCapacity = 32;
		}
		data = new byte[initialCapacity];
	}
	
	public void append(byte b)
	{
		int newSize = size + 1;
		ensureCapacity(newSize);
		data[size] = b;
		size = newSize;
	}
	
	public void append(byte[] b)
	{
		appendImpl(b,0,b.length);
	}

	public void append(byte[] b,int offset,int length)
	{
		if (offset < 0 || length < 0)
		{
			throw new IllegalArgumentException(String.format("Negative offset [%d] or length [%d]",offset,length));
		}
		
		// allow offset == b.length because it makes the code easier to use
		// from some widely used logical approaches
		if (offset > b.length)
		{
			throw new IllegalArgumentException(String.format("offset [%d] > b.length [%d]",offset,b.length));
		}
		
		if (offset + length > b.length)
		{
			throw new IllegalArgumentException(String.format("offset [%d] + length [%d] > b.length [%d]",offset,length,b.length));
		}
		
		appendImpl(b, offset, length);
	}
	
	// TODO make faster and more robust -> CharsetEncoder
	public void appendAsciiString(String str)
	{
		append(str.getBytes(Charsets.ASCII));
	}
	
	// TODO make faster and more robust -> CharsetEncoder
	// This follows the strange logic that for strings it's start,end, while for arrays it's offset,length.
	public void appendAsciiString(String str,int start,int end)
	{
		append(str.substring(start,end).getBytes(Charsets.ASCII));
	}
	
	private void appendImpl(byte[] b,int offset,int length)
	{
		int newSize = size + length;
		ensureCapacity(newSize);
		System.arraycopy(b,offset,data,size,length);
		size = newSize;
	}
	
	public int size()
	{
		return size;
	}
	
	public byte[] toByteArray()
	{
		byte[] result = new byte[size];
		System.arraycopy(data,0,result,0,size);
		return result;
	}
	
	/**
	 * Copies the data to a byte array. Trailing data is not changed if that byte array's length is > this ByteBuffer's size.
	 * @param b The byte array the data is copied into. Its size must be at least this ByteBuffer's size.
	 */
	public void writeToByteArray(byte[] b)
	{
		if (b.length < size)
		{
			throw new IllegalArgumentException(String.format("b.length [%d] < size [%d]",b.length,size));
		}
		System.arraycopy(data, 0, b, 0, size);
	}
	
	public String createString(Charset charset)
	{
		return new String(data,0,size,charset);
	}
	
	public void appendAsString(StringBuilder sb, Charset charset)
	{
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(data,0,size);
			InputStreamReader reader = new InputStreamReader(bais,charset);
			char[] buffer = new char[1024];
			while (true)
			{
				int n = reader.read(buffer);
				if (n < 0)
				{
					break;
				}
				sb.append(buffer,0,n);
			}
		}
		catch(IOException exc)
		{
			throw new RuntimeException("Unexpected internal IOException",exc);
		}
	}
	
	private void ensureCapacity(int capacity)
	{
		int newCapacity = Math.max(data.length * 2, capacity);
		
		if (data.length < capacity)
		{
			byte[] newData = new byte[newCapacity];
			System.arraycopy(data,0,newData,0,size);
			data = newData;
		}
	}
}
