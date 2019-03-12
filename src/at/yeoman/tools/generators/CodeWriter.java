
package at.yeoman.tools.generators;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class CodeWriter extends PrintWriter
{
    private static final Pattern compoundNewline = Pattern.compile("\\r\\n");
    private static final Pattern simpleNewline = Pattern.compile("\\n");
    
    private int m_tabs;
    private boolean m_afterNewline;
    private String m_openingBracket = "{";
    private String m_closingBracket = "}";
    
    public CodeWriter(Writer w)
    {
        super(w);
    }
    
    public CodeWriter(Writer w, boolean autoflush)
    {
        super(w, autoflush);
    }
    
    public int getTabs()
    {
        return m_tabs;
    }
    
    public void changeIndentationBy(int diff)
    {
        if ((long) m_tabs + diff > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException("Attempt to indent to value beyond maximum " + ((long) m_tabs + diff));
        }
        else if ((long) m_tabs + diff < 0) // prevent positive overflow
        {
            throw new IllegalArgumentException("Attempt to indent to value below zero " + ((long) m_tabs + diff));
        }
        
        m_tabs += diff;
    }
    
    public void indent()
    {
        changeIndentationBy(1);
    }
    
    public void unindent()
    {
        changeIndentationBy(-1);
    }
    
    public void printIndentation(int tabOffset)
    {
        for (int idx = 0; idx < (m_tabs + tabOffset); ++idx)
        {
            super.print("    ");
        }
        
        m_afterNewline = false;
    }
    
    private void printIndentationIfNecessary(int tabOffset)
    {
        if (m_afterNewline)
        {
            printIndentation(tabOffset);
        }
    }
    
    public void nl()
    {
        super.println();
        
        m_afterNewline = true;
    }
    
    public void beginBlock()
    {
        beginBlock(m_openingBracket);
    }
    
    public void beginBlock(String openingBracket)
    {
        if (openingBracket != null)
        {
            print(openingBracket);
            indent();
            println();
        }
        else
        {
            indent();
        }
    }
    
    public void endBlock()
    {
        endBlock(m_closingBracket);
    }
    
    public void endBlock(String closingBracket)
    {
        if (m_closingBracket != null)
        {
            unindent();
            print(closingBracket);
            println();
        }
        else
        {
            unindent();
        }
    }
    
    @Override
    public void println()
    {
        printIndentationIfNecessary(0);
        nl();
    }
    
    @Override
    public void println(int tabOffset)
    {
        printIndentationIfNecessary(tabOffset);
        nl();
    }
    
    @Override
    public void println(boolean x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(boolean x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, boolean x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, boolean x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(char x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(char x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, char x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, char x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(long x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(long x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, long x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, long x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(float x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(float x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, float x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, float x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(double x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(double x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, double x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, double x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(char[] x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(char[] x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, char[] x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, char[] x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(String x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(String x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, String x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, String x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    @Override
    public void println(Object x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
        nl();
    }
    
    @Override
    public void print(Object x)
    {
        printIndentationIfNecessary(0);
        super.print(x);
    }
    
    public void println(int tabOffset, Object x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
        nl();
    }
    
    public void print(int tabOffset, Object x)
    {
        printIndentationIfNecessary(tabOffset);
        super.print(x);
    }
    
    public PrintWriter printfln(Locale l, String format, Object... args)
    {
        printIndentationIfNecessary(0);
        printf(l, format, args);
        nl();
        return this;
    }
    
    public PrintWriter printfln(int tabOffset, Locale l, String format, Object... args)
    {
        printIndentationIfNecessary(tabOffset);
        printf(l, format, args);
        nl();
        return this;
    }
    
    public PrintWriter printfln(String format, Object... args)
    {
        printIndentationIfNecessary(0);
        printf(format, args);
        nl();
        return this;
    }
    
    public CodeWriter printfln(int tabOffset, String format, Object... args)
    {
        printIndentationIfNecessary(tabOffset);
        printf(format, args);
        nl();
        return this;
    }
    
    public void setPythonMode()
    {
        m_openingBracket = null;
        m_closingBracket = null;
    }
    
    /**
     * @param prints
     *            preformatted code consisting of several lines, indenting each
     */
    public void printCode(String code)
    {
        Stream.of(compoundNewline.split(code))
            .flatMap(intermediate -> Stream.of(simpleNewline.split(intermediate)))
            .forEach(this::println);
    }
}
