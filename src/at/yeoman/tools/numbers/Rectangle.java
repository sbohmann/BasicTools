
package at.yeoman.tools.numbers;

public class Rectangle<T extends Number & Comparable<T>>
{
	private final Integer x, y, width, height;

	public Rectangle(Integer x, Integer y, Integer width, Integer height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Integer getX()
	{
		return x;
	}

	public Integer getY()
	{
		return y;
	}

	public Integer getWidth()
	{
		return width;
	}

	public Integer getHeight()
	{
		return height;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((height == null) ? 0 : height.hashCode());
		result = prime * result + ((width == null) ? 0 : width.hashCode());
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Rectangle<T> other = (Rectangle<T>) obj;
		if (height == null)
		{
			if (other.height != null)
				return false;
		} else if (!height.equals(other.height))
			return false;
		if (width == null)
		{
			if (other.width != null)
				return false;
		} else if (!width.equals(other.width))
			return false;
		if (x == null)
		{
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null)
		{
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
	
}
