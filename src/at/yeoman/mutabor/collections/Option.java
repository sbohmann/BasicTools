
package at.yeoman.mutabor.collections;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Option<E> extends AbstractList<E>
{
	private static final Option<?> NONE = new Option<>(null);
	
	private final E m_value;
	
	private Option(E value)
	{
		m_value = value;
	}
	
	public static <E> Option<E> some(E value)
	{
		if (value == null)
		{
			throw new NullPointerException("Argument value is null");
		}
		
		return new Option<>(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> Option<E> none()
	{
		return (Option<E>) NONE;
	}
	
	public boolean hasValue()
	{
		return m_value != null;
	}
	
	public E get()
	{
		if (m_value == null)
		{
			throw new IndexOutOfBoundsException("get() was called on nil option");
		}
		
		return m_value;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_value == null) ? 0 : m_value.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Option<?> other = (Option<?>) obj;
		if (m_value == null)
		{
			if (other.m_value != null) return false;
		}
		else if (!m_value.equals(other.m_value)) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		if (m_value != null)
		{
			return "Some(" + m_value + ")";
		}
		else
		{
			return "None";
		}
	}
	
	@Override
	public Iterator<E> iterator()
	{
		return new Iterator<E>()
		{
			boolean finished = (hasValue() == false);

			@Override
			public boolean hasNext()
			{
				return finished == false;
			}

			@Override
			public E next()
			{
				if (finished)
				{
					throw new NoSuchElementException();
				}
				
				finished = true;
				
				return m_value;
			}
		};
	}
	
	@Override
	public int size()
	{
		return hasValue() ? 1 : 0;
	}

	@Override
	public E get(int index)
	{
		if (m_value != null && index == 0)
		{
			return m_value;
		}
		else
		{
			throw new IndexOutOfBoundsException("hasValue: " + hasValue() + ", index: " + index);
		}
	}
}
