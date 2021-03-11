
import java.util.*;

public class MovieRunnerWithFilters {
    public void printAverageRatings(){
        //ThirdRatings thdRatings = new ThirdRatings("ratings_short.csv");
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int minimalRaters = 35;
        ArrayList<Rating> AvgRatings = thdRatings.getAverageRatings(minimalRaters);
        System.out.println("Found " + AvgRatings.size() + " movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: AvgRatings){
            System.out.println(r.getValue() + " " + moviedata.getTitle(r.getItem()));
        }
        */
    }
    
    public void printAverageRatingsByYear(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        //ArrayList<String> movies = moviedata.filterBy(new YearAfterFilter(2000));
        int minimalRaters = 20;
        int year = 2000;
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, new YearAfterFilter(year));
        System.out.println("Found " + filterRatings.size() + " movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + " " + moviedata.getTitle(r.getItem()));
        }
        */
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int minimalRaters = 20;
        String genre = "Comedy";
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, new GenreFilter(genre));
        System.out.println("Found " + filterRatings.size() + " filtered movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem()) + "   " + moviedata.getGenres(r.getItem()));
        }
        */
    }
    
    public void printAverageRatingsByMinutes(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int minimalRaters = 5;
        int min = 105;
        int max = 135;
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, new MinutesFilter(min, max));
        System.out.println("Found " + filterRatings.size() + " filtered movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem()) + "   " + moviedata.getMinutes(r.getItem()));
        }
        */
    }
    
    public void printAverageRatingsByDirectors(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int minimalRaters = 4;
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, new DirectorsFilter(directors));
        System.out.println("Found " + filterRatings.size() + " filtered movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem()) + "   " + moviedata.getDirector(r.getItem()));
        }
        */
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int year = 1990;
        String genre = "Drama";
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(year));
        allFilters.addFilter(new GenreFilter(genre));
        
        int minimalRaters = 8;
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        /*
        for(Filter f: allFilters.filters){
            filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, f);
        }
        */
        
        System.out.println("Found " + filterRatings.size() + " filtered movies with a minimal rater of " + minimalRaters);
        
        /*
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem())
            + " ; Year: " + moviedata.getYear(r.getItem()) + " ; Genres: " + moviedata.getGenres(r.getItem()) );
        }
        */
    }
    
    public void printAverageRatingsByDirectorsAndMinutes(){
        ThirdRatings thdRatings = new ThirdRatings("ratings.csv");
        System.out.println(thdRatings.getRaterSize() + " raters at all.");
        
        MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        System.out.println(moviedata.size() + " movies at all.");
        
        int min = 90;
        int max = 180;
        
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new MinutesFilter(min, max));
        allFilters.addFilter(new DirectorsFilter(directors));
        
        int minimalRaters = 3;
        ArrayList<Rating> filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, allFilters);
        
        /*
        for(Filter f: allFilters.filters){
            filterRatings = thdRatings.getAverageRatingsByFilter(minimalRaters, f);
        }
        */
        
        System.out.println("Found " + filterRatings.size() + " filtered movies with a minimal rater of " + minimalRaters);
        
        
        for(Rating r: filterRatings){
            System.out.println(r.getValue() + "  " + moviedata.getTitle(r.getItem())
            + " ; Minutes: " + moviedata.getMinutes(r.getItem()) + " ; Directors: " + moviedata.getDirector(r.getItem()) );
        }
        
    }
}
