
package at.yeoman.mutabor.collections;

public class Pair<F,S>
{
	private final F first;
	private final S second;
	
	public Pair(F first, S second)
	{
		if (first == null || second == null)
		{
			throw new NullPointerException("First or second == null - first: [" + first + "], second: [" + second + "]");
		}
		
		this.first = first;
		this.second = second;
	}

	public F getFirst()
	{
		return first;
	}

	public S getSecond()
	{
		return second;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pair<?,?> other = (Pair<?,?>) obj;
		if (first == null)
		{
			if (other.first != null) return false;
		}
		else if (!first.equals(other.first)) return false;
		if (second == null)
		{
			if (other.second != null) return false;
		}
		else if (!second.equals(other.second)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Pair(" + first + ", " + second + ")";
	}
}
