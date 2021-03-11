
public class DirectorsFilter implements Filter{
    private String[] myDirectors;
    
    public DirectorsFilter(String directors){
        myDirectors = directors.split(",");
    }
   
    public boolean satisfies(String id){
        String selectedDirectors = MovieDatabase.getDirector(id);
        for(String d: myDirectors){
            if(selectedDirectors.indexOf(d) != -1){
                return true;
            }
        }
        return false;
    }

}

