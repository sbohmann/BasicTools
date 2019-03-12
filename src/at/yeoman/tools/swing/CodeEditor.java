
package at.yeoman.tools.swing;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JComponent;

public class CodeEditor extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	public CodeEditor()
	{
		font = new Font(Font.MONOSPACED, 0, 14);
		
		for (Map.Entry<TextAttribute, ?> entry : font.getAttributes().entrySet())
		{
			System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue());
		}
	}
	
	public static void main(String[] args)
	{
		new CodeEditor();
	}
	
	private Font font;
}
