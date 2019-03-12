
package at.yeoman.tools.parsing;

import at.yeoman.mutabor.collections.IntCollector;
import at.yeoman.tools.io.IoTools;

public final class ParserSource
{
	private final int[] codePoints;
	private final int[] lineNumbers;
	private final int[] columns;
	private final int size;
	
	private final int tabColumns;
	
	private final String path; // may be null
	
	public ParserSource(String str)
	{
		this(str,4,null);
	}
	
	public ParserSource(String str, int tabColumns)
	{
		this(str,tabColumns,null);
	}
	
	public ParserSource(String str, String path)
	{
		this(str,4,path);
	}
	
	public ParserSource(String str, int tabColumns, String path)
	{
		if (tabColumns < 0)
		{
			throw new IllegalArgumentException("Illegal number of tab columns: " + tabColumns);
		}
		
		this.tabColumns = tabColumns;
		
		this.path = path;
		
		IntCollector codePointCollector = new IntCollector();
		IntCollector lineNumberCollector = new IntCollector();
		IntCollector columnCollector = new IntCollector();
		
		final int size = str.length();
		int line = 1;
		int column = 1;
		boolean was_cr = false;
		boolean ignore = false;
		for (int idx = 0; idx < size; idx = str.offsetByCodePoints(idx, 1))
		{
			int cp = str.codePointAt(idx);
			int cpLine = line;
			int cpColumn = column;
			
			switch(cp)
			{
			case '\t': column = tab(column); break;
			case '\r': ++line; column = 1; break;
			case '\n': if (was_cr == false) { ++line; column = 1; } break;
			default:
				if (Character.isISOControl(cp))
				{
					throw new IllegalArgumentException("Illegal control character character at line " + line + ", column " + column + ": code point " + cp);
				}
				if (Character.isWhitespace(cp) && cp != ' ')
				{
					throw new IllegalArgumentException("Illegal white space character at line " + line + ", column " + column + ": code point " + cp);
				}
				else if (IoTools.isPrintable(cp))
				{
					++column;
				}
				else
				{
					ignore = true;
				}
			}
			
			was_cr = (cp == '\r');
			
			if (ignore == false)
			{
				codePointCollector.add(cp);
				lineNumberCollector.add(cpLine);
				columnCollector.add(cpColumn);
			}
		}
		
		this.codePoints = codePointCollector.toArray();
		this.lineNumbers = lineNumberCollector.toArray();
		this.columns = columnCollector.toArray();
		this.size = codePoints.length;
	}
	
	public int size()
	{
		return size;
	}
	
	public int get(int idx)
	{
		return codePoints[idx];
	}
	
	public int getLineNumber(int idx)
	{
		return lineNumbers[idx];
	}
	
	public int getColumn(int idx)
	{
		return columns[idx];
	}
	
	public int getTabColumns()
	{
		return tabColumns;
	}
	
	public String getPath()
	{
		return path;
	}
	
	private int tab(int column)
	{
		return (column + tabColumns) / tabColumns * tabColumns;
	}
}
