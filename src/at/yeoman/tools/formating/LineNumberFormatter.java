
package at.yeoman.tools.formating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LineNumberFormatter
{
	public static LineNumberFormattedText format(String text)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new StringReader(text));
			List<String> lines = new ArrayList<>();
			while (true)
			{
				String line = reader.readLine();
				if(line == null)
				{
					break;
				}
				lines.add(line);
			}
			int numChars = Integer.toString(lines.size()).length();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			int lineNumber = 1;
			for (String line : lines)
			{
				pw.printf("[%" + numChars + "d] %s%n",lineNumber++,line);
			}
			return new LineNumberFormattedText(sw.toString(),lines);
		}
		catch(IOException exc)
		{
			throw new RuntimeException(exc);
		}
	}

	/**
	 * @author seb
	 *
	 * Contains the line number formatted text (with 1-based line numbers), as well as the lines (naturally 0-based)
	 *
	 */
	public static class LineNumberFormattedText
	{
		private final String lineNumberFormattedText;
		private final List<String> lines;

		public LineNumberFormattedText(String lineNumberFormattedText, List<String> lines)
		{
			this.lineNumberFormattedText = lineNumberFormattedText;
			this.lines = lines;
		}

		public String getLineNumberFormattedText()
		{
			return lineNumberFormattedText;
		}

		public List<String> getLines()
		{
			return lines;
		}
	}
}
