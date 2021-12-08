package tests;

import database.*;
import datatypes.*;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.Assert.*;

public class LinesFactoriesTest {

    private LineName lineName1;
    private LineName lineName2;
    private LineName lineName3;
    private MemoryDatabase mdb;
    private MemoryLinesFactory mlf;
    private FileLinesFactory flf;

    public void setUp() throws NegativeCapacityException, NegativeSegmentIndexException {
        lineName1 = new LineName("Line1");
        lineName2 = new LineName("Line2");
        lineName3 = new LineName("Line3");
        mdb = new MemoryDatabase();
        mlf = new MemoryLinesFactory(mdb);
        StopsFactoryInterface sf = new MemoryStopsFactory(mdb);
        flf = new FileLinesFactory(sf);
    }

    @Test
    public void createLineTest() throws NegativeCapacityException, NegativeSegmentIndexException, FileNotFoundException {
        setUp();
        //should not create lines which dont exist
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line10")));
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line365")));
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line0")));

        //should create these lines from files, because they are in the database
        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line1")));
        Optional<LineInterface> line1 = mlf.createLine(new LineName("Line1"));
        LineInterface line1FromMemoryDatabase = mdb.readLines().get(0);
        assertEquals(lineName1, line1.get().getLineName());
        assertEquals("Trnava", line1.get().getFirstStop().getName());
        int counter = 0;
        for (LineSegmentInterface lineSegment : line1FromMemoryDatabase.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line1.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line1.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line1.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line1.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line1FromMemoryDatabase.getStartingTimes(), line1.get().getStartingTimes());

        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line2")));
        Optional<LineInterface> line2 = mlf.createLine(new LineName("Line2"));
        LineInterface line2FromMemoryDatabase = mdb.readLines().get(1);
        assertEquals(lineName2, line2.get().getLineName());
        assertEquals("Bratislava", line2.get().getFirstStop().getName());
        counter = 0;
        for (LineSegmentInterface lineSegment : line2FromMemoryDatabase.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line2.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line2.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line2.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line2.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line2FromMemoryDatabase.getStartingTimes(), line2.get().getStartingTimes());

        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line3")));
        Optional<LineInterface> line3 = mlf.createLine(new LineName("Line3"));
        LineInterface line3FromMemoryDatabase = mdb.readLines().get(2);
        assertEquals(lineName3, line3.get().getLineName());
        assertEquals("Brezno", line3.get().getFirstStop().getName());
        counter = 0;
        for (LineSegmentInterface lineSegment : line3FromMemoryDatabase.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line3.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line3.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line3.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line3.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line3FromMemoryDatabase.getStartingTimes(), line3.get().getStartingTimes());

        //should not create another one, because there are only 3 in database
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line4")));

        setUp();

        //should not create lines which dont exist
        assertEquals(Optional.empty(), flf.createLine(new LineName("Line10")));
        assertEquals(Optional.empty(), flf.createLine(new LineName("Line365")));
        assertEquals(Optional.empty(), flf.createLine(new LineName("Line0")));

        //should create these lines from files
        assertNotEquals(Optional.empty(), flf.createLine(new LineName("Line1")));
        Optional<LineInterface> line1F = flf.createLine(new LineName("Line1"));
        LineInterface line1FromDatabaseF = mdb.readLines().get(0);
        assertEquals(lineName1, line1F.get().getLineName());
        assertEquals("Trnava", line1F.get().getFirstStop().getName());
        counter = 0;
        for (LineSegmentInterface lineSegment : line1FromDatabaseF.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line1F.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line1F.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line1F.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line1F.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line1FromMemoryDatabase.getStartingTimes(), line1F.get().getStartingTimes());

        assertNotEquals(Optional.empty(), flf.createLine(new LineName("Line2")));
        Optional<LineInterface> line2F = flf.createLine(new LineName("Line2"));
        LineInterface line2FromDatabaseF = mdb.readLines().get(1);
        assertEquals(lineName2, line2F.get().getLineName());
        assertEquals("Bratislava", line2F.get().getFirstStop().getName());
        counter = 0;
        for (LineSegmentInterface lineSegment : line2FromDatabaseF.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line2F.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line2F.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line2F.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line2F.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line2FromMemoryDatabase.getStartingTimes(), line2F.get().getStartingTimes());

        assertNotEquals(Optional.empty(), flf.createLine(new LineName("Line3")));
        Optional<LineInterface> line3F = flf.createLine(new LineName("Line3"));
        LineInterface line3FromDatabaseF = mdb.readLines().get(2);
        assertEquals(lineName3, line3F.get().getLineName());
        assertEquals("Brezno", line3F.get().getFirstStop().getName());
        counter = 0;
        for (LineSegmentInterface lineSegment : line3FromDatabaseF.getLineSegments()) {
            assertEquals(lineSegment.getLineName().getName(), line3F.get().getLineSegments().get(counter).getLineName().getName());
            assertEquals(lineSegment.getSegmentIndex(), line3F.get().getLineSegments().get(counter).getSegmentIndex());
            assertEquals(lineSegment.getTimeToNextStop().getTimeDiff(), line3F.get().getLineSegments().get(counter).getTimeToNextStop().getTimeDiff());
            assertEquals(lineSegment.getNextStop().getStopName(), line3F.get().getLineSegments().get(counter).getNextStop().getStopName());
            counter++;
        }
        assertEquals(line3FromMemoryDatabase.getStartingTimes(), line3F.get().getStartingTimes());

    }

