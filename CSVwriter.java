import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Sachin
 * September 2019
 * 
 * Helper class to append text to a csv file
 */

public class CSVwriter
{
    public PrintWriter p;
    
    public CSVwriter(String name)
    {
        FileWriter fw = null;
        BufferedWriter bw = null;

        try
        {
            fw = new FileWriter(name + ".csv", true);
            bw = new BufferedWriter(fw);
            p = new PrintWriter(bw, true);
            System.out.println("Successfully created csv writer: " + name);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Clear all content in a file and initalize it with a header value, then create CSVwriter
     *
     * @param  filename  target file without extension to be cleared
     * @param  init      initalization header value
     * @return new CSVwriter
     */
    public static CSVwriter create_new(String filename, String init)
    {
        try
        {
            PrintWriter pw = new PrintWriter(filename + ".csv");
            pw.println(init);
            pw.close();
            System.out.println("Successfully initalized file: " + filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new CSVwriter(filename);
    }
    
    /**
     * Clear all content in a file create a new CSVwriter 
     *
     * @param  filename  target file without extension to be cleared
     * @return new CSVwriter
     */
    public static CSVwriter create_new(String filename)
    {
        try
        {
            PrintWriter pw = new PrintWriter(filename + ".csv");
            pw.close();
            System.out.println("Successfully cleared file: " + filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return new CSVwriter(filename);
    }
    
    /**
     * Clear all content in a file and initalize it with a header value 
     *
     * @param  filename  target file to be cleared
     * @param  init      initalization header value
     */
    public static void reset(String filename, String init)
    {
        try
        {
            PrintWriter pw = new PrintWriter(filename);
            pw.println(init);
            pw.close();
            System.out.println("Successfully reset file: " + filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Print an item followed by a new line to output stream
     *
     * @param  o  any Object
     */
    public void println(Object o)
    {
        p.println(o.toString());
    }
    
    /**
     * Print an item to output stream
     *
     * @param  o  any Object
     */
    public void print(Object o)
    {
        p.print(o.toString());
    }
    
    /**
     * Write a csv row to output stream
     *
     * @param  o  list of any Object
     */
    public void row(Object... o)
    {
        for (int i = 0; i < o.length-1; i++)
        {
            print(o[i].toString());
            print(",");
        }
        print(o[o.length-1].toString());
        println("");
    }
    
    /**
     * Write a csv row to output stream
     *
     * @param  o  list of doubles
     */
    public void row(double... o)
    {
        for (int i = 0; i < o.length-1; i++)
        {
            print(o[i]);
            print(",");
        }
        print(o[o.length-1]);
        println("");
    }
    
    /**
     * Close output stream
     *
     */
    public void close()
    {
        p.close();
    }
}
