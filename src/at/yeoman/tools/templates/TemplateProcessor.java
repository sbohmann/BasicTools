
package at.yeoman.tools.templates;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import at.yeoman.tools.templates.PatternReplacer.ReplacementFunction;


public final class TemplateProcessor
{
	private static final Pattern indentedHtmlCommentTarget = Pattern.compile("^([ \\t]+)<!--\\s*@_(\\w+);?\\s*-->");
	
	private static final Pattern htmlCommentTarget = Pattern.compile("<!--\\s*@_(\\w+);?\\s*-->");
	
	private static final Pattern javaCommentTarget = Pattern.compile("//\\s*@_(\\w+);?\\s*$");
	
	private static final Pattern javaInlineCommentTarget = Pattern.compile("/\\*\\s*@_(\\w+);?\\s*\\*/$");
	
	private static final Pattern indentedInlineTarget = Pattern.compile("([ \\t]+)@_(\\w+);?");
	
	private static final Pattern inlineTarget = Pattern.compile("@_(\\w+);?");
	
	private static final Pattern escapeCode = Pattern.compile("@__");
		
	private static final List<Pattern> htmlTargets = Collections.unmodifiableList(Arrays.asList(new Pattern[] {
		htmlCommentTarget, inlineTarget }));
	
	private static final List<Pattern> javaTargets = Collections.unmodifiableList(Arrays.asList(new Pattern[] {
		javaCommentTarget, javaInlineCommentTarget, inlineTarget }));
	
	public static String processHtml(String template, Map<String,String> replacements)
	{
		return process(template, htmlTargets, replacements);
	}
	
	public static String processHtml(String template, ReplacementFunction replacementFunction)
	{
		template = PatternReplacer.indentedReplace(indentedHtmlCommentTarget, template, replacementFunction);
		template = PatternReplacer.indentedReplace(indentedInlineTarget, template, replacementFunction);
		
		return process(template, htmlTargets, replacementFunction);
	}
	
	public static String processJava(String template, Map<String,String> replacements)
	{
		return process(template, javaTargets, replacements);
	}
	
	public static String processJava(String template, ReplacementFunction replacementFunction)
	{
		return process(template, javaTargets, replacementFunction);
	}
	
	private static String process(String template, List<Pattern> targets, Map<String,String> replacements)
	{
		ReplacementFunction replacementFunction = createReplacementFunction(replacements);
		
		return process(template, targets, replacementFunction);
	}
	
	private static String process(String template, List<Pattern> targets, ReplacementFunction replacementFunction)
	{
		String str = template;
		
		for (Pattern target : targets)
		{
			str = PatternReplacer.replace(target, str, replacementFunction);
		}
		
		str = escapeCode.matcher(str).replaceAll("@_");
		
		return str;
	}
	
	private static ReplacementFunction createReplacementFunction(final Map<String,String> replacements)
	{
		return new ReplacementFunction()
		{
			@Override
			public String call(String key)
			{
				String value = replacements.get(key);
				if (value != null)
				{
					return value;
				}
				else
				{
					throw new IllegalArgumentException("Undefined key: [" + key + "]");
				}
			}
		};
	}
}
