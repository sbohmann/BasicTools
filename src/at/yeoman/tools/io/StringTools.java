
package at.yeoman.tools.io;

import java.io.PrintWriter;
import java.util.Map;

public class StringTools
{
	public static String escape(String s)
	{
		StringBuilder sb = new StringBuilder();
		final int size = s.length();
		for (int i = 0; i < size; ++i)
		{
			char c = s.charAt(i);
			
			sb.append(escape(c));
		}
		return sb.toString();
	}
	
	public static String escape(char c)
	{
		switch (c)
		{
		case '\r':
			return "\\r";
		case '\n':
			return "\\n";
		case '\t':
			return "\\t";
		case '\b':
			return "\\b";
		case '\\':
			return "\\\\";
		case '"':
			return "\\\"";
		default:
			return String.valueOf(c);
		}
	}
	
	public static String quote(String s)
	{
		return '"' + escape(s) + '"';
	}
	
	public static String unescape(String s)
	{
		StringBuffer sb = new StringBuffer();
		final int size = s.length();
		
		boolean escaped = false;
		for (int i = 0; i < size; ++i)
		{
			char c = s.charAt(i);
			switch (c)
			{
			case '"': if (escaped == false) throw new IllegalArgumentException("Illegal unescaped '\"' at position " + i + " in quoted string [" + s + "]"); break;
			case '\\': if (escaped) sb.append('\''); else escaped = true; break;
			default:
				if (escaped)
				{
					switch(c)
					{
					case 'r': sb.append('\r'); break;
					case 'n': sb.append('\n'); break;
					case 't': sb.append('\t'); break;
					case 'b': sb.append('\b'); break;
					case '"':
					case '\\': sb.append(c); break;
					default:
						throw new IllegalArgumentException("Illegal escape sequence [\\" + c + "] at position " + i + " of quoted string [" + s + "]");
					}
					escaped = false;
				}
				else
				{
					sb.append(c);
				}
			}
		}
		
		if (escaped)
		{
			throw new IllegalArgumentException("Dangling escape at end of quoted string: [" + s + "]");
		}
		
		return sb.toString();
	}
	
	public static String unquote(String s)
	{
		if (s.length() < 2 || s.charAt(0) != '"' || s.charAt(s.length() - 1) != '"')
		{
			throw new IllegalArgumentException("Illegal quoted string: [" + s + "]");
		}
		
		return unescape(s.substring(1, s.length() - 1));
	}
	
	public static String capitalize(String name)
	{
		return firstCharToUpperCase(name);
	}
	
	public static String firstCharToUpperCase(String name)
	{
		if (name.length() == 0)
		{
			return name;
		}
		
		char c = name.charAt(0);
		
		if (Character.isLowerCase(c))
		{
			return "" + Character.toUpperCase(c) + name.substring(1);
		}
		
		return name;
	}
	
	public static String firstCharToLowerCase(String name)
	{
		if (name.length() == 0)
		{
			return name;
		}
		
		char c = name.charAt(0);
		
		if (Character.isUpperCase(c))
		{
			return "" + Character.toLowerCase(c) + name.substring(1);
		}
		
		return name;
	}
	
	public static String pformat(Object obj)
	{
		if (obj instanceof Object[])
		{
			StringBuilder sb = new StringBuilder();
			sb.append(obj.getClass().getComponentType().getSimpleName());
			sb.append('[');
			boolean first = true;
			for (String s : (String[])obj)
			{
				if (first == false)
				{
					sb.append(", ");
					sb.append(pformat(s));
				}
				first = false;
			}
			sb.append(']');
			return sb.toString();
		}
		else if (obj instanceof Map<?,?>)
		{
			return pformat((Map<?,?>)obj);
		}
		else if (obj instanceof String)
		{
			return quote((String)obj);
		}
		else
		{
			return obj.toString();
		}
	}
	
