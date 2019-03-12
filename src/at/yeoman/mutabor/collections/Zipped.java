
package at.yeoman.mutabor.collections;

public class Zipped<E>
{
	public final E value;
	public final int index;
	public final boolean first;
	public final boolean last;
	
	public Zipped(E value, int index, boolean first, boolean last) {
		this.value = value;
		this.index = index;
		this.first = first;
		this.last = last;
	}
}