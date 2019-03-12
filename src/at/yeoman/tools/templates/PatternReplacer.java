
package at.yeoman.tools.templates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PatternReplacer
{
	public static String replace(Pattern pattern, String input, ReplacementFunction fun)
	{
		Matcher matcher = pattern.matcher(input);
		
		StringBuilder sb = new StringBuilder();
		int lastOffset = 0;
		
		while (matcher.find())
		{
			int start = matcher.start();
			int end = matcher.end();
			
			if (matcher.start() > lastOffset)
			{
				sb.append(input,lastOffset,start);
			}
			
			sb.append(fun.call(matcher.group(1)));
			
			lastOffset = end;
		}
		
		if (input.length() > lastOffset)
		{
			sb.append(input,lastOffset,input.length());
		}
		
		return sb.toString();
	}
	
	public static String staticReplace(Pattern pattern, String input, String replacement)
	{
		Matcher matcher = pattern.matcher(input);
		
		StringBuilder sb = new StringBuilder();
		int lastOffset = 0;
		
		while (matcher.find())
		{
			int start = matcher.start();
			int end = matcher.end();
			
			if (matcher.start() > lastOffset)
			{
				sb.append(input,lastOffset,start);
			}
			
			sb.append(replacement);
			
			lastOffset = end;
		}
		
		if (input.length() > lastOffset)
		{
			sb.append(input,lastOffset,input.length());
		}
		
		return sb.toString();
	}
	
	static final Pattern newlinePattern = Pattern.compile("\\r?\\n");
	
	/**
	 * @param indentedPattern an pattern with two groups, the first catching leading white space in a line
	 */
	public static String indentedReplace(Pattern indentedPattern, String input, ReplacementFunction fun)
	{
		Matcher matcher = indentedPattern.matcher(input);
		
		StringBuilder sb = new StringBuilder();
		int lastOffset = 0;
		
		while (matcher.find())
		{
			int start = matcher.start();
			int end = matcher.end();
			
			if (matcher.start() > lastOffset)
			{
				sb.append(input,lastOffset,start);
			}
			
			String ws = matcher.group(1);
			
			String text = fun.call(matcher.group(2));
			
			String[] split = newlinePattern.split(text);
			
			boolean first = true;
			for (String line : split)
			{
				if (first == false)
				{
					sb.append("\n");
				}
				sb.append(ws);
				sb.append(line);
				first = false;
			}
			
			lastOffset = end;
		}
		
		if (input.length() > lastOffset)
		{
			sb.append(input,lastOffset,input.length());
		}
		
		return sb.toString();
	}
	
	public interface ReplacementFunction
	{
		public String call(String key);
	}
}
