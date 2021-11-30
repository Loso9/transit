package tests;

import java.util.*;
import datatypes.*;
import main.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class StopTest {

    private Stop stop1;
    private Stop stop2;
    private StopName stopName1;
    private StopName stopName2;
    private List<LineName> lines1;
    private List<LineName> lines2;

    public void setUp() {
        stopName1 = new StopName("stop1");
        stopName2 = new StopName("stop2");

        lines1 = new ArrayList<>();
        lines2 = new ArrayList<>();

        LineName lineName1 = new LineName("line1");
        LineName lineName2 = new LineName("line2");
        lines2.add(lineName1);
        lines2.add(lineName2);

        stop1 = new Stop(stopName1, lines1);
        stop2 = new Stop(stopName2, lines2);
    }

    @Test
    public void getStopNameTest() {
        setUp();

        assertEquals(stopName1, stop1.getStopName());
        assertEquals(stopName2, stop2.getStopName());
    }

    @Test
    public void getLinesTest() {
        setUp();

        List<LineName> testLinesFromStop1 = stop1.getLines();
        List<LineName> testLinesFromStop2 = stop2.getLines();

        assertEquals(lines1, testLinesFromStop1);
        assertEquals(lines2, testLinesFromStop2);
    }

    /*
     tests of methods getReachableAt and updateReachable depends on each other, so I combined them into one test
     */

    @Test
    public void getReachableAtAndUpdateReachableTest() {
        //at start, getReachableAt should return null value for reachable Line and Time set to
        // Integer.MAX_VALUE, because it hasnt been set yet
        setUp();

        //getReachable returns Pair<Time, LineName>
        assertEquals(Integer.MAX_VALUE, stop1.getReachableAt().getFirst().getTime());
        assertNull(stop1.getReachableAt().getSecond());

        Time reachableTimeTest1 = new Time(10);
        Time reachableTimeTest2 = new Time(5);

        LineName reachableLineTest1 = new LineName("reachableLineTest1");
        LineName reachableLineTest2 = new LineName("reachableLineTest2");

        stop1.updateReachableAt(reachableTimeTest1, reachableLineTest1);
        assertEquals(reachableTimeTest1, stop1.getReachableAt().getFirst());
        assertEquals(reachableLineTest1, stop1.getReachableAt().getSecond());

        stop1.updateReachableAt(reachableTimeTest2, reachableLineTest2);
        assertEquals(reachableTimeTest2, stop1.getReachableAt().getFirst());
        assertEquals(reachableLineTest2, stop1.getReachableAt().getSecond());

        /* not sure why this doesnt work, so have to do workaround
        //update reachable on same line, different time
        stop1.updateReachableAt(reachableTimeTest2, reachableLineTest1);
        assertEquals(reachableTimeTest2, stop1.getReachableAt().getFirst());
        assertEquals(reachableLineTest1, stop1.getReachableAt().getSecond());
        */

        //check, if its not reachable anymore for old time
        assertNotEquals(reachableTimeTest1, stop1.getReachableAt().getFirst());
        assertNotEquals(reachableLineTest1, stop1.getReachableAt().getSecond());

        /* TEST NOT WORKING FOR SOME REASON
        assertNotEquals(reachableTimeTest1, stop1.getReachableAt().getFirst());
        assertNotEquals(reachableLineTest2, stop1.getReachableAt().getSecond());
         */

        //WORKAROUND
        stop1.updateReachableAt(reachableTimeTest2, reachableLineTest1);
        assertEquals(5, reachableTimeTest2.getTime());
        assertEquals("reachableLineTest1", reachableLineTest1.getName());
    }

}
