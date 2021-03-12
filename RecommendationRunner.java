
import java.io.*;
import java.util.*;
import org.json.*;

public class RecommendationRunner implements Recommender {
    
    public static void main(String[] args) throws JSONException 
	{
	  RecommendationRunner rr = new RecommendationRunner();
	  ArrayList<String> movieId = rr.getItemsToRate();
	  
	  MovieDatabase moviedata = new MovieDatabase();
      moviedata.initialize("ratedmoviesfull.csv");
          
      JSONArray ja =  new JSONArray();
      for(String id: movieId){
          //System.out.println(id + "  " + moviedata.getTitle(id));
          JSONObject jo = new JSONObject();
          jo.put(id, moviedata.getTitle(id));
          ja.put(jo);
      }
	  
	  //System.out.println(ja.getJSONObject(0).toString());
	  
	  try (FileWriter file = new FileWriter("end/torate.json")) {
          //write JSONArray to the file
          file.write(ja.toString()); 
          file.flush();

      } catch (IOException e) {
          e.printStackTrace();
      }
	  
	  rr.writeHTML(movieId); 
	  
	  rr.printRecommendationsFor("561"); // Should be the last rater in the database
	}
	
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
        int numSimilarRaters = 50;
        
        ArrayList<Rating> AvgRatings = frRatings.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        
        int numRecommend = AvgRatings.size();
        //System.out.println("\nFound " + numRecommend + " similar movies.\n");
        
        StringBuilder sb = new StringBuilder();
        sb.append( "<html><body style = \"background-color:#dce7f3;\"><h1 style = 'font-family:Arial; text-align:center; font-style:italic;' > Movie Recommendations based on your Rates</h1>"
        + "<style> td {padding: 6px; border: 1px solid black; text-align: center;} th {background: black; color: white; font-weight: bold; padding: 6px; border: 1px solid grey; text-align: center;} "
        		+"table {margin-left: auto; margin-right: auto;}</style>");
        
        sb.append("<table> <tr> <th>Title</th> <th>Genre</th> </tr>");
        
        for(int i = 0; i < numRecommend; i++){
        		String id = AvgRatings.get(i).getItem();
            String title = moviedata.getTitle(id);
            String ref = "http://www.imdb.com/title/tt"+id+"/";
            String genre = moviedata.getGenres(id);
            sb.append("<tr> <td><a href=" + ref + ">" + title + "</td> <td>"+ genre +"</td> </tr>");
        }
        sb.append("</table><h2 style = 'font-family:Arial; text-align:center; font-style:italic; font-variant: small-caps;'>Enjoy!</h2>" + 
        		"</body></html>");
        
        if (numRecommend == 0){
            System.out.println("No matching movies Found according to your rates. Sorry!");
        }
        else{
            System.out.println(sb.toString());
        }
         
        
  	  	try (FileWriter file = new FileWriter("end/output.html")) {
  		  BufferedWriter writer = new BufferedWriter(file);
  	      writer.write(sb.toString());
  	      writer.newLine(); 
  	      writer.close(); 
  	  	} catch (Exception e) {
  	    	  //catch any exceptions here
  	  	}
    }
    
    public void writeHTML(ArrayList<String> items) {
    		StringBuilder sb = new StringBuilder();
    		sb.append("<html><body style = \"background-color:#dce7f3;\">" 
    				+ "<style> INPUT[type=submit] {font-weight: bold; display:block; margin: 0 auto;}</style>\n" 
    				+ "<h1 style = \"font-family:Arial; text-align:center; font-style:italic;\">Rate the following movies to create a recommendation for you!</h1>"
    				+ "<form action=\"\" method=\"post\">\n" 
    				+ "<table style = \"margin-left: auto; margin-right: auto;\">\n" 
    				+ " <tr> <th>Movie</th> <th>Rating</th> </tr>");
    		
    		MovieDatabase moviedata = new MovieDatabase();
        moviedata.initialize("ratedmoviesfull.csv");
        
        for(String id: items){
            String title = "<tr><td><a href=\"http://www.imdb.com/title/tt" + id + "/\">" + moviedata.getTitle(id) + "</td>"; 
            String radio = "<td>";
            for (int i = 0; i < 10; i++) {
            		String button = "<input type=\"radio\" name=\"" + id + "\" value=\"" + i + "\">" + i +"</input>";
            		radio += button;
            }
            radio += "</td>";
            title = title + radio +"</tr>";
            sb.append(title);
        }
    		sb.append("</table><input type=\"submit\" name=\"submit\" value=\"Submit\"></form> </body></html>");
    		
    		try (FileWriter file = new FileWriter("end/torate.html")) {
    	  		  BufferedWriter writer = new BufferedWriter(file);
    	  	      writer.write(sb.toString());
    	  	      writer.newLine(); 
    	  	      writer.close(); 
    	  	  	} catch (Exception e) {
    	  	    	  //catch any exceptions here
    	  	  	}
    }

}
