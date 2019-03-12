
package bootstrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.yeoman.tools.io.Charsets;
import at.yeoman.tools.io.IoTools;
import at.yeoman.tools.templates.TemplateProcessor;

public class CollectionGenerator
{
	private static List<Map<String,String>> replacementsList = new ArrayList<>();
	
	static
	{
		Map<String,String> map;
		
		map = new HashMap<>();
		map.put("long_uc", "Byte");
		map.put("long_lc", "byte");
		map.put("short_uc", "Byte");
		map.put("short_lc", "byte");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Short");
		map.put("long_lc", "short");
		map.put("short_uc", "Short");
		map.put("short_lc", "short");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Integer");
		map.put("long_lc", "integer");
		map.put("short_uc", "Int");
		map.put("short_lc", "int");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Long");
		map.put("long_lc", "long");
		map.put("short_uc", "Long");
		map.put("short_lc", "long");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Float");
		map.put("long_lc", "float");
		map.put("short_uc", "Float");
		map.put("short_lc", "float");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Double");
		map.put("long_lc", "double");
		map.put("short_uc", "Double");
		map.put("short_lc", "double");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Character");
		map.put("long_lc", "character");
		map.put("short_uc", "Char");
		map.put("short_lc", "char");
		replacementsList.add(map);
		
		map = new HashMap<>();
		map.put("long_uc", "Boolean");
		map.put("long_lc", "boolean");
		map.put("short_uc", "Boolean");
		map.put("short_lc", "boolean");
		replacementsList.add(map);
	}
	
	public static void main(String[] args)
	{
		try
		{
			File outputDir = new File("src/at/yeoman/tools/collections");
			
			if (outputDir.isDirectory() == false)
			{
				throw new IOException("Not a directory: [" + outputDir + "]");
			}
			
			for (Map<String,String> replacements : replacementsList)
			{
				Writer writer = new OutputStreamWriter(new FileOutputStream(new File(outputDir,replacements.get("short_uc") + "Collector.java")),Charsets.UTF8);
				writer.write(TemplateProcessor.processJava(IoTools.readTextFile(new File("templates/collector.txt"),Charsets.UTF8), replacements));
				writer.close();
			}
		}
		catch(IOException exc)
		{
			exc.printStackTrace();
		}
	}
}
