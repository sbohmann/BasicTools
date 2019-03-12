
package at.yeoman.tools.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;


public class Boxed
{
	public static Byte nullAsZero(Byte value)
	{
		if (value == null)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public static Short nullAsZero(Short value)
	{
		if (value == null)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public static Character nullAsZero(Character value)
	{
		if (value == null)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public static Integer nullAsZero(Integer value)
	{
		if (value == null)
		{
			return 0;
		}
		else
		{
			return value;
		}
	}
	
	public static Long nullAsZero(Long value)
	{
		if (value == null)
		{
			return 0L;
		}
		else
		{
			return value;
		}
	}
	
	public static BigInteger nullAsZero(BigInteger value)
	{
		if (value == null)
		{
			return BigInteger.valueOf(0);
		}
		else
		{
			return value;
		}
	}
	
	public static BigDecimal nullAsZero(BigDecimal value)
	{
		if (value == null)
		{
			return BigDecimal.valueOf(0);
		}
		else
		{
			return value;
		}
	}
}