	public static String pformat(Map<?,?> map)
	{
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		boolean first = true;
		for (Map.Entry<?,?> entry : map.entrySet())
		{
			if (first == false)
			{
				sb.append(",\n ");
			}
			sb.append(pformat(entry.getKey()));
			sb.append(' ');
			sb.append(pformat(entry.getValue()));
			first = false;
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static String codePointToString(int codePoint)
	{
		int[] a = new int[1];
		a[0] = codePoint;
		return new String(a,0,1);
	}
	
	public static String escapeForHtml(String str)
	{
		StringBuilder sb = new StringBuilder();
		
		int size = str.length();
		
		for (int idx = 0; idx < size; ++idx)
		{
			char c = str.charAt(idx);
			
			switch(c)
			{
			case '"':
				sb.append("&quot;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			default:
				if (c < 0x20 || c == 128)
				{
					sb.append("&#");
					sb.append((int)c);
					sb.append(';');
				}
				else
				{
					sb.append(c);
				}
			}
		}
		
		return sb.toString();
	}
	
	public byte parseDigit(char c)
	{
		if (c >= '0' && c <= '9')
		{
			return (byte)(c - '0');
		}
		else if (c >= 'A' && c <= 'Z')
		{
			return (byte)(c - 'A' + 10);
		}
		else if (c >= 'a' && c <= 'z')
		{
			return (byte)(c - 'a' + 10);
		}
		else
		{
			return -1;
		}
	}
	
	public static String hexByte(byte value)
	{
		return "" + hexChar((value >> 4) & 0x0F) + hexChar(value & 0x0F);
	}
	
	public static void hexPrint(byte[] data)
	{
		hexPrint(data, IoTools.stdout());
	}
	
	public static void hexPrint(byte[] data, PrintWriter out)
	{
		boolean first = true;
		for (byte b : data)
		{
			if (first == false)
			{
				out.print(' ');
			}
			
			out.print(hexChar((b >> 4) & 0x0F));
			out.print(hexChar(b & 0x0F));
			
			first = false;
		}
		out.println();
	}
	
	public static char hexChar(int value)
	{
		if (value < 0 || value > 15)
		{
			throw new IllegalArgumentException("Out of range [0,15]: " + value);
		}
		
		if (value < 10)
		{
			return (char)('0' + value);
		}
		else
		{
			return (char)('a' + value - 10);
		}
	}
	
	/*private <T> T parseInteger(String str, byte radix, Class<T> returnType, boolean optional, boolean isSigned, byte numBytes)
	{
		T result = T(0);
		T limit = 0;
		T limitBeforeShift = 0;Integer.parseInt(s)
		
		bool positive = true;
		
		bool firstDigit = true;
		
		size_t size = str.size();
		for (size_t idx = 0; idx < size; ++idx)
		{
			char c = str.at(idx);
			
			bool skip = false;
			
			if (idx == 0)
			{
				if (isSigned && c == '-')
				{
					positive = false;
					skip = true;
				}
				
				if (positive)
				{
					limit = maxValue<T>();
					limitBeforeShift = T(limit / radix);
				}
				else
				{
					limit = minValue<T>();
					limitBeforeShift = T(limit / radix);
				}
			}
			
			if (skip == false)
			{
				if ((positive && result > limitBeforeShift) ||
					(positive == false && result < limitBeforeShift))
				{
					if (DDEBUG)
					{
						PrintStream(std::cerr) << "out of range - type: " << typeid(T).name() <<
							", signed: " << isSigned << ", numBytes: " << numBytes <<
							", radix: " << radix << ", limit: " << limit <<
							", limitBeforeShift: " << limitBeforeShift <<
							", result: " << result << ", idx: " << idx <<
							", c: " << c;
						std::cerr << std::endl;
					}
					
					if (optional)
					{
						return R();
					}
					else
					{
						THROW("Literal out of range: " << StringTools::quote(str) <<
							  " while parsing " << (isSigned?"a signed ":"an unsigned ") <<
							  (numBytes * 8) << " bit integer with radix " << int16(radix));
					}
				}
				
				result = T(result * radix);
				
				int8 digit = parseDigit(c);
				
				if (digit < 0 || digit >= radix)
				{
					if (optional)
					{
						return R();
					}
					else
					{
						THROW("Illegal character [" << StringTools::escape(c) <<
							  "] at position " << (idx + 1) <<
							  " in string " << StringTools::quote(str) <<
							  " while parsing " << (isSigned?"a signed ":"an unsigned ") <<
							  (numBytes * 8) << " bit integer with radix " << int16(radix));
					}
				}
				
				if ((positive && T(digit) > (limit - result)) ||
					(positive == false && T(-digit) < (limit - result)))
				{
					if (optional)
					{
						return R();
					}
					else
					{
						THROW("Literal out of range: " << StringTools::quote(str) <<
							  " while parsing " << (isSigned?"a signed ":"an unsigned ") <<
							  (numBytes * 8) << " bit integer with radix " << int16(radix));
					}
				}
				
				if (positive)
				{
					result = T(result + digit);
				}
				else
				{
					result = T(result - digit);
				}
				
				firstDigit = false;
			}
		}
		
		if (firstDigit)
		{
			if (optional)
			{
				return R();
			}
			else
			{
				THROW("No digits found in string " << StringTools::quote(str) <<
					  " while parsing " << (isSigned?"a signed ":"an unsigned ") <<
					  (numBytes * 8) << " bit integer with radix " << int16(radix));
			}
		}
		
		return result;
	}
	
	int8 StringTools::parseInt8(const std::string &str, uint8 radix)
	{
		return parseInteger<int8,false,int8,true,1>(str, radix);
	}
	
	uint8 StringTools::parseUint8(const std::string &str, uint8 radix)
	{
		return parseInteger<uint8,false,uint8,false,1>(str, radix);
	}
	
	int16 StringTools::parseInt16(const std::string &str, uint8 radix)
	{
		return parseInteger<int16,false,int16,true,2>(str, radix);
	}
	
	uint16 StringTools::parseUint16(const std::string &str, uint8 radix)
	{
		return parseInteger<uint16,false,uint16,false,2>(str, radix);
	}
	
	int32 StringTools::parseInt32(const std::string &str, uint8 radix)
	{
		return parseInteger<int32,false,int32,true,4>(str, radix);
	}
	
	uint32 StringTools::parseUint32(const std::string &str, uint8 radix)
	{
		return parseInteger<uint32,false,uint32,false,4>(str, radix);
	}
	
	int64 StringTools::parseInt64(const std::string &str, uint8 radix)
	{
		return parseInteger<int64,false,int64,true,8>(str, radix);
	}
	
	uint64 StringTools::parseUint64(const std::string &str, uint8 radix)
	{
		return parseInteger<uint64,false,uint64,false,8>(str, radix);
	}*/
}
