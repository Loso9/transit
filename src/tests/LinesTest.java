package tests;

import static org.junit.Assert.*;

import exceptions.FullBusException;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;
import datatypes.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

public class LinesTest {

    private Line line1;
    private Line line2;
    private LineName lineName1;
    private LineName lineName2;
    private LineSegmentInterface lineSegment22;
    private StopInterface nextStop2; //stops used in updateReachable test
    private StopInterface nextStop3;
    private Lines lines;

    public void setUp() throws NegativeCapacityException, NegativeSegmentIndexException {

        lineName1 = new LineName("line1");
        lineName2 = new LineName("line2");
        StopName firstStop1 = new StopName("firstStop1");
        StopName firstStop2 = new StopName("firstStop2");
        List<Time> startingTimes1 = new ArrayList<>();
        startingTimes1.add(new Time(4));
        startingTimes1.add(new Time(5));
        startingTimes1.add(new Time(6));

        List<Time> startingTimes2 = new ArrayList<>();
        startingTimes2.add(new Time(22));
        startingTimes2.add(new Time(23));

        List<LineSegmentInterface> lineSegments1 = new ArrayList<>();
        //1 LineSegment
        int capacity = 2;
        TimeDiff timeToNextStop1 = new TimeDiff(2);
        Map<Time, Integer> numOfPassengers1 = new HashMap<>();
        for (Time time : startingTimes1) {
            numOfPassengers1.put(new Time(time.getTime() + timeToNextStop1.getTimeDiff()), 0);
        }
        List<LineName> lines1 = new ArrayList<>();
        lines1.add(lineName1);
        StopInterface nextStop1 = new Stop(new StopName("stopName2"), lines1);
        LineSegmentInterface lineSegment1 = new LineSegment(timeToNextStop1, numOfPassengers1, capacity, lineName2, nextStop1, 0);
        lineSegments1.add(lineSegment1);

        line1 = new Line(lineName1, firstStop1, lineSegments1, startingTimes1);

        //2 LineSegments
        List<LineSegmentInterface> lineSegments2 = new ArrayList<>();
        //1st lineSegment
        capacity = 1;
        TimeDiff timeToNextStop21 = new TimeDiff(1);
        Map<Time, Integer> numOfPassengers2 = new HashMap<>();
        for (Time time : startingTimes2) {
            numOfPassengers2.put(new Time(time.getTime() + timeToNextStop21.getTimeDiff()), 0);
        }
        List<LineName> lines2 = new ArrayList<>();
        lines2.add(lineName2);
        nextStop2 = new Stop(new StopName("stopName2"), lines2);
        LineSegmentInterface lineSegment21 = new LineSegment(timeToNextStop21, numOfPassengers2, capacity, lineName2, nextStop2, 0);
        lineSegments2.add(lineSegment21);

        //2nd lineSegment
        TimeDiff timeToNextStop22 = new TimeDiff(2);
        Map<Time, Integer> numOfPassengers3 = new HashMap<>();
        for (Time time : startingTimes2) {
            numOfPassengers3.put(new Time(time.getTime() + timeToNextStop21.getTimeDiff() + timeToNextStop22.getTimeDiff()), 0);
        }
        nextStop3 = new Stop(new StopName("stopName3"), lines2);
        lineSegment22 = new LineSegment(timeToNextStop22, numOfPassengers3, capacity, lineName2, nextStop3, 1);
        lineSegments2.add(lineSegment22);

        line2 = new Line(lineName2, firstStop2, lineSegments2, startingTimes2);
        Map<LineName, LineInterface> linesMap = new HashMap<>();
        linesMap.put(lineName1, line1);
        linesMap.put(lineName2, line2);
        lines = new Lines(linesMap);
    }

    @Test
    public void isLineLoaded() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        assertTrue(lines.isLineLoaded(lineName1));
        assertTrue(lines.isLineLoaded(lineName2));
    }

    @Test
    public void cleanTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        assertTrue(lines.getLines().size() != 0);
        lines.clean();
        assertEquals(0, lines.getLines().size());
    }

    @Test
    public void updateReachableTest() throws NegativeCapacityException, FileNotFoundException, NegativeSegmentIndexException {
        setUp();
        List<LineName> lineNameList = new ArrayList<>();
        lineNameList.add(lineName2);

        //init
        assertEquals(Integer.MAX_VALUE, nextStop2.getReachableAt().getFirst().getTime());
        assertNull(nextStop2.getReachableAt().getSecond());
        assertEquals(Integer.MAX_VALUE, nextStop3.getReachableAt().getFirst().getTime());
        assertNull(nextStop3.getReachableAt().getSecond());

        lines.updateReachable(lineNameList, lines.getLines().get(lineName2).getFirstStop(), new Time(1));

        assertEquals(23, nextStop2.getReachableAt().getFirst().getTime());
        assertEquals(line2.getLineName(), nextStop2.getReachableAt().getSecond());

        assertEquals(25, nextStop3.getReachableAt().getFirst().getTime());
        assertEquals(line2.getLineName(), nextStop3.getReachableAt().getSecond());
    }

    @Test
    public void updateCapacityAndGetPreviousStopTest() throws NegativeCapacityException, FileNotFoundException, FullBusException, NegativeSegmentIndexException {
        setUp();
        //before updating capacity
        assertEquals(0, lineSegment22.getPassengersAt(new Time(26)));
        StopName previousStop = lines.updateCapacityAndGetPreviousStop(lineName2, new StopName("stopName3"), new Time(26));
        //after updating capacity
        assertNotEquals(0, lineSegment22.getPassengersAt(new Time(26)));
        assertEquals(1, lineSegment22.getPassengersAt(new Time(26)));
        assertEquals("stopName2", previousStop.getName());

    }

    @Test
    public void getLinesTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        Map<LineName, LineInterface> expectedLinesMap = new HashMap<>();
        expectedLinesMap.put(lineName1, line1);
        expectedLinesMap.put(lineName2, line2);
        assertEquals(expectedLinesMap, lines.getLines());
    }
}
