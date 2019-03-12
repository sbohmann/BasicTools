
package at.yeoman.tools.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public final class Range<T extends Number> implements Iterable<T>
{
	private final Class<? extends Number> c;
	private final T begin;
	private final T end;
	private final T step;
	private final boolean inclusive;
	
	public static <T extends Number & Comparable<T>> Range<T> inclusive(T begin, T end)
	{
		return new Range<>(begin, end, true);
	}
	
	public static <T extends Number & Comparable<T>> Range<T> exclusive(T begin, T end)
	{
		return new Range<>(begin, end, false);
	}
	
	public Range(T begin, T end)
	{
		this.c = check(begin,end);
		this.begin = begin;
		this.end = end;
		this.step = defaultStep();
		this.inclusive = false;
	}
	
	public Range(T begin, T end, boolean inclusive)
	{
		this.c = check(begin,end);
		this.begin = begin;
		this.end = end;
		this.step = defaultStep();
		this.inclusive = inclusive;
	}
	
	public Range(T begin, T end, T step)
	{
		this.c = check(begin,end,step);
		this.begin = begin;
		this.end = end;
		this.step = step;
		this.inclusive = false;
	}
	
	public Range(T begin, T end, T step, boolean inclusive)
	{
		this.c = check(begin,end,step);
		this.begin = begin;
		this.end = end;
		this.step = step;
		this.inclusive = inclusive;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begin == null) ? 0 : begin.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Range<T> other = (Range<T>) obj;
		if (begin == null)
		{
			if (other.begin != null)
				return false;
		} else if (!begin.equals(other.begin))
			return false;
		if (c == null)
		{
			if (other.c != null)
				return false;
		} else if (!c.equals(other.c))
			return false;
		if (end == null)
		{
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (step == null)
		{
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		return true;
	}

	public T getBegin()
	{
		return begin;
	}

	public T getEnd()
	{
		return end;
	}

	public T getStep()
	{
		return step;
	}
	
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			T position = begin;
			boolean reachedEnd = false;
			
			@Override
			public boolean hasNext()
			{
				boolean positionEqualsEnd = position.equals(end);
				
				boolean result;
				
				if (inclusive)
				{
					result = (reachedEnd == false || positionEqualsEnd);
				}
				else
				{
					result = (positionEqualsEnd == false);
				}
				
				if (positionEqualsEnd)
				{
					reachedEnd = true;
				}
				
				return result;
			}
			
			@Override
			public T next()
			{
				T result = position;
				position = increment(position);
				return result;
			}

			@Override
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	private Class<? extends Number> check(Number ... values)
	{
		Class<? extends Number> c = null;
		for (Number elem : values)
		{
			if (elem == null)
			{
				throw new NullPointerException();
			}
			
			if (c == null)
			{
				c = elem.getClass();
			}
			else
			{
				if (elem.getClass() != c)
				{
					throw new IllegalArgumentException(String.format("Mixing numeric types: %s and %s",c,elem.getClass()));
				}
			}
		}
		
		if (c != Byte.class && c != Short.class && c != Integer.class && c != Long.class &&
			c != Float.class && c != Double.class && c != BigInteger.class && c != BigDecimal.class)
		{
			throw new RuntimeException("Unsupported numeric class: " + c);
		}
		
		return c;
	}
	
	@SuppressWarnings("unchecked")
	private T increment(T value)
	{
		if (c == Byte.class)
		{
			return (T)(Byte)(byte)((Byte)value + (Byte)step);
		}
		else if (c == Short.class)
		{
			return (T)(Short)(short)((Short)value + (Short)step);
		}
		else if (c == Integer.class)
		{
			return (T)(Integer)((Integer)value + (Integer)step);
		}
		else if (c == Long.class)
		{
			return (T)(Long)((Long)value + (Long)step);
		}
		else if (c == Float.class)
		{
			return (T)(Float)((Float)value + (Float)step);
		}
		else if (c == Double.class)
		{
			return (T)(Double)((Double)value + (Double)step);
		}
		else if (c == BigInteger.class)
		{
			return (T)((BigInteger)value).add((BigInteger)step);
		}
		else if (c == BigDecimal.class)
		{
			return (T)((BigDecimal)value).add((BigDecimal)step);
		}
		else
		{
			throw new RuntimeException("Unsupported numeric class: " + c);
		}
	}
	
	@SuppressWarnings("unchecked")
	private T defaultStep()
	{
		if (c == Byte.class)
		{
			return (T)(Byte)(byte)1;
		}
		else if (c == Short.class)
		{
			return (T)(Short)(short)1;
		}
		else if (c == Integer.class)
		{
			return (T)(Integer)1;
		}
		else if (c == Long.class)
		{
			return (T)(Long)1L;
		}
		else if (c == Float.class)
		{
			return (T)(Float)1.0f;
		}
		else if (c == Double.class)
		{
			return (T)(Double)1.0;
		}
		else if (c == BigInteger.class)
		{
			return (T)BigInteger.ONE;
		}
		else if (c == BigDecimal.class)
		{
			return (T)BigDecimal.ONE;
		}
		else
		{
			throw new RuntimeException("Unsupported numeric class: " + c);
		}
	}
}
