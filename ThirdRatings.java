
import java.util.*;

public class ThirdRatings {
    //private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;
    
    public ThirdRatings() {
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        //myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
    }
    
    
    public int getRaterSize(){
        return myRaters.size();
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
        
        HashMap<String, Rating> AverageRatingsAll = new HashMap<String, Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for(String mov: movies){
            Double avgRating = getAverageByID(mov, 1);
            AverageRatingsAll.put(mov, new Rating(mov, avgRating));
        }
        
        /*
        for (Map.Entry<String, Integer> set : ratingCount.entrySet()) {
            if(set.getValue() >= minimalRaters){
                Double avgRating = getAverageByID(set.getKey(), minimalRaters);
                AverageRatings.add(new Rating(set.getKey(), avgRating));
            }
        }
        */
        for (Map.Entry<String, Integer> set : ratingCount.entrySet()) {
            if(set.getValue() >= minimalRaters){
                Rating selectedRating = AverageRatingsAll.get(set.getKey());
                AverageRatings.add(selectedRating);
            }
        }
        
        return AverageRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> filterRatings = new ArrayList<Rating>();
        ArrayList<String> filterMovIds = MovieDatabase.filterBy(filterCriteria);
        
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
            if(set.getValue() >= minimalRaters && filterMovIds.contains(set.getKey())){
                Double avgRating = getAverageByID(set.getKey(), minimalRaters);
                filterRatings.add(new Rating(set.getKey(), avgRating));
            }
        }
        
        return filterRatings;
    }
    
}
