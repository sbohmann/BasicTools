
package at.yeoman.tools.graphics;

public enum OutputFormat
{
	PNG("PNG", "png", "png"),
	JPEG("JPEG", "jpeg", "jpg");
	
	private OutputFormat(String imageIoName, String httpHeaderName, String fileExtension)
	{
		this.imageIoName = imageIoName;
		this.httpHeaderName = httpHeaderName;
		this.fileExtension = fileExtension;
	}
	
	public final String imageIoName;
	public final String httpHeaderName;
	public final String fileExtension;
}
