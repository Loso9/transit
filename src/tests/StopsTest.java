package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.*;
import datatypes.*;
import main.*;

import org.junit.Test;

public class StopsTest {

    private StopsInterface stops;
    private Stop stop1;
    private Stop stop2;
    private StopName stopName1;
    private StopName stopName2;

    public void setUp() {

        stopName1 = new StopName("stop1");
        stopName2 = new StopName("stop2");

        //not empty
        List<LineName> lines1 = new ArrayList<>();
        LineName line1 = new LineName("line1");
        LineName line2 = new LineName("line2");
        LineName line3 = new LineName("line3");
        lines1.add(line1);
        lines1.add(line2);
        lines1.add(line3);

        //empty
        ArrayList<LineName> lines2 = new ArrayList<>();

        stop1 = new Stop(stopName1, lines1);
        stop2 = new Stop(stopName2, lines2);
        Map<StopName, StopInterface> stopsMap = new HashMap<>();
        stopsMap.put(stop1.getStopName(), stop1);
        stopsMap.put(stop2.getStopName(), stop2);
        stops = new Stops(stopsMap);
    }

    @Test
    public void isStopLoadedTest() {
        setUp();
        assertTrue(stops.isStopLoaded(stopName1));
        assertTrue(stops.isStopLoaded(stopName2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLinesTest() throws FileNotFoundException {
        setUp();
        //expected 3 lines from stop1
        assertTrue(stops.isStopLoaded(stopName1));
        assertEquals(3, stop1.getLines().size());

        ArrayList<LineName> testLines = new ArrayList<>(stops.getLines(stopName1));
        LineName testLine1 = testLines.get(0);
        LineName testLine2 = testLines.get(1);
        LineName testLine3 = testLines.get(2);
        assertEquals(new LineName("line1"), testLine1);
        assertEquals(new LineName("line2"), testLine2);
        assertEquals(new LineName("line3"), testLine3);

        //stop2 should have empty list of lines, so calling first element should throw exception
        LineName exceptionLineName = stops.getLines(stop2.getStopName()).get(0);
    }

    @Test
    public void getStopTest() throws FileNotFoundException {
        setUp();
        assertEquals(stop1, stops.getStop(stopName1));
        assertEquals(stop2, stops.getStop(stopName2));
    }

    @Test
    public void cleanTest() {
        setUp();
        assertTrue(stops.getStops().size() != 0);
        stops.clean();
        assertEquals(0, stops.getStops().size());
    }

    @Test
    public void getReachableAtTest() throws FileNotFoundException {
        setUp();
        Pair<Time, LineName> getReachableAtTest = stops.getReachableAt(stopName1);
        //init
        assertEquals(Integer.MAX_VALUE, getReachableAtTest.getFirst().getTime());
        assertNull(getReachableAtTest.getSecond());

        Time changeTime = new Time(10);
        LineName lineNameChangeTest = new LineName("lineNameTest");

        stops.getStops().get(new StopName("stop1")).updateReachableAt(changeTime, lineNameChangeTest);

        Pair<Time, LineName> getReachableAtTestAfter = stops.getReachableAt(stopName1);
        assertEquals(10, getReachableAtTestAfter.getFirst().getTime());
        assertEquals(lineNameChangeTest, getReachableAtTestAfter.getSecond());
    }

    @Test
    public void setStartingStop() throws FileNotFoundException {
        setUp();
        Pair<Time, LineName> getReachableTest = stops.getReachableAt(stopName1);
        //init
        assertEquals(Integer.MAX_VALUE, getReachableTest.getFirst().getTime());
        assertNull(getReachableTest.getSecond());

        stops.setStartingStop(stopName1, new Time(50));

        Pair<Time, LineName> getReachableTestAfter = stops.getReachableAt(stopName1);
        assertEquals(50, getReachableTestAfter.getFirst().getTime());
        assertNull(getReachableTestAfter.getSecond());
    }

    @Test
    public void earliestReachableAfterTest() {
        setUp();
        //init
        assertEquals(Integer.MAX_VALUE, stops.getStops().get(stopName1).getReachableAt().getFirst().getTime());
        assertEquals(Integer.MAX_VALUE, stops.getStops().get(stopName2).getReachableAt().getFirst().getTime());
        //any time will return null (Optional.empty()), as both stops are not reachable
        assertEquals(Optional.empty(), stops.earliestReachableStopAfter(new Time(24)));
        stops.getStops().get(stopName1).updateReachableAt(new Time(50), null);
        stops.getStops().get(stopName2).updateReachableAt(new Time(24), null);

        //presentChecks for Optionals arent needed

        Optional<Pair<StopName, Time>> earliestReachableTest1 = stops.earliestReachableStopAfter(new Time(10));
        //earliest reachable should be stop2 as the time is closer
        assertEquals(new Time(24), earliestReachableTest1.get().getSecond());

        Optional<Pair<StopName, Time>> earliestReachableTest2 = stops.earliestReachableStopAfter(new Time(40));
        //as the stop2 is reachable in time(24) and stop1 in time(50), stop1 should be earliestReachable
        assertEquals(new Time(50), earliestReachableTest2.get().getSecond());

        Optional<Pair<StopName, Time>> earliestReachableTest3 = stops.earliestReachableStopAfter(new Time(60));
        //no stop should be reachable after 60, so it should return optional.empty()
        assertEquals(Optional.empty(), earliestReachableTest3);
    }

    @Test
    public void getStopsTest() {
        setUp();
        Map<StopName, StopInterface> expectedStopMap = new HashMap<>();
        expectedStopMap.put(stopName1, stop1);
        expectedStopMap.put(stopName2, stop2);
        assertEquals(expectedStopMap, stops.getStops());
    }
}
