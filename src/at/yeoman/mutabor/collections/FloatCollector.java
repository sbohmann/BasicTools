
package at.yeoman.mutabor.collections;

public final class FloatCollector
{
	private final static int INITIAL_CAPACITY = 32;
	
	private float[] data = new float[INITIAL_CAPACITY];
	private int size = 0;
	
	public void add(float value)
	{
		if (size == Integer.MAX_VALUE)
		{
			throw new RuntimeException("Size limit of " + Integer.MAX_VALUE + " reached.");
		}
		ensureCapacity(size + 1);
		data[size] = value;
		++size;
	}
	
	public float get(int idx)
	{
		if (idx < 0 || idx > size)
		{
			throw new IllegalArgumentException("Index out of bound: " + idx + ", size: " + size);
		}
		
		return data[idx];
	}
	
	public int size()
	{
		return size;
	}
	
	public void toArray(float[] target)
	{
		if (target.length < size)
		{
			throw new IllegalArgumentException("Target length [" + target.length + "] < size [" + size + "]");
		}
		System.arraycopy(data,0,target,0,size);
	}
	
	public float[] toArray()
	{
		float[] result = new float[size];
		System.arraycopy(data,0,result,0,size);
		return result;
	}
	
	public float[] releaseAndReset()
	{
		float[] result = data;
		data = new float[INITIAL_CAPACITY];
		size = 0;
		return result;
	}
	
	private void ensureCapacity(int cap)
	{
		if (data.length < cap)
		{
			int newCap = Math.max(nextCap(), cap);
			float[] newData = new float[newCap];
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}
	
	private int nextCap()
	{
		final int cap = data.length;
		if (cap < (1 << 10))
		{
			return cap * 2;
		}
		else if (cap < (1 << 20))
		{
			return cap + cap / 2;
		}
		else if (cap < (1 << 25))
		{
			return cap + cap / 4;
		}
		else if (cap < 1 << 30)
		{
			return cap + cap / 8;
		}
		else
		{
			int result = cap + cap / 16;
			if (result < 0)
			{
				result = Integer.MAX_VALUE;
			}
			return result;
		}
	}
}
