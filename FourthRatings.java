
import java.util.*;

public class FourthRatings {
    
    private double getAverageByID(String id, int minimalRaters){
        ArrayList<Double> raintingsByID = new ArrayList<Double>();
        double sumRatings = 0.0;
        for(Rater r: RaterDatabase.getRaters()){
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
        for(Rater r: RaterDatabase.getRaters()){
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
        for(Rater r: RaterDatabase.getRaters()){
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

    private double dotProduct(Rater me, Rater r){
        HashMap<String, Double> dotProds = new HashMap<String, Double>();
        
        for(String item: me.getItemsRated()){
         if(r.hasRating(item)){
             double dotPro = (me.getRating(item) -5) * (r.getRating(item) -5);
             dotProds.put(item, dotPro);
            }
         else{
             dotProds.put(item, 0.0);
        }
        }
        for(String item: r.getItemsRated()){
         if(!dotProds.containsKey(item)){
             dotProds.put(item, 0.0);
         }
        }
        
        double dotProduct = 0.0;
        for (Map.Entry<String, Double> set : dotProds.entrySet()) {
         dotProduct += set.getValue();
        }
        return dotProduct;
    }
    
    public ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> simList = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for(Rater r: RaterDatabase.getRaters()){
            if(r != me){
                Double dotProd = dotProduct(me, r);
                Rating simItem = new Rating(r.getID(), dotProd);
                simList.add(simItem);
            }
        }
        Collections.sort(simList, Collections.reverseOrder());
        return simList;
    }
    
    /*
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> weightedAvg = new ArrayList<Rating>();
        
        ArrayList<Rating> similarities = getSimilarities(id);
        
        ArrayList<Rating> topsimPositives = new ArrayList<Rating>();// Rating(raterId, similarity)
        for(int i = 0; i < numSimilarRaters; i++){
            if(similarities.get(i).getValue() > 0){
               topsimPositives.add(similarities.get(i));
            }
        }
         
        HashMap<String, ArrayList<Rating>> ratingCount = new HashMap<String, ArrayList<Rating>>();
        // mov, Rating(raterId, similarity)
        for(Rating r: topsimPositives){
            Rater rater = RaterDatabase.getRater(r.getItem());
            for(String i: rater.getItemsRated()){
                if(!ratingCount.containsKey(i)){
                    ArrayList<Rating> selectedsimPositives = new ArrayList<Rating>();
                    selectedsimPositives.add(r);
                    ratingCount.put(i, selectedsimPositives);
                }
                else{
                    ArrayList<Rating> selectedsimPositives = ratingCount.get(i);
                    selectedsimPositives.add(r);
                    ratingCount.put(i, selectedsimPositives);
                }
            }
        }
          
        ArrayList<Rating> weightedRatings = new ArrayList<Rating>();
        for (Map.Entry<String, ArrayList<Rating>> set : ratingCount.entrySet()){
            String movieID = set.getKey();
            ArrayList<Rating> simPositivesintop = set.getValue();
            double countsimPositivesintop = set.getValue().size();
            if(countsimPositivesintop >= minimalRaters){
                //System.out.println(movieID + "  " + countsimPositivesintop + ": ");
                double sumMulSimRbyR = 0.0;
                for(Rating r: simPositivesintop){
                    //System.out.println(r);// [RaterId  Similarity]
                    //System.out.println(RaterDatabase.getRater(r.getItem()).getRating(movieID)+"\n");
                    double simR = r.getValue();
                    double ratingR = RaterDatabase.getRater(r.getItem()).getRating(movieID);
                    sumMulSimRbyR += simR * ratingR;
                }         
                double weightedAvgratings = sumMulSimRbyR/countsimPositivesintop;
                String movTitle = MovieDatabase.getTitle(movieID);
                Rating weighted = new Rating(movTitle, weightedAvgratings);
                weightedRatings.add(weighted);
            }
        }
        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }
    */
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<Rating> similarities = getSimilarities(id);
        
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        for(String mov: movies){
            int countSim = 0;
            double sumWeighted = 0.0;
            for(int k = 0; k < numSimilarRaters; k++){ 
                // may need to consider the numSimilarRaters on a more serious scale
                Rating selectedRating = similarities.get(k);
                String selectedRaterId = selectedRating.getItem();
                Double selectedSim = selectedRating.getValue();
                Rater selectedRater = RaterDatabase.getRater(selectedRaterId);
                if(selectedRater.hasRating(mov)){
                    countSim += 1;
                    sumWeighted += selectedRater.getRating(mov) * selectedSim;
                }
            }
            if (countSim >= minimalRaters){
                double avgWeighted = sumWeighted/countSim;
                //String title = MovieDatabase.getTitle(mov);
                Rating selectedMov = new Rating(mov, avgWeighted);
                ret.add(selectedMov);
            }
        }
        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }
    
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria){
        ArrayList<String> filterMovIds = MovieDatabase.filterBy(filterCriteria);
        
        ArrayList<Rating> AvgSimRatingsNoFilter = getSimilarRatings(id, numSimilarRaters, minimalRaters);
        ArrayList<Rating> AvgSimRatingsFiltered = new ArrayList<Rating>();
        
        for(Rating r: AvgSimRatingsNoFilter){
            if(filterMovIds.contains(r.getItem())){
                AvgSimRatingsFiltered.add(r);
            }
        }
        
        return AvgSimRatingsFiltered;  
    }
    
}
