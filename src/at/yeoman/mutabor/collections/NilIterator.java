
package at.yeoman.mutabor.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class NilIterator<E> implements Iterator<E>
{
	@Override
	public boolean hasNext()
	{
		return false;
	}
	
	@Override
	public E next()
	{
		throw new NoSuchElementException();
	}
	
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
