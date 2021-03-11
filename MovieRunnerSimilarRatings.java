
import java.util.*;

public class MovieRunnerSimilarRatings {
    public void testdotProduct(){
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("copy.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        FourthRatings frRatings = new FourthRatings();
        ArrayList<Rating> simList = frRatings.getSimilarities("20");
        for(Rating r: simList){
            System.out.println(r.getItem()  + "  " + r.getValue());
        }
    }
    
    public void printSimilarRatings(){        
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        FourthRatings frRatings = new FourthRatings();
        
        String raterID = "71";
        int minimalRaters = 5;
        int numSimilarRaters = 20;
        
        ArrayList<Rating> AvgRatings = frRatings.getSimilarRatings(raterID, numSimilarRaters, minimalRaters);
        
        System.out.println("\nFound " + AvgRatings.size() + " similar movies.\n");
        
        for(Rating r: AvgRatings){
            System.out.println(moviedata.getTitle(r.getItem())  + "  " + r.getValue());
        }
        
    }
    
    public void printSimilarRatingsByGenre(){
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        FourthRatings frRatings = new FourthRatings();
        
        String raterID = "964";
        int minimalRaters = 5;
        int numSimilarRaters = 20;
        
        String genre = "Mystery";
        
        ArrayList<Rating> AvgRatings = frRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, new GenreFilter(genre));
        
        System.out.println("\nFound " + AvgRatings.size() + " similar movies.\n");
        
        for(Rating r: AvgRatings){
            System.out.println(moviedata.getTitle(r.getItem()) + "  " + r.getValue());
        }
    }
    
    public void printSimilarRatingsByDirector(){
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        FourthRatings frRatings = new FourthRatings();
        
        String raterID = "120";
        int minimalRaters = 2;
        int numSimilarRaters = 10;
        
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        
        ArrayList<Rating> AvgRatings = frRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, new DirectorsFilter(directors));
        
        System.out.println("\nFound " + AvgRatings.size() + " similar movies.\n");
        
        for(Rating r: AvgRatings){
            System.out.println(moviedata.getTitle(r.getItem()) + "  " + r.getValue());
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes(){
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        FourthRatings frRatings = new FourthRatings();
        
        String raterID = "168";
        int minimalRaters = 3;
        int numSimilarRaters = 10;
        
        int min = 80;
        int max = 160;
        
        String genre = "Drama";
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new MinutesFilter(min, max));
        allFilters.addFilter(new GenreFilter(genre));
        
        ArrayList<Rating> filterRatings = frRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, allFilters);
        
        System.out.println("\nFound " + filterRatings.size() + " filtered movies.\n");
        
        
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem())
            + " ; Minutes: " + moviedata.getMinutes(r.getItem()) + " ; Genres: " + moviedata.getGenres(r.getItem()) );
        }
        
    }

    public void printSimilarRatingsByYearAfterAndMinutes(){
        RaterDatabase raterdata = new RaterDatabase();
        raterdata.initialize("ratings.csv");
        System.out.println("\n");
        System.out.println(raterdata.size() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        FourthRatings frRatings = new FourthRatings();
        
        String raterID = "314";
        int minimalRaters = 5;
        int numSimilarRaters = 10;
        
        int min = 70;
        int max = 200;
        
        int year = 1975;
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new MinutesFilter(min, max));
        allFilters.addFilter(new YearAfterFilter(year));
        
        ArrayList<Rating> filterRatings = frRatings.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, allFilters);
        
        System.out.println("\nFound " + filterRatings.size() + " filtered movies.\n");
        
        
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem())
            + " ; Year: " + moviedata.getYear(r.getItem()) + " ; Minutes: " + moviedata.getMinutes(r.getItem()) );
        }
    }
}
