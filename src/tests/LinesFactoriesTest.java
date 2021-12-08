package tests;

import database.*;
import datatypes.*;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.LineSegmentInterface;
import main.Stop;
import main.StopInterface;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class LinesFactoriesTest {

    private LineName lineName1;
    private LineName lineName2;
    private LineName lineName3;

    private MemoryLinesFactory mlf;
    private FileLinesFactory flf;

    public void setUp() throws NegativeCapacityException, NegativeSegmentIndexException {
        lineName1 = new LineName("Line1");
        lineName2 = new LineName("Line2");
        lineName3 = new LineName("Line3");
        MemoryDatabase mdb = new MemoryDatabase();
        mlf = new MemoryLinesFactory(mdb);
        //flf = new FileLinesFactory();
    }

    @Test
    public void createLineTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        //should not create lines which dont exist
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line10")));
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line365")));
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line0")));

        //should create these lines from memory, because they are in the database
        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line1")));
        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line2")));
        assertNotEquals(Optional.empty(), mlf.createLine(new LineName("Line3")));

        //should not create another one, because there are only 3 in database
        assertEquals(Optional.empty(), mlf.createLine(new LineName("Line4")));

        setUp();
    }

    @Test
    public void createSegmentTest() throws NegativeCapacityException, NegativeSegmentIndexException {
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

    }

    @Test(expected = NegativeSegmentIndexException.class)
    public void negativeSegmentExceptionTest() throws NegativeCapacityException, NegativeSegmentIndexException {
        setUp();
        mlf.createLineSegment(new LineName("Line1"), -1);
    }
}
