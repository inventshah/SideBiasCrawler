import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 * Sachin
 * September 2019
 * 
 * Web Crawler for tabroom.com
 * Pulls round results from target tournaments automatically using JSoup
 * 
 */

public class Crawler
{
    public String tabroom = "https://www.tabroom.com";

    public ArrayDeque<Round> rounds;

    public String url_file = "url.csv";
    
    public String raw_data = "_raw.csv";
    public String numeric_data = "_numeric.csv";
    
    public String current_year;
    public String current_topic;
    public String current_tournament;
    public String current_round;

    public CSVwriter raw_writer;
    public CSVwriter writer;

    public Crawler()
    {
        this.raw_writer = new CSVwriter("_raw");
        this.writer = new CSVwriter("_numeric");
        
        this.raw_writer.println("");
        this.writer.println("");
    }
    
    /**
     * Load all data
     *
     */
    public void load_all()
    {
        this.rounds = new ArrayDeque<>();
        this.init_rounds();
        
        this.load_new_urls();
        
        this.end();
    }
    
    /**
     * Close all data outputs
     * 
     */
    public void end()
    {
        this.raw_writer.close();
        this.writer.close();
        System.out.println("Finished loading data");
    }
    
    /**
     * Initalize round ArrayDeque from raw data file
     *
     */
    public void init_rounds()
    {
        System.out.println("Initalizing rounds with csv file");
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(raw_data));
            String entry = "";
            String[] data;
            
            entry = br.readLine();
            
            while ((entry = br.readLine()) != null)
            {
                data = entry.split(",");
                if (data.length == 8)
                {
                    Round round = new Round(data[4], data[1], data[2], data[0], data[3]);

                    rounds.add(round);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Iterate through all new tournament urls from url_file
     *
     */
    public void load_new_urls()
    {
        System.out.println("Loading data from new URLs");
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(url_file));
            String entry = "";
            
            String[] data;
            
            while ((entry = br.readLine()) != null)
            {
                data = entry.split(",");
                
                if (data.length >= 5)
                {
                    this.current_year = data[1];
                    this.current_topic = data[2];
                    this.current_tournament = data[3];
                    
                    this.tournament(data[4]);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Remove all problematic symbols such as ',' and '/' because their use in .csv and folder extensions
     *
     * @param  s  String for cleaning
     * @return cleaned String
     */
    public String clean(String s)
    {
        String o = s.replace(",","");
        o = o.replace("/", "");
        return o;
    }
    
    /**
     * Find all round pages for a particular tournament
     *
     * @param  site  full url for the target tournament
     */
    public void tournament(String site)
    {
        try
        {
            System.out.println("Loading " + current_tournament + " " + current_year + " from " + site);
            Document doc = Jsoup.connect(site).get();

            Elements links = doc.select("a");
            String site_path;
            Element e;
            
            for (int i = links.size() - 1; i > 0; i--)
            {
                e = links.get(i);
                site_path = e.attr("href");
                if (site_path.toLowerCase().contains("round_result"))
                {
                    this.current_round = clean(e.text().split(" ")[0] + " " + e.text().split(" ")[1]);
                    this.round(tabroom+site_path);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong at: " + current_tournament);
        }
    }
    
    /**
     * Print round data for a particular round
     *
     * @param  site  full url for the target round results page
     */
    public void round(String site)
    {
        int affBallots = 0, negBallots = 0;
        
        try
        { 
            Document doc = Jsoup.connect(site).get();
            System.out.println("---" + current_round + " from " + site);
            
            Elements e = doc.select("table#debate_results");
            
            Round round;

            for (Element row : e.select("tr"))
            {
                Elements tds = row.select("td:not([rowspan])");
                if (tds.size() >= 4)
                {
                    round = new Round(tds.get(3).text(), current_topic, current_tournament, current_year, current_round);
                    rounds.add(round);
                    
                    affBallots += round.aff_ballots;
                    negBallots += round.neg_ballots;
                    
                    raw_writer.println(round.toRow());
                }
            }

            writer.row(current_year, current_topic, current_tournament, current_round, affBallots, negBallots);
        }
        catch (IOException e)
        {
            System.out.println("Something went wrong at: " + current_tournament + " " + current_round);
        }
    }
}
