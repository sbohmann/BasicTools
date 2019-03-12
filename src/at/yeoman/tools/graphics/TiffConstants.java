
package at.yeoman.tools.graphics;

public enum TiffConstants
{
	Orientation(0x112);
	
	private TiffConstants(int value)
	{
		this.value = value;
	}

	public final int value;
}
