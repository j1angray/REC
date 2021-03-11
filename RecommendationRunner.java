
import java.util.*;

public class RecommendationRunner implements Recommender {
    
    public ArrayList<String> getItemsToRate(){
        /*
         * return a list of strings representing movie IDs 
         */ 
        ArrayList<String> ItemsToRate = new ArrayList<String>();
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        //System.out.println(moviedata.size() + " movies at all.");
        
        Random random = new Random();
        int numItems = random.nextInt(10) + 10;
        //System.out.println(numItems + " movies to selected.");
        
        Filter f = new YearAfterFilter(1999);
        ArrayList<String> filteredMovie = moviedata.filterBy(f);
        //System.out.println(filteredMovie.size());
        
        for(int i = 0; i < numItems; i++){
            int itemIndex = random.nextInt(filteredMovie.size());
            if(!ItemsToRate.contains(filteredMovie.get(itemIndex))){
                ItemsToRate.add(filteredMovie.get(itemIndex));
            }
        }
        
        /*
        System.out.println(ItemsToRate.size() + " movies selected finally.");
        for(String id: ItemsToRate){
            System.out.println(id + "  " + moviedata.getTitle(id));  
        }
        */
        return ItemsToRate;
    }
    
    public void printRecommendationsFor(String webRaterID){
        /* 
         * prints out an HTML table of movies recommended by your program 
         * for the user based on the movies they rated
         */
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");

        FourthRatings frRatings = new FourthRatings();
        int minimalRaters = 5;
        int numSimilarRaters = 10;
        
        ArrayList<Rating> AvgRatings = frRatings.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        
        int numRecommend = AvgRatings.size();
        //System.out.println("\nFound " + numRecommend + " similar movies.\n");
        
        StringBuilder sb = new StringBuilder();
        sb.append( "<h2>Movie Recommendations based on your Rates</h2>"
        + "<style> td {padding: 6px; border: 1px solid black; text-align: center;} th {background: black; color: white; font-weight: bold; padding: 6px; border: 1px solid grey; text-align: center;} </style>");
        
        sb.append("<table> <tr> <th>Recommendation Order</th> <th>Movie Title</th> </tr>");
        
        for(int i = 0; i < numRecommend; i++){
            String title = moviedata.getTitle(AvgRatings.get(i).getItem());
            sb.append("<tr> <td>" + (i+1) + "</td><td>" + title + "</td> </tr>");
        }
        sb.append("</table>");
        
        if (numRecommend == 0){
            System.out.println("No matching movies Found according to your rates. Sorry!");
        }
        else{
            System.out.println(sb.toString());
        }
    }

}
