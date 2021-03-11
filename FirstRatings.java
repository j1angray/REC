
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename){
        /* process every record from the CSV file whose name is filename, 
           a file of movie information, 
           and return an ArrayList of type Movie 
           with all of the movie data from the file.
        */
        ArrayList<Movie> movList = new ArrayList<Movie>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            //System.out.println(record.get("title"));
            Movie movRec = new Movie(record.get("id"), record.get("title"), 
            record.get("year"), record.get("genre"), record.get("director"), 
            record.get("director"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            movList.add(movRec);
        }
        return movList;
    }
    
    public void testLoadMovies(){
        //ArrayList<Movie> movList = loadMovies("ratedmovies_short.csv");
        ArrayList<Movie> movList = loadMovies("ratedmoviesfull.csv");
        System.out.println("\nThere are " + movList.size() + " movies at all.");
        /*
        for(Movie mov: movList){
            System.out.println(mov.toString());
        }
        */
        int countComedy = 0;
        int count150Min = 0;
        int countMaxdirected = 0;
        HashMap<String, Integer> countDirected = new HashMap<String, Integer>();
        for(Movie mov: movList){
            if(mov.getGenres().indexOf("Comedy") != -1){
                countComedy += 1;
            }
            if(mov.getMinutes() > 150){
                count150Min += 1;
            }
            String[] directors = mov.getDirector().split(", ");
            for(String dir: directors){
                if (!countDirected.containsKey(dir)){
                    countDirected.put(dir, 1);
                }
                else{
                    countDirected.put(dir, countDirected.get(dir) + 1);
                }
            }
        }
        System.out.println("\nMovies include the Comedy genre: " + countComedy);
        System.out.println("\nMovies greater than 150 minutes: " + count150Min);
        for (Map.Entry<String, Integer> set : countDirected.entrySet()) {
            if (countMaxdirected < set.getValue()){
                countMaxdirected = set.getValue();
            }
        }
        System.out.println("\nMaximum number of movies by any director: " + countMaxdirected);
        System.out.println("Directors directed "+ countMaxdirected +" movies: ");
        for (Map.Entry<String, Integer> set : countDirected.entrySet()) {
            if (countMaxdirected == set.getValue()){
                System.out.println(set.getKey());
            }
        }
    }
    
    public ArrayList<EfficientRater> loadRaters(String filename){
        /* process every record from the CSV file whose name is filename, 
           a file of raters and their ratings, 
           and return an ArrayList of type Rater 
           with all the rater data from the file.
        */
        ArrayList<EfficientRater> rateList = new ArrayList<EfficientRater>();
        ArrayList<String> raterIds = new ArrayList<String>();
        
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser){
            if(!raterIds.contains(record.get("rater_id"))){
                raterIds.add(record.get("rater_id"));
                EfficientRater rate = new EfficientRater(record.get("rater_id"));
                rate.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                rateList.add(rate);
            }
            else{
                for(EfficientRater r: rateList){
                    if (r.getID().equals(record.get("rater_id"))){
                        int rateIdx = rateList.indexOf(r);
                        r.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                        rateList.set(rateIdx,r);
                    }
                }
            }
        }
        return rateList;
    }
    
    public void testLoadRaters(){
        //ArrayList<Rater> rateList = loadRaters("ratings_short.csv");
        ArrayList<EfficientRater> rateList = loadRaters("ratings.csv");
        System.out.println("\nThere are " + rateList.size() + " raters at all.");
        /*
        for(Rater r: rateList){
            System.out.println("Rater's ID: " + r.getID() +"; number of ratings: " + r.numRatings());
            ArrayList<String> items = r.getItemsRated();
            for(String i: items){
                System.out.println("Movie_id: " + i + "; rating: " + r.getRating(i));
            }
        }
        */
        
        System.out.println("\nNumbers of ratings from the rater which rater_id is 193: ");
        for(EfficientRater r: rateList){
            if (r.getID().equals("193")){
                ArrayList<String> items = r.getItemsRated();
                System.out.println(items.size());
                /*
                for(String i: items){
                    System.out.println("Movie_id: " + i + "; rating: " + r.getRating(i));
                }
                */
            }
        }
        
        int countRatings = 0;
        int countRaters = 0;
        for(EfficientRater r: rateList){
            if (countRatings < r.numRatings()){
                countRatings = r.numRatings();
            }
        }
        System.out.println("\nMaximum number of ratings by any rater: " + countRatings);
        System.out.println("Ids of the rater with the maximum number of ratings: ");
        for(EfficientRater r: rateList){
            if (r.numRatings() == countRatings){
                System.out.println(r.getID());
                countRaters += 1;
            }
        }
        System.out.println("Numbers of raters have this maximum number of ratings: " + countRaters);
        
        int countMovieratings = 0;
        String movieId = "1798709";
        for(EfficientRater r: rateList){
            if(r.hasRating(movieId)){
                countMovieratings += 1;
            }
        }
        System.out.println("\nnumber of ratings for " + movieId + " : " + countMovieratings);
        
        ArrayList<String> movieIds = new ArrayList<String>();
        for(EfficientRater r: rateList){
            ArrayList<String> items = r.getItemsRated();
            for(String item: items){
                if(!movieIds.contains(item)){
                    movieIds.add(item);
                }
            }
        }
        System.out.println("\nnumber of movies rated by all these raters: " + movieIds.size());
    }
}
