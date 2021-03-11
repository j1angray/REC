
public class GenreFilter implements Filter {
    private String myGenre;
    
    public GenreFilter(String genre) {
    myGenre = genre;
    }
    
    public boolean satisfies(String id){
        if (MovieDatabase.getGenres(id).indexOf(myGenre) != -1){
            return true;
        }
        return false;
    }
}
