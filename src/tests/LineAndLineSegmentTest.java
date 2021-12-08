package tests;

import exceptions.FullBusException;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;
import datatypes.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class LineAndLineSegmentTest {

    private LineInterface line1;
    private LineInterface line2;
    private LineName lineName1;
    private LineName lineName2;
    private LineName updateReachable;
    private LineInterface lineUpdateReachable;
    private LineSegmentInterface lineSegment1;
    private LineSegmentInterface lineSegment21;
    private LineSegmentInterface lineSegment22;
    private LineSegmentInterface lineSegment23;
    private LineSegmentInterface lineSegmentUpdateReachable1;
    private LineSegmentInterface lineSegmentUpdateReachable2;
    private StopInterface stop1;
    private StopInterface stop2;


    public void setUp() throws NegativeCapacityException, NegativeSegmentIndexException {

        //Line for UpdateReachable Test
        updateReachable = new LineName("UpdateReachableLine");

        List<Time> startingTimesUpdateReachable = new ArrayList<>();
        startingTimesUpdateReachable.add(new Time(1));

        List<LineName> linesForStopsUpdateReachable = new ArrayList<>();
        linesForStopsUpdateReachable.add(updateReachable);

        stop1 = new Stop(new StopName("stop1"), linesForStopsUpdateReachable);
        stop2 = new Stop(new StopName("stop2"), linesForStopsUpdateReachable);

        Map<Time, Integer> numOfPassengersUpdateReachable1 = new HashMap<>();
        numOfPassengersUpdateReachable1.put(new Time(3), 3);

        Map<Time, Integer> numOfPassengersUpdateReachable2 = new HashMap<>();
        numOfPassengersUpdateReachable2.put(new Time(6), 2);

        lineSegmentUpdateReachable1 = new LineSegment(new TimeDiff(2), numOfPassengersUpdateReachable1, 5, updateReachable, stop1, 0);
        lineSegmentUpdateReachable2 = new LineSegment(new TimeDiff(3), numOfPassengersUpdateReachable2, 5, updateReachable, stop2, 1);
        List<LineSegmentInterface> lineSegmentsUpdateReachable = new ArrayList<>();
        lineSegmentsUpdateReachable.add(lineSegmentUpdateReachable1);
        lineSegmentsUpdateReachable.add(lineSegmentUpdateReachable2);
        lineUpdateReachable = new Line(updateReachable, new StopName("firstStopUpdateReachable"), lineSegmentsUpdateReachable, startingTimesUpdateReachable);
        //line1
        lineName1 = new LineName("Line1");
        StopName firstStop1 = new StopName("firstStop1");
        List<Time> startingTimes1 = new ArrayList<>();
        startingTimes1.add(new Time(5));
        startingTimes1.add(new Time(6));
        startingTimes1.add(new Time(7));
        startingTimes1.add(new Time(8));
        //1 LineSegment
        List<LineSegmentInterface> lineSegments1 = new ArrayList<>();
        TimeDiff timeToNextStop1 = new TimeDiff(11);
        Map<Time, Integer> numOfPassengers = new HashMap<>();
        for (Time time : startingTimes1) {
            numOfPassengers.put(new Time(time.getTime() + timeToNextStop1.getTimeDiff()), 0);
        }
        int capacity = 2;
        List<LineName> lines1 = new ArrayList<>();
        lines1.add(lineName1);
        StopInterface nextStop = new Stop(new StopName("nextStop"), lines1);
        lineSegment1 = new LineSegment(timeToNextStop1, numOfPassengers, capacity, lineName1, nextStop, 0);
        lineSegments1.add(lineSegment1);
        line1 = new Line(lineName1, firstStop1, lineSegments1, startingTimes1);

        //line2
        lineName2 = new LineName("Line2");
        StopName firstStop2 = new StopName("firstStop2");
        List<Time> startingTimes2 = new ArrayList<>();
        startingTimes2.add(new Time(4));
        startingTimes2.add(new Time(6));
        startingTimes2.add(new Time(8));
        startingTimes2.add(new Time(10));

        //3 lineSegments
        List<LineSegmentInterface> lineSegments2 = new ArrayList<>();
        capacity = 1;
        //1st lineSegment
        TimeDiff timeToNextStop21 = new TimeDiff(5);
        Map<Time, Integer> numOfPassengers1 = new HashMap<>();
        for (Time time : startingTimes2) {
            numOfPassengers1.put(new Time(time.getTime() + timeToNextStop21.getTimeDiff()), 1);
        }
        List<LineName> lines2 = new ArrayList<>();
        lines2.add(lineName2);
        StopInterface nextStop1 = new Stop(new StopName("stopName2"), lines2);
        lineSegment21 = new LineSegment(timeToNextStop21, numOfPassengers1, capacity, lineName2, nextStop1, 0);
        lineSegments2.add(lineSegment21);

        //2nd lineSegment
        TimeDiff timeToNextStop22 = new TimeDiff(7);
        Map<Time, Integer> numOfPassengers2 = new HashMap<>();
        for (Time time : startingTimes2) {
            numOfPassengers2.put(new Time(time.getTime() + timeToNextStop21.getTimeDiff() + timeToNextStop22.getTimeDiff()), 2);
        }
        StopInterface nextStop2 = new Stop(new StopName("stopName3"), lines2);
        lineSegment22 = new LineSegment(timeToNextStop22, numOfPassengers2, capacity + 2, lineName2, nextStop2, 1);
        lineSegments2.add(lineSegment22);

        //3rd lineSegment
        TimeDiff timeToNextStop23 = new TimeDiff(4);
        Map<Time, Integer> numOfPassengers3 = new HashMap<>();
        for (Time time : startingTimes2) {
            numOfPassengers3.put(new Time(time.getTime() + timeToNextStop21.getTimeDiff() + timeToNextStop22.getTimeDiff() + timeToNextStop23.getTimeDiff()), 0);
        }
        StopInterface nextStop3 = new Stop(new StopName("stopName4"), lines2);
        lineSegment23 = new LineSegment(timeToNextStop23, numOfPassengers3, capacity + 1, lineName2, nextStop3, 2);
        lineSegments2.add(lineSegment23);

        line2 = new Line(lineName2, firstStop2, lineSegments2, startingTimes2);
    }

    /*
     LineSegment tests
     */

    @Test
    public void getCapacityTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        assertEquals(2, lineSegment1.getCapacity());
        assertEquals(1, lineSegment21.getCapacity());
        assertEquals(3, lineSegment22.getCapacity());
        assertEquals(2, lineSegment23.getCapacity());
    }

    @Test
    public void getLineNameTestForLineSegment() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        assertEquals(updateReachable, lineUpdateReachable.getLineName());
        assertEquals(lineName1, lineSegment1.getLineName());
        assertEquals(lineName2, lineSegment21.getLineName());
        assertEquals(lineName2, lineSegment22.getLineName());
        assertEquals(lineName2, lineSegment23.getLineName());
    }

    @Test
    public void getTimeDiffTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        TimeDiff expectedTimeDiff1 = new TimeDiff(11);
        TimeDiff expectedTimeDiff2 = new TimeDiff(5);
        TimeDiff expectedTimeDiff3 = new TimeDiff(7);
        TimeDiff expectedTimeDiff4 = new TimeDiff(4);
        assertEquals(expectedTimeDiff1, lineSegment1.getTimeToNextStop());
        assertEquals(expectedTimeDiff2, lineSegment21.getTimeToNextStop());
        assertEquals(expectedTimeDiff3, lineSegment22.getTimeToNextStop());
        assertEquals(expectedTimeDiff4, lineSegment23.getTimeToNextStop());
    }

    @Test
    public void getNextStopTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        assertEquals(stop1, lineSegmentUpdateReachable1.getNextStop());
        assertEquals(stop2, lineSegmentUpdateReachable2.getNextStop());
    }

    @Test(expected = NegativeCapacityException.class)
    public void negativeCapacityExceptionTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        int capacity = -1;
        LineName lineName = new LineName("testLineName");
        StopInterface nextStop = new Stop(new StopName("testStopName"), new ArrayList<>());
        TimeDiff timeToNextStop = new TimeDiff(0);
        Map<Time, Integer> numOfPassengers = new HashMap<>();
        LineSegmentInterface testLineSegmentWithNegativeCapacity = new LineSegment(timeToNextStop,
                numOfPassengers, capacity, lineName, nextStop, 0);
    }

    @Test
    public void nextStopTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        Pair<Time, StopName> nextStopTest1 = lineSegment1.nextStop(new Time(5));
        assertEquals(16, nextStopTest1.getFirst().getTime());
        assertEquals("nextStop", nextStopTest1.getSecond().getName());

        Pair<Time, StopName> nextStopTest2 = lineSegment21.nextStop(new Time(4));
        assertEquals(9, nextStopTest2.getFirst().getTime());
        assertEquals("stopName2", nextStopTest2.getSecond().getName());

        Pair<Time, StopName> nextStopTest3 = lineSegment22.nextStop(new Time(9));
        assertEquals(16, nextStopTest3.getFirst().getTime());
        assertEquals("stopName3", nextStopTest3.getSecond().getName());

        Pair<Time, StopName> nextStopTest4 = lineSegment23.nextStop(new Time(16));
        assertEquals(20, nextStopTest4.getFirst().getTime());
        assertEquals("stopName4", nextStopTest4.getSecond().getName());
    }

    /*
    @Test(expected = NoSuchElementException.class)
    public void nextStopExceptionTest() throws NegativeCapacityException {
        //next stop for time, which isnt in startingTimes list
        setUp();
        Pair<Time, StopName> nextStopExceptionTest1 = lineSegment1.nextStop(new Time(1));
        Pair<Time, StopName> nextStopExceptionTest2 = lineSegment22.nextStop(new Time(24));
    }
     */


    @Test(expected = NullPointerException.class)
    public void nullPointerLineNameException() throws NegativeCapacityException, NegativeSegmentIndexException {
        TimeDiff timeDiff = new TimeDiff(1);
        Map<Time, Integer> mapTest = new HashMap<>();
        StopInterface nextStop = new Stop(new StopName("nextStop"), new ArrayList<>());
        LineSegmentInterface nullTest = new LineSegment(timeDiff, mapTest, 10, null, nextStop, 0);
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerNumOfPassengersException() throws NegativeCapacityException, NegativeSegmentIndexException {
        TimeDiff timeDiff = new TimeDiff(1);
        LineName testLineName = new LineName("testLineName");
        StopInterface nextStop = new Stop(new StopName("nextStop"), new ArrayList<>());
        LineSegmentInterface nullTest = new LineSegment(timeDiff, null, 10, testLineName, nextStop, 0);
    }

    @Test(expected = NegativeSegmentIndexException.class)
    public void negativeSegmentIndexExceptionTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        TimeDiff testDiff = new TimeDiff(1);
        Map<Time, Integer> mapTest = new HashMap<>();
        StopInterface stopTest = new Stop(new StopName("stopTest"), new ArrayList<>());

        LineSegmentInterface expectedExceptionTest = new LineSegment(testDiff, mapTest, 10, lineName1, stopTest, -1);
    }

    @Test(expected = FullBusException.class)
    public void nextStopAndUpdateReachableTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        Triplet<Time, StopName, Boolean> nextStopUpdateReachable1 = lineSegment1.nextStopAndUpdateReachable(new Time(5));
        Triplet<Time, StopName, Boolean> nextStopUpdateReachable2 = lineSegment21.nextStopAndUpdateReachable(new Time(4));
        Triplet<Time, StopName, Boolean> nextStopUpdateReachable3 = lineSegment22.nextStopAndUpdateReachable(new Time(9));
        Triplet<Time, StopName, Boolean> nextStopUpdateReachable4 = lineSegment23.nextStopAndUpdateReachable(new Time(16));

        assertEquals(0, lineSegment1.getPassengersAt(new Time(16)));
        assertEquals(16, nextStopUpdateReachable1.getFirst().getTime());
        assertEquals("nextStop", nextStopUpdateReachable1.getSecond().getName());
        //should return true, as 0 < capacity(2)
        assertTrue(nextStopUpdateReachable1.getThird());

        assertEquals(1, lineSegment21.getPassengersAt(new Time(9)));
        assertEquals(9, nextStopUpdateReachable2.getFirst().getTime());
        assertEquals("stopName2", nextStopUpdateReachable2.getSecond().getName());
        //already has one passenger on each startTime, and capacity is 1, so there shouldnt be free seats
        assertFalse(nextStopUpdateReachable2.getThird());

        assertEquals(2, lineSegment22.getPassengersAt(new Time(16)));
        assertEquals(16, nextStopUpdateReachable3.getFirst().getTime());
        assertEquals("stopName3", nextStopUpdateReachable3.getSecond().getName());
        //should return true, as 2 < capacity(3)
        assertTrue(nextStopUpdateReachable3.getThird());

        assertEquals(0, lineSegment23.getPassengersAt(new Time(20)));
        assertEquals(20, nextStopUpdateReachable4.getFirst().getTime());
        assertEquals("stopName4", nextStopUpdateReachable4.getSecond().getName());
        //should return true, as 0 < capacity(1)
        assertTrue(nextStopUpdateReachable4.getThird());

        //has capacity of 2, and at start has 0 passengers
        Time lineSegment23time = new Time(20);
        lineSegment23.incrementCapacity(lineSegment23time);
        assertEquals(1, lineSegment23.getPassengersAt(lineSegment23time));
        Triplet<Time, StopName, Boolean> freeSeatsAfterIncrementTest = lineSegment23.nextStopAndUpdateReachable(lineSegment23time);
        assertTrue(freeSeatsAfterIncrementTest.getThird());
        lineSegment23.incrementCapacity(lineSegment23time);
        assertEquals(2, lineSegment23.getPassengersAt(lineSegment23time));
        //should throw fullBusException if we want to add another passenger
        lineSegment23.incrementCapacity(lineSegment23time);

    }

    /*
    @Test(expected = NoSuchElementException.class)
    public void nextStopUpdateReachableExceptionTest() throws NegativeCapacityException {
        setUp();
        Triplet<Time, StopName, Boolean> nextStopUpdateReachableExceptionTest1 = lineSegment1.nextStopAndUpdateReachable(new Time(1));
        Triplet<Time, StopName, Boolean> nextStopUpdateReachableExceptionTest2 = lineSegment21.nextStopAndUpdateReachable(new Time(2));
    }
     */

    @Test
    public void incrementCapacityTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        //firstly, lineSegment1 should have 0 passengers on each startTime
        assertEquals(0, lineSegment1.getPassengersAt(new Time(16)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(17)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(18)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(19)));
        lineSegment1.incrementCapacity(new Time(16));

        assertEquals(1, lineSegment1.getPassengersAt(new Time(16)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(17)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(18)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(19)));

        lineSegment1.incrementCapacity(new Time(17));
        lineSegment1.incrementCapacity(new Time(18));
        lineSegment1.incrementCapacity(new Time(19));

        assertEquals(1, lineSegment1.getPassengersAt(new Time(16)));
        assertEquals(1, lineSegment1.getPassengersAt(new Time(17)));
        assertEquals(1, lineSegment1.getPassengersAt(new Time(18)));
        assertEquals(1, lineSegment1.getPassengersAt(new Time(19)));

        assertEquals(0, lineSegment23.getPassengersAt(new Time(20)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(22)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(24)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(26)));

        lineSegment23.incrementCapacity(new Time(20));
        lineSegment23.incrementCapacity(new Time(22));
        lineSegment23.incrementCapacity(new Time(24));

        assertEquals(1, lineSegment23.getPassengersAt(new Time(20)));
        assertEquals(1, lineSegment23.getPassengersAt(new Time(22)));
        assertEquals(1, lineSegment23.getPassengersAt(new Time(24)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(26)));
    }

    @Test(expected = FullBusException.class)
    public void incrementFullBusExceptionTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        //lineSegment1 has capacity of 2 and numOfPassengers is init. to 0 -> after 3 increments it should throw FullBusException
        Time startTimeExceptionTest = new Time(16);
        assertEquals(0, lineSegment1.getPassengersAt(startTimeExceptionTest));
        lineSegment1.incrementCapacity(startTimeExceptionTest);
        assertEquals(1, lineSegment1.getPassengersAt(startTimeExceptionTest));
        lineSegment1.incrementCapacity(startTimeExceptionTest);
        assertEquals(2, lineSegment1.getPassengersAt(startTimeExceptionTest));
        lineSegment1.incrementCapacity(startTimeExceptionTest); //throws exception
        System.err.println("Exception wasnt thrown"); //output if program continues
    }


    @Test
    public void getPassengersAtTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        //test all inits
        assertEquals(0, lineSegment1.getPassengersAt(new Time(16)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(17)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(18)));
        assertEquals(0, lineSegment1.getPassengersAt(new Time(19)));

        assertEquals(1, lineSegment21.getPassengersAt(new Time(9)));
        assertEquals(1, lineSegment21.getPassengersAt(new Time(11)));
        assertEquals(1, lineSegment21.getPassengersAt(new Time(13)));
        assertEquals(1, lineSegment21.getPassengersAt(new Time(15)));

        assertEquals(2, lineSegment22.getPassengersAt(new Time(16)));
        assertEquals(2, lineSegment22.getPassengersAt(new Time(18)));
        assertEquals(2, lineSegment22.getPassengersAt(new Time(20)));
        assertEquals(2, lineSegment22.getPassengersAt(new Time(22)));

        assertEquals(0, lineSegment23.getPassengersAt(new Time(20)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(22)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(24)));
        assertEquals(0, lineSegment23.getPassengersAt(new Time(26)));
    }

    @Test(expected = NoSuchElementException.class)
    public void getPassengersAtExceptionTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        int exceptionTest1 = lineSegment1.getPassengersAt(new Time(24));
        int exceptionTest2 = lineSegment21.getPassengersAt(new Time(5));
        int exceptionTest3 = lineSegment22.getPassengersAt(new Time(11));
        int exceptionTest4 = lineSegment23.getPassengersAt(new Time(15));
    }

    @Test
    public void getBusesTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        Map<Time, Integer> expectedBuses1 = new HashMap<>();
        expectedBuses1.put(new Time(16), 0);
        expectedBuses1.put(new Time(17), 0);
        expectedBuses1.put(new Time(18), 0);
        expectedBuses1.put(new Time(19), 0);
        assertEquals(expectedBuses1, lineSegment1.getBuses());

        lineSegment23.incrementCapacity(new Time(20)); //1st
        lineSegment23.incrementCapacity(new Time(22)); //2nd
        lineSegment23.incrementCapacity(new Time(26)); //4th

        //linesegment 23 has starting times startTime + 2nd linesegment
        Map<Time, Integer> expectedBuses2 = new HashMap<>();
        expectedBuses2.put(new Time(20), 1);
        expectedBuses2.put(new Time(22), 1);
        expectedBuses2.put(new Time(24), 0);
        expectedBuses2.put(new Time(26), 1);
        assertEquals(expectedBuses2, lineSegment23.getBuses());
    }

    /*
     Line tests
     */

    @Test
    public void getStartingTimes() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        List<Time> startingTimesLine1 = line1.getStartingTimes();
        assertEquals(4, startingTimesLine1.size());
        //expected times
        assertEquals(new Time(5), startingTimesLine1.get(0));
        assertEquals(new Time(6), startingTimesLine1.get(1));
        assertEquals(new Time(7), startingTimesLine1.get(2));
        assertEquals(new Time(8), startingTimesLine1.get(3));

        List<Time> startingTimesLine2 = line2.getStartingTimes();
        assertEquals(4, startingTimesLine2.size());
        //expected times
        assertEquals(new Time(4), startingTimesLine2.get(0));
        assertEquals(new Time(6), startingTimesLine2.get(1));
        assertEquals(new Time(8), startingTimesLine2.get(2));
        assertEquals(new Time(10), startingTimesLine2.get(3));
    }

    @Test
    public void getLineSegmentsTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        List<LineSegmentInterface> expectedLineSegments1 = new ArrayList<>();
        List<LineSegmentInterface> testedLineSegments = line1.getLineSegments();
        //should have only one linesegment
        assertEquals(1, testedLineSegments.size());
        LineSegmentInterface lineSegment = testedLineSegments.get(0);
        Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface> lineSegmentQuintuplet = lineSegment.convertToQuintuplet();

        //expected values
        TimeDiff timeToNextStop1 = new TimeDiff(11);
        assertEquals(timeToNextStop1.getTimeDiff(), lineSegmentQuintuplet.getFirst().getTimeDiff());

        Map<Time, Integer> numOfPassengers = new HashMap<>();
        numOfPassengers.put(new Time(16), 0);
        numOfPassengers.put(new Time(17), 0);
        numOfPassengers.put(new Time(18), 0);
        numOfPassengers.put(new Time(19), 0);
        assertEquals(numOfPassengers, lineSegmentQuintuplet.getSecond());

        Integer capacity = 2;
        assertEquals(capacity, lineSegmentQuintuplet.getThird());

        LineName lineName = new LineName("Line1");
        assertEquals(lineName.getName(), lineSegmentQuintuplet.getForth().getName());

        List<LineName> lines = new ArrayList<>();
        lines.add(new LineName("Line1"));
        StopInterface nextStop = new Stop(new StopName("nextStop"), lines);
        assertEquals(nextStop.getStopName(), nextStop.getStopName());
        assertEquals(lines, nextStop.getLines());
    }

    @Test
    public void getFirstStopTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        StopName firstStop1Test = new StopName("firstStop1");
        StopName firstStop2Test = new StopName("firstStop2");
        assertEquals(firstStop1Test.getName(), line1.getFirstStop().getName());
        assertEquals(firstStop2Test.getName(), line2.getFirstStop().getName());
    }

    @Test
    public void getLineNameTestForLine() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        LineName lineName1Test = new LineName("Line1");
        LineName lineName2Test = new LineName("Line2");
        assertEquals(lineName1Test, line1.getLineName());
        assertEquals(lineName2Test, line2.getLineName());
    }

    @Test
    public void updateReachableTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        //init test
        assertEquals(Integer.MAX_VALUE, stop1.getReachableAt().getFirst().getTime());
        assertNull(stop1.getReachableAt().getSecond());
        assertEquals(Integer.MAX_VALUE, stop2.getReachableAt().getFirst().getTime());
        assertNull(stop2.getReachableAt().getSecond());

        lineUpdateReachable.updateReachable(new Time(1), lineUpdateReachable.getFirstStop());

        assertEquals(3, stop1.getReachableAt().getFirst().getTime());
        assertEquals(lineUpdateReachable.getLineName(), stop1.getReachableAt().getSecond());
        assertEquals(6, stop2.getReachableAt().getFirst().getTime());
        assertEquals(lineUpdateReachable.getLineName(), stop2.getReachableAt().getSecond());

        setUp();
        //init test
        assertEquals(Integer.MAX_VALUE, stop1.getReachableAt().getFirst().getTime());
        assertNull(stop1.getReachableAt().getSecond());
        assertEquals(Integer.MAX_VALUE, stop2.getReachableAt().getFirst().getTime());
        assertNull(stop2.getReachableAt().getSecond());

        lineUpdateReachable.updateReachable(new Time(1), new StopName("stop1"));

        assertEquals(6, stop2.getReachableAt().getFirst().getTime());
        assertEquals(lineUpdateReachable.getLineName(), stop2.getReachableAt().getSecond());

        setUp();
        //init test
        assertEquals(Integer.MAX_VALUE, stop1.getReachableAt().getFirst().getTime());
        assertNull(stop1.getReachableAt().getSecond());
        assertEquals(Integer.MAX_VALUE, stop2.getReachableAt().getFirst().getTime());
        assertNull(stop2.getReachableAt().getSecond());

        lineUpdateReachable.updateReachable(new Time(1), new StopName("stop2"));

        assertEquals(Integer.MAX_VALUE, stop1.getReachableAt().getFirst().getTime());
        assertNull(stop1.getReachableAt().getSecond());
    }

    @Test(expected = FullBusException.class)
    public void updateCapacityAndGetPreviousStopTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        StopName previousStop = line2.updateCapacityAndGetPreviousStop(new StopName("stopName4"), new Time(26));
        assertEquals(1, line2.getLineSegments().get(2).getPassengersAt(new Time(26)));
        assertEquals("stopName3", previousStop.getName());

        StopName previousStop2 = line2.updateCapacityAndGetPreviousStop(previousStop, new Time(22));
        assertEquals(3, line2.getLineSegments().get(1).getPassengersAt(new Time(22)));
        assertEquals("stopName2", previousStop2.getName());

        //should throw exception line2.getLineSegments().get(0) has already 1 passenger on each time and has capacity of 1, so its full
        StopName previousStop3 = line2.updateCapacityAndGetPreviousStop(previousStop2, new Time(15));
        // - what would be called
        //assertEquals(1, line2.getLineSegments().get(0).getPassengersAt());
        //assertEquals("firstStop1", previousStop3.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateCapacityAndGetPreviousStopTestExceptionTest() throws NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        setUp();
        //should throw exception because there is no previous stop for first stop
        StopName previousStopException = line1.updateCapacityAndGetPreviousStop(line1.getFirstStop(), new Time(20));
    }
}
