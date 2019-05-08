
import java.util.Comparator;

//
//  Provide initializers for the static fields, as instructed.
//
public class Comparators {

    public final static Comparator<Song> 
    artistComparator = new ArtistComparator();

    public final static Comparator<Song> 
    lambdaTitleComparator = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());

    public final static Comparator<Song> 
    keyExtractorYearComparator = Comparator.comparingInt(Song::getYear);

    public final static Comparator<Song> 
    twoFieldComparator = (Song s1, Song s2) -> {
        int result = s1.getArtist().compareTo(s2.getArtist());
        if (result == 0){
            result = s1.getYear() - s2.getYear();
        }
        return result; 
    };

    public final static Comparator<Song> 
    composedComparator = new ComposedComparator(artistComparator, lambdaTitleComparator);


    public final static Comparator<Song> 
    sortByArtistThenTitleThenYearComparator = (Song s1, Song s2) -> {
        int result = s1.getArtist().compareTo(s2.getArtist());
        if (result == 0){
            result = s1.getTitle().compareTo(s2.getTitle());
            if (result == 0){
                result = s1.getYear() - s2.getYear();
            }
        }
        return result; 
    }; 
    
}
