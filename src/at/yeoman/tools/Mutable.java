
package at.yeoman.tools;

public final class Mutable<T>
{
	private T value;
	
	public Mutable(T value)
	{
		this.value = value;
	}
	
	public T get()
	{
		return value;
	}
	
	public void set(T newValue)
	{
		value = newValue;
	}
}
