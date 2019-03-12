
package at.yeoman.tools.io;

public class SafeCasts
{
	public static byte toInt8(long value)
	{
		if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return (byte)value;
	}
	
	public static short toUint8(long value)
	{
		if (value < 0 || value > 0xFF)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return (short)value;
	}
	
	public static short toInt16(long value)
	{
		if (value < Short.MIN_VALUE || value > Short.MAX_VALUE)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return (short)value;
	}
	
	public static int toUint16(long value)
	{
		if (value < 0 || value > 0xFFFF)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return (int)value;
	}
	
	public static int toInt32(long value)
	{
		if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return (int)value;
	}
	
	public static long toUint32(long value)
	{
		if (value < 0 || value > 0xFFFFFFFF)
		{
			throw new IllegalArgumentException("Value out of range: " + value);
		}
		
		return value;
	}
}
