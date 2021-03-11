
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public String getTitle(String id){
        for(Movie mov: myMovies){
            if (mov.getID().equals(id)){
                return mov.getTitle();
            }
        }
        return ("ID " + id + " was not found.");
    }
    
    public String getID(String title){
        for(Movie mov: myMovies){
            if (mov.getTitle().equals(title)){
                return mov.getID();
            }
        }
        return ("Title " + title + " was not found.");
    }
    
    private double getAverageByID(String id, int minimalRaters){
        ArrayList<Double> raintingsByID = new ArrayList<Double>();
        double sumRatings = 0.0;
        for(EfficientRater r: myRaters){
            if (r.hasRating(id)){
                raintingsByID.add(r.getRating(id));
            }
        }
        if(raintingsByID.size() >= minimalRaters){
            for (double r: raintingsByID){
                sumRatings += r;
            }
            return sumRatings/raintingsByID.size();
        }
        return 0.0;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> AverageRatings = new ArrayList<Rating>();
        
        HashMap<String, Integer> ratingCount = new HashMap<String, Integer>();
        for(EfficientRater r: myRaters){
            ArrayList<String> RatedItems = r.getItemsRated();
            for(String i: RatedItems){
                if(!ratingCount.containsKey(i)){
                    ratingCount.put(i, 1);
                }
                else{
                    ratingCount.put(i, ratingCount.get(i) + 1);
                }
            }
        }
        
        for (Map.Entry<String, Integer> set : ratingCount.entrySet()) {
            if(set.getValue() >= minimalRaters){
                Double avgRating = getAverageByID(set.getKey(), minimalRaters);
                AverageRatings.add(new Rating(set.getKey(), avgRating));
            }
        }
        
        return AverageRatings;
    }
}
