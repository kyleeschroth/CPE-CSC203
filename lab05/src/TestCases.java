import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.assertNotNull;
import static edu.calpoly.testy.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import edu.calpoly.testy.Testy;

public class TestCases
{
    private static final Song[] songs = new Song[] {
        new Song("Lene Lovich", "New Toy", 1981),
        new Song("Kate Bush", "The Wedding List", 1980),
        new Song("Lene Lovich", "Lucky Number", 1979),
        new Song("Pizzicato 5", "Twiggy Twiggy", 1991),
        new Song("Lene Lovich", "It's You, Only You (Mein Schmerz)", 1982),
        new Song("Kate Bush", "Babooshka", 1980),
        new Song("Devo", "Beautiful World", 1981),
        new Song("Devo", "Be Stiff", 1978),
        new Song("Lene Lovich", "Be Stiff", 1978),
        new Song("Devo", "Freedom of Choice", 1980),
        new Song("Anonymous", "Sumer is Icuumen In", 1250),
        new Song("Thomas Dolby", "She Blinded Me with Science", 1982),
        new Song("Kate Bush", "Them Heavy People", 1978)
    };

    public void testArtistComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.artistComparator);
        Song[] test = new Song[] { songs[0], songs[1] };
        Arrays.sort(test, new ArtistComparator());
        Song[] result = new Song[] { songs[1], songs[0] };
        assertEquals(test, result);

        //Song[] test = new Song [] { new Song("devo", "aaaa", 1970), new Song("devo", "bbbb", 1980)}; 
        //testArtist = new ArtistComparator(test); 
        //assertEquals(testArtist, 0); 
    }

    public void testLambdaTitleComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.lambdaTitleComparator);
        Comparator<Song> compTitle = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());

        Song[] test1 = new Song[] { songs[2], songs[7] };
        Arrays.sort(test1, compTitle);
        Song[] result1 = new Song[] { songs[7], songs[2] };
        assertEquals(test1, result1);
    }


    public void testKeyExtractorYearComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.keyExtractorYearComparator);
        Comparator<Song> compYear = Comparator.comparing(Song::getYear, (s1,s2) -> s2.compareTo(s1));

        Song[] test2 = new Song[] { songs[5], songs[0] };
        Arrays.sort(test2, compYear);
        Song[] result2 = new Song[] { songs[0], songs[5] };
        assertEquals(test2, result2);
    }

    public void testTwoFieldComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.twoFieldComparator);
    }

    public void testComposedComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.composedComparator);
        Comparator<Song> compareYear = Comparator.comparing(Song::getYear, (s1,s2) -> s1.compareTo(s2));
        Comparator<Song> compareTitle = (Song s1, Song s2) -> s1.getTitle().compareTo(s2.getTitle());
        // Test1
        Comparator<Song> artistThenYear = new ComposedComparator(new ArtistComparator(), compareYear);
        Song[] songList1 = new Song[] {songs[3], songs[7]};
        Arrays.sort(songList1, artistThenYear);
        Song[] expected1 = new Song[] {songs[7], songs[3]};
        assertEquals(expected1, songList1);

        // Test 2
        Comparator<Song> yearThenTitle = new ComposedComparator(compareYear, compareTitle);
        Song[] songList3 = new Song[] {songs[1], songs[0]};
        Arrays.sort(songList3, yearThenTitle);
        Song[] expected3 = new Song[] {songs[1], songs[0]};
        assertEquals(expected3, songList3);

        // Test 3 
        Comparator<Song> titleThenArtist = new ComposedComparator(compareTitle, new ArtistComparator());
        Song[] songList2 = new Song[] {songs[3], songs[5]};
        Arrays.sort(songList2, titleThenArtist);
        Song[] expected2 = new Song[] {songs[5], songs[3]};
        assertEquals(expected2, songList2);
    }

    public void testSort()
    {
        // This method is already complete.
        List<Song> songList = new ArrayList<>(Arrays.asList(songs));
        List<Song> expectedList = Arrays.asList(
            new Song("Anonymous", "Sumer is Icuumen In", 1250),
            new Song("Devo", "Be Stiff", 1978),
            new Song("Devo", "Beautiful World", 1981),
            new Song("Devo", "Freedom of Choice", 1980),
            new Song("Kate Bush", "Babooshka", 1980),
            new Song("Kate Bush", "The Wedding List", 1980),
            new Song("Kate Bush", "Them Heavy People", 1978),
            new Song("Lene Lovich", "Be Stiff", 1978),
            new Song("Lene Lovich", "It's You, Only You (Mein Schmerz)", 1982),
            new Song("Lene Lovich", "Lucky Number", 1979),
            new Song("Lene Lovich", "New Toy", 1981),
            new Song("Pizzicato 5", "Twiggy Twiggy", 1991),
            new Song("Thomas Dolby", "She Blinded Me with Science", 1982)
        );

        assertNotNull(Comparators.sortByArtistThenTitleThenYearComparator);
        songList.sort(Comparators.sortByArtistThenTitleThenYearComparator);

        assertEquals(songList, expectedList);
    }

    public void runTests() 
    {
        Testy.run(
            () -> testArtistComparator(),
            () -> testLambdaTitleComparator(),
            () -> testKeyExtractorYearComparator(),
            () -> testTwoFieldComparator(),
            () -> testComposedComparator(),
            () -> testSort()
        );
    }
}
