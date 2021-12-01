package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.*;

import datatypes.*;
import main.*;
import exceptions.*;
import org.junit.Test;

public class StopsTest {
    Stops stops;
    Stop stop1;
    Stop stop2;

    public void setUp() {
        StopName stopName1 = new StopName("stop1");
        StopName stopName2 = new StopName("stop2");

        //not empty
        ArrayList<LineName> lines1 = new ArrayList<>();
        LineName line1 = new LineName("line1");
        LineName line2 = new LineName("line2");
        LineName line3 = new LineName("line3");

        //empty
        ArrayList<LineName> lines2 = new ArrayList<>();

        stop1 = new Stop(stopName1, lines1);
        stop2 = new Stop(stopName2, lines2);
    }

    @Test
    public void getLinesTest() throws AlreadyLoadedStopException, FileNotFoundException {
        setUp();
        //expected 3 lines from stop1

        ArrayList<LineName> testLines = new ArrayList<>(stops.getLines(stop1.getStopName()));
        LineName testLine1 = testLines.get(0);
        LineName testLine2 = testLines.get(1);
        LineName testLine3 = testLines.get(2);
        assertEquals(new LineName("line1"), testLine1);
        assertEquals(new LineName("line2"), testLine2);
        assertEquals(new LineName("line3"), testLine3);

        //stop2 should have empty list of lines, so first element of getLines list should be null
        assertNull(stops.getLines(stop2.getStopName()).get(0));
        assertNull(stops.getLines(stop2.getStopName()).get(1));
    }



}

