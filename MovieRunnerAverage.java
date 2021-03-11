
import java.util.*;

public class MovieRunnerAverage {
    public void printAverageRatings(){
        //SecondRatings secRatings = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
        SecondRatings secRatings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        System.out.println(secRatings.getMovieSize() + " movies at all.");
        System.out.println(secRatings.getRaterSize() + " raters at all.");
        
        int minimalRaters = 12;
        ArrayList<Rating> AvgRatings = secRatings.getAverageRatings(minimalRaters);
        System.out.println(AvgRatings.size());
        double lowestRating = 10.0;
        String lowestTitle = "";
        for(Rating r: AvgRatings){
            //System.out.println(r.getValue() + " " + secRatings.getTitle(r.getItem()));
            if(lowestRating > r.getValue()){
                lowestRating = r.getValue();
                lowestTitle = secRatings.getTitle(r.getItem());
            }
        }
        System.out.println(lowestTitle + " " + lowestRating);
    }
    
    public void getAverageRatingOneMovie(){
        //SecondRatings secRatings = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
        SecondRatings secRatings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        String title = "Vacation";
        String id = secRatings.getID(title);
        int minimalRaters = 1;
        ArrayList<Rating> AvgRatings = secRatings.getAverageRatings(minimalRaters);
        for(Rating r: AvgRatings){
            if(r.getItem().equals(id)){
                System.out.println(title + " " + r.getValue());
            }
        }
    }
}
