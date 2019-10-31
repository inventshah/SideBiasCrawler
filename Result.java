/**
 * Sachin
 * September 2019
 * 
 * Store useful information about tournament results
 */

public class Result
{
    public int rounds = 0;
    public int neg_wins = 0;
    public int ballots = 0;
    public int neg_ballots = 0;
    
    /**
     * Add to total number of rounds
     *
     */
    public void rounds()
    {
        rounds++;
    }
    
    /**
     * Add to total number of negative wins
     *
     */
    public void neg_wins()
    {
        neg_wins++;
    }

    /**
     * Add to total number of ballots
     * 
     * @param  aff  affirmative ballots
     * @param  neg  negative ballots
     *
     */
    public void ballots(int aff, int neg)
    {
        ballots += aff + neg;
        neg_ballots += neg;
    }
    
    /**
     * Format n number of doubles to csv format 
     *
     * @param  items  any number of doubles
     * @return a single String with comma seperated values
     */
    public String csv(double... items)
    {
        String output = "";
        for (double d : items)
        {
            output = output + d + ", " ;
        }
        return output;
    }
    
    /**
     * Convert round to csv row format
     *
     */
    public String toRow()
    {
        return csv(rounds, neg_wins, ballots, neg_ballots);
    }
}
