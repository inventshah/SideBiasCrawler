/**
 * Sachin
 * September 2019
 * 
 * Store data about each round
 */

public class Round
{   
    public String raw_winner;
    
    public String topic;
    public String tournament;
    public String year;
    public String round;
    
    public String winner;
    
    public int aff_ballots;
    public int neg_ballots;

    /**
     * Round Constructor
     *
     * @param  winner       tabroom's winner entry
     * @param  topic        the topic the round was on (SO, ND, or JF)
     * @param  tournament   the tournament the round is from
     * @param  year         the last two digits of the first year and the last two digits of the following year i.e. for 2019-2020 -> 1920
     * @param  round        the round designation on tabroom, i.e. Round 1, Quarters, etc
     */
    public Round(String winner, String topic, String tournament, String year, String round)
    {
        this.raw_winner = winner;
        
        this.topic = topic;
        this.tournament = tournament;
        this.year = year;
        this.round = round;
        
        char[] win_chars = winner.toLowerCase().toCharArray();
        int w_count = 0;
        for (Character c : win_chars)
        {
            if (c.equals('a'))
            {
                w_count++;
                aff_ballots++;
            }
            else if (c.equals('n'))
            {
                w_count--;
                neg_ballots++;
            }
        }
        
        if (w_count > 0)
        {
            this.winner = "aff";
        }
        else if (w_count < 0)
        {
            this.winner = "neg";
        }
        else
        {
            this.winner = "bye";
        }
    }
    
    /**
     * Format n number of Strings to csv format 
     *
     * @param  items  any number of Strings
     * @return a single String with comma seperated values
     */
    private String csv(String... items)
    {
        String output = "";
        for (String s : items)
        {
            output = output + s + "," ;
        }
        return output;
    }
    
    /**
     * Convert round to csv row format
     *
     */
    public String toRow()
    {
        return csv(year, topic, tournament, round, winner, raw_winner);
    }
}