    @Test(expected = NoSuchElementException.class)
    public void createSegmentTest() throws NegativeCapacityException, NegativeSegmentIndexException, FileNotFoundException {
        setUp();
        //should not create segments which dont exists (either wrong lineName or segment out of bounds)
        //wrong lineName
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line10"), 0));
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line365"), 0));
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line0"), 0));

        //out of bounds segment
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line1"), 100));
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line2"), 5));
        assertEquals(Optional.empty(), mlf.createLineSegment(new LineName("Line3"), 1));

        //memoryLinesFactory
        //wont test whether all constructor parameters are correct, thorough test would be too long
        Optional<LineSegmentInterface> trnavaBratislava = mlf.createLineSegment(lineName1, 0);
        assertTrue(trnavaBratislava.isPresent());
        assertEquals(new TimeDiff(11), trnavaBratislava.get().getTimeToNextStop());
        assertEquals(lineName1, trnavaBratislava.get().getLineName());
        assertEquals(0, trnavaBratislava.get().getSegmentIndex());
        List<LineName> bratislavaLines = new ArrayList<>();
        bratislavaLines.add(lineName1);
        bratislavaLines.add(lineName2);
        StopInterface bratislavaStop = new Stop(new StopName("Bratislava"), bratislavaLines);
        assertEquals(bratislavaStop.getStopName(), trnavaBratislava.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> bratislavaKosice = mlf.createLineSegment(lineName1, 1);
        assertTrue(bratislavaKosice.isPresent());
        assertEquals(new TimeDiff(13), bratislavaKosice.get().getTimeToNextStop());
        assertEquals(lineName1, bratislavaKosice.get().getLineName());
        assertEquals(1, bratislavaKosice.get().getSegmentIndex());
        List<LineName> kosiceLineNames = new ArrayList<>();
        kosiceLineNames.add(lineName1);
        StopInterface kosiceStop = new Stop(new StopName("Kosice"), kosiceLineNames);
        assertEquals(kosiceStop.getStopName(), bratislavaKosice.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> bratislavaMartin = mlf.createLineSegment(lineName2, 0);
        assertTrue(bratislavaMartin.isPresent());
        assertEquals(new TimeDiff(5), bratislavaMartin.get().getTimeToNextStop());
        assertEquals(lineName2, bratislavaMartin.get().getLineName());
        assertEquals(0, bratislavaMartin.get().getSegmentIndex());
        List<LineName> martinLineNames = new ArrayList<>();
        martinLineNames.add(lineName2);
        StopInterface martinStop = new Stop(new StopName("Martin"), martinLineNames);
        assertEquals(martinStop.getStopName(), bratislavaMartin.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> martinPresov = mlf.createLineSegment(lineName2, 1);
        assertTrue(martinPresov.isPresent());
        assertEquals(new TimeDiff(3), martinPresov.get().getTimeToNextStop());
        assertEquals(lineName2, martinPresov.get().getLineName());
        assertEquals(1, martinPresov.get().getSegmentIndex());
        List<LineName> presovLineNames = new ArrayList<>();
        StopInterface presovStop = new Stop(new StopName("Presov"), presovLineNames);
        assertEquals(presovStop.getStopName(), martinPresov.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> breznoPartizanske = mlf.createLineSegment(lineName3, 0);
        assertTrue(breznoPartizanske.isPresent());
        assertEquals(new TimeDiff(2), breznoPartizanske.get().getTimeToNextStop());
        assertEquals(lineName3, breznoPartizanske.get().getLineName());
        assertEquals(0, breznoPartizanske.get().getSegmentIndex());
        List<LineName> partizanskeLines = new ArrayList<>();
        StopInterface partizanskeStop = new Stop(new StopName("Partizanske"), partizanskeLines);
        assertEquals(partizanskeStop.getStopName(), breznoPartizanske.get().getNextStop().getStopName());

        //same, but for fileLinesFactory
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line10"), 0));
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line365"), 0));
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line0"), 0));

        //out of bounds segment
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line1"), 100));
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line2"), 5));
        assertEquals(Optional.empty(), flf.createLineSegment(new LineName("Line3"), 1));

        //wont test whether all constructor parameters are correct, thorough test would be too long
        Optional<LineSegmentInterface> trnavaBratislavaF = flf.createLineSegment(lineName1, 0);
        assertTrue(trnavaBratislavaF.isPresent());
        assertEquals(new TimeDiff(11), trnavaBratislava.get().getTimeToNextStop());
        assertEquals(lineName1, trnavaBratislavaF.get().getLineName());
        assertEquals(0, trnavaBratislavaF.get().getSegmentIndex());
        List<LineName> bratislavaLinesF = new ArrayList<>();
        bratislavaLinesF.add(lineName1);
        bratislavaLinesF.add(lineName2);
        StopInterface bratislavaStopF = new Stop(new StopName("Bratislava"), bratislavaLinesF);
        assertEquals(bratislavaStopF.getStopName(), trnavaBratislavaF.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> bratislavaKosiceF = flf.createLineSegment(lineName1, 1);
        assertTrue(bratislavaKosiceF.isPresent());
        assertEquals(new TimeDiff(13), bratislavaKosiceF.get().getTimeToNextStop());
        assertEquals(lineName1, bratislavaKosiceF.get().getLineName());
        assertEquals(1, bratislavaKosiceF.get().getSegmentIndex());
        List<LineName> kosiceLineNamesF = new ArrayList<>();
        kosiceLineNamesF.add(lineName1);
        StopInterface kosiceStopF = new Stop(new StopName("Kosice"), kosiceLineNamesF);
        assertEquals(kosiceStopF.getStopName(), bratislavaKosiceF.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> bratislavaMartinF = flf.createLineSegment(lineName2, 0);
        assertTrue(bratislavaMartinF.isPresent());
        assertEquals(new TimeDiff(5), bratislavaMartinF.get().getTimeToNextStop());
        assertEquals(lineName2, bratislavaMartinF.get().getLineName());
        assertEquals(0, bratislavaMartinF.get().getSegmentIndex());
        List<LineName> martinLineNamesF = new ArrayList<>();
        martinLineNamesF.add(lineName2);
        StopInterface martinStopF = new Stop(new StopName("Martin"), martinLineNamesF);
        assertEquals(martinStopF.getStopName(), bratislavaMartinF.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> martinPresovF = flf.createLineSegment(lineName2, 1);
        assertTrue(martinPresovF.isPresent());
        assertEquals(new TimeDiff(3), martinPresovF.get().getTimeToNextStop());
        assertEquals(lineName2, martinPresovF.get().getLineName());
        assertEquals(1, martinPresov.get().getSegmentIndex());
        List<LineName> presovLineNamesF = new ArrayList<>();
        StopInterface presovStopF = new Stop(new StopName("Presov"), presovLineNamesF);
        assertEquals(presovStopF.getStopName(), martinPresovF.get().getNextStop().getStopName());

        Optional<LineSegmentInterface> breznoPartizanskeF = flf.createLineSegment(lineName3, 0);
        assertTrue(breznoPartizanskeF.isPresent());
        assertEquals(new TimeDiff(2), breznoPartizanskeF.get().getTimeToNextStop());
        assertEquals(lineName3, breznoPartizanskeF.get().getLineName());
        assertEquals(0, breznoPartizanskeF.get().getSegmentIndex());
        List<LineName> partizanskeLinesF = new ArrayList<>();
        StopInterface partizanskeStopF = new Stop(new StopName("Partizanske"), partizanskeLinesF);
        assertEquals(partizanskeStopF.getStopName(), breznoPartizanskeF.get().getNextStop().getStopName());
    }

    @Test
    public void updateDatabaseTest() throws NegativeCapacityException, NegativeSegmentIndexException, FileNotFoundException {
        setUp();
        //going to test on Martin - Presov segment from memory database
        //firstly will check, what are current values of linesegment variables

        Optional<LineSegmentInterface> martinPresov = mlf.createLineSegment(lineName2, 1);
        assertTrue(martinPresov.isPresent());
        assertEquals(new TimeDiff(3), martinPresov.get().getTimeToNextStop());
        assertEquals(lineName2, martinPresov.get().getLineName());
        assertEquals(1, martinPresov.get().getSegmentIndex());
        List<LineName> presovLineNames = new ArrayList<>();
        StopInterface presovStop = new Stop(new StopName("Presov"), presovLineNames);
        assertEquals(presovStop.getStopName(), martinPresov.get().getNextStop().getStopName());
        List<Time> timesForLineSegment = new ArrayList<>();
        timesForLineSegment.add(new Time(2));
        timesForLineSegment.add(new Time(4));
        timesForLineSegment.add(new Time(6));
        timesForLineSegment.add(new Time(8));
        timesForLineSegment.add(new Time(10));
        Map<Time, Integer> martinPresovMap = new HashMap<>();
        for (Time time : timesForLineSegment) {
            Time newTime = new Time(time.getTime() + 5 + martinPresov.get().getTimeToNextStop().getTimeDiff());
            martinPresovMap.put(newTime, 0);
        }
        assertEquals(martinPresovMap, martinPresov.get().getBuses());

        List<LineSegmentInterface> updatedSegments = new ArrayList<>();
        //will create linesegment with updated passengers
        TimeDiff timeDiffToPresov = new TimeDiff(3);
        Map<Time, Integer> numOfPassengersMartinPresov = new HashMap<>();
        for (Time time : timesForLineSegment) {
            Time newTime = new Time(time.getTime() + 5 + timeDiffToPresov.getTimeDiff());
            //all times will have one passenger
            numOfPassengersMartinPresov.put(newTime, 1);
        }
        int capacity = 20;
        LineSegmentInterface martinPresovUpdatedSegment = new LineSegment(timeDiffToPresov, numOfPassengersMartinPresov, capacity, lineName2, presovStop, 1);
        updatedSegments.add(martinPresovUpdatedSegment);

        /* MEMORYFACTORY TEST */
        mlf.updateDatabase(updatedSegments);

        //updated segment
        Optional<LineSegmentInterface> martinPresovUpdated = mlf.createLineSegment(lineName2, 1);
        assertTrue(martinPresovUpdated.isPresent());
        assertEquals(new TimeDiff(3), martinPresovUpdated.get().getTimeToNextStop());
        assertEquals(lineName2, martinPresovUpdated.get().getLineName());
        assertEquals(1, martinPresovUpdated.get().getSegmentIndex());
        List<LineName> presovLineNamesUpdated = new ArrayList<>();
        StopInterface presovStopUpdated = new Stop(new StopName("Presov"), presovLineNamesUpdated);
        assertEquals(presovStopUpdated.getStopName(), martinPresovUpdated.get().getNextStop().getStopName());
        assertEquals(numOfPassengersMartinPresov, martinPresovUpdated.get().getBuses());

        //other segment should stay the same
        Optional<LineSegmentInterface> bratislavaMartin = mlf.createLineSegment(lineName2, 0);
        assertTrue(bratislavaMartin.isPresent());
        assertEquals(new TimeDiff(5), bratislavaMartin.get().getTimeToNextStop());
        assertEquals(lineName2, bratislavaMartin.get().getLineName());
        assertEquals(0, bratislavaMartin.get().getSegmentIndex());
        List<LineName> martinLineNames = new ArrayList<>();
        martinLineNames.add(lineName2);
        StopInterface martinStop = new Stop(new StopName("Martin"), martinLineNames);
        assertEquals(martinStop.getStopName(), bratislavaMartin.get().getNextStop().getStopName());
        Map<Time, Integer> bratislavaMartinMap = new HashMap<>();
        for (Time time : timesForLineSegment) {
            Time newTime = new Time(time.getTime() + 5);
            bratislavaMartinMap.put(newTime, 0);
        }
        assertEquals(bratislavaMartinMap, bratislavaMartin.get().getBuses());

        setUp();

        /* FILEFACTORY TEST */
        flf.updateDatabase(updatedSegments);

        //other segment should stay the same
        Optional<LineSegmentInterface> bratislavaMartinF = flf.createLineSegment(lineName2, 0);
        assertTrue(bratislavaMartinF.isPresent());
        assertEquals(new TimeDiff(5), bratislavaMartinF.get().getTimeToNextStop());
        assertEquals(lineName2, bratislavaMartinF.get().getLineName());
        assertEquals(0, bratislavaMartinF.get().getSegmentIndex());
        List<LineName> martinLineNamesF = new ArrayList<>();
        martinLineNamesF.add(lineName2);
        StopInterface martinStopF = new Stop(new StopName("Martin"), martinLineNamesF);
        assertEquals(martinStopF.getStopName(), bratislavaMartinF.get().getNextStop().getStopName());
        assertEquals(bratislavaMartinMap, bratislavaMartinF.get().getBuses());

        Optional<LineSegmentInterface> martinPresovUpdatedF = flf.createLineSegment(lineName2, 1);
        assertTrue(martinPresovUpdatedF.isPresent());
        assertEquals(new TimeDiff(3), martinPresovUpdatedF.get().getTimeToNextStop());
        assertEquals(lineName2, martinPresovUpdatedF.get().getLineName());
        assertEquals(1, martinPresovUpdatedF.get().getSegmentIndex());
        List<LineName> presovLineNamesUpdatedF = new ArrayList<>();
        StopInterface presovStopUpdatedF = new Stop(new StopName("Presov"), presovLineNamesUpdatedF);
        assertEquals(presovStopUpdatedF.getStopName(), martinPresovUpdatedF.get().getNextStop().getStopName());
        assertEquals(numOfPassengersMartinPresov, martinPresovUpdatedF.get().getBuses());
    }

    @Test(expected = NegativeSegmentIndexException.class)
    public void negativeSegmentExceptionTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        mlf.createLineSegment(new LineName("Line1"), -1); //both throwing NegativeSegmentIndexException
        flf.createLineSegment(new LineName("Line1"), -1);
    }

}
