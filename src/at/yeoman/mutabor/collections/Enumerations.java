
package at.yeoman.mutabor.collections;

import java.util.Enumeration;
import java.util.Iterator;

public class Enumerations
{
	public static <E> Iterable<E> iter(final Enumeration<E> enumeration)
	{
		return new Iterable<E>()
		{
			@Override
			public Iterator<E> iterator()
			{
				return new Iterator<E>()
				{
					@Override
					public boolean hasNext()
					{
						return enumeration.hasMoreElements();
					}
					
					@Override
					public E next()
					{
						return enumeration.nextElement();
					}
					
					@Override
					public void remove()
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	
	/**
	 * Warning: thie method breaks Java's type safety guarantees for generics!
	 */
	@SuppressWarnings("unchecked")
	public static <E> Iterable<E> unsafeIter(final Enumeration<?> enumeration)
	{
		return iter((Enumeration<E>)enumeration);
	}
}
