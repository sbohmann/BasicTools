
package at.yeoman.tools.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GraphicsTools
{
	public static final Map<RenderingHints.Key, Object> GoodRenderingHints = createGoodRenderingHints();
	
	private static Map<RenderingHints.Key, Object> createGoodRenderingHints()
	{
		Map<RenderingHints.Key, Object> result = new HashMap<>();
		result.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		result.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		result.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		result.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		result.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		result.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		result.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return Collections.unmodifiableMap(result);
	}
	
	/**
	 * orientation: EXIF orientation value (1...8)
	 */
	public static BufferedImage normalizeOrientation(BufferedImage src, int orientation)
	{
		System.out.println("NormalizeOrientation called with orientation " + orientation + ", width " + src.getWidth() + ", height: " + src.getHeight());
		
		int xFromX = 0;
		int xFromY = 0;
		int yFromX = 0;
		int yFromY = 0;
		boolean rotatedResultSize = false;
		
		switch(orientation)
		{
		case 1:
			return src;
		case 2:
			xFromX = -1;
			yFromY = 1;
			break;
		case 3:
			xFromX = -1;
			yFromY = -1;
			break;
		case 4:
			xFromX = 1;
			yFromY = -1;
			break;
		case 5:
			xFromY = 1;
			yFromX = 1;
			rotatedResultSize = true;
			break;
		case 6:
			xFromY = -1;
			yFromX = 1;
			rotatedResultSize = true;
			break;
		case 7:
			xFromY = -1;
			yFromX = -1;
			rotatedResultSize = true;
			break;
		case 8:
			xFromY = 1;
			yFromX = -1;
			rotatedResultSize = true;
			break;
		default:
			throw new IllegalArgumentException("orientation: " + orientation);
		}
		
		BufferedImage result;
		
		if (rotatedResultSize)
		{
			result = new BufferedImage(src.getHeight(), src.getWidth(), BufferedImage.TYPE_INT_RGB);
		}
		else
		{
			result = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
		}
		
		int xShift = 0;
		int yShift = 0;
		
		if (xFromX + xFromY == -1)
		{
			xShift = result.getWidth();
		}
		
		if (yFromX + yFromY == -1)
		{
			yShift = result.getHeight();
		}
		
		System.out.println(xFromX + ", " + yFromX + ", " + xFromY + ", " + yFromY + ", " + xShift + ", " + yShift);
		
		AffineTransform transform = new AffineTransform(xFromX, yFromX, xFromY, yFromY, xShift, yShift);
		
		result.createGraphics().drawImage(src, transform, null);
		
		return result;
	}
	
	public static void drawStringCentered(Graphics2D g, String str, double centerX, double y)
	{
		Rectangle2D rect = g.getFont().getStringBounds(str, g.getFontRenderContext());
		
		g.drawString(str, (float)(centerX - rect.getWidth() / 2.0), (float)y);
	}
}
