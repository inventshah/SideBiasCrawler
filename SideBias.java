import java.util.ArrayDeque;

/**
 * Sachin
 * September 2019
 * 
 * Main driver for side bias analysis
 */

public class SideBias
{   
    /**
     * Main method to record information for each topic and tournament
	*/
    public void sidebias()
    {
    	// Scape data from tabroom
        Crawler crawler = new Crawler();
        ArrayDeque<Round> rounds = new ArrayDeque<>();
        
        crawler.load_all();
    
        rounds = crawler.rounds;
        
        // Create output information objects
        CSVwriter tour = new CSVwriter("_tournamentBias");
        CSVwriter topic = new CSVwriter("_topicBias");

        String current_topic = rounds.getFirst().topic;
        String current_year = rounds.getFirst().year;
        String current_tournament = rounds.getFirst().tournament;
        
        Result topic_r = new Result();
        Result tournament_r = new Result();
        Result total_r = new Result();
        
        for (Round round : rounds)
        {
            if (!current_topic.equals(round.topic))
            {
                topic.println(current_topic+current_year + ", " + topic_r.toRow());

                current_topic = round.topic;
                current_year = round.year;
                
                topic_r = new Result();
            }
            
            if (!current_tournament.equals(round.tournament))
            {
                tour.println(current_tournament + ", " + tournament_r.toRow());
                
                current_tournament = round.tournament;
                
                tournament_r = new Result();
            }

            if (!round.winner.equals("bye"))
            {
            	topic_r.ballots(round.aff_ballots, round.neg_ballots);
                tournament_r.ballots(round.aff_ballots, round.neg_ballots);
                total_r.ballots(round.aff_ballots, round.neg_ballots);

                topic_r.rounds();
                tournament_r.rounds();
                total_r.rounds();

                if (round.winner.equals("neg"))
                {
                	topic_r.neg_wins();
                    tournament_r.neg_wins();
                    total_r.neg_wins();
                }
            }
        }
        topic.println(current_topic+current_year + ", " + topic_r.toRow());
        tour.println(current_tournament + ", " + tournament_r.toRow());
        System.out.println("=== Total ===");
        System.out.println(total_r.toRow());
    }
}
