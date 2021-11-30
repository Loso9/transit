package tests;

import datatypes.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class XNameTest {

    private LineName lineName1;
    private LineName lineName2;
    private StopName stopName1;
    private StopName stopName2;

    public void setUp() {
        lineName1 = new LineName("lineName1");
        lineName2 = new LineName("lineName2");
        stopName1 = new StopName("stopName1");
        stopName2 = new StopName("stopName2");
    }

    @Test
    public void getNameTest() {
        //expected values
        setUp();
        assertEquals("lineName1", lineName1.getName());
        assertEquals("lineName2", lineName2.getName());
        assertEquals("stopName1", stopName1.getName());
        assertEquals("stopName2", stopName2.getName());
    }

    @Test
    public void equalsTest() {
        setUp();
        LineName lineName1Test = new LineName("lineName1");
        LineName lineName2Test = new LineName("lineName2");
        assertEquals(lineName1, lineName1Test);
        assertEquals(lineName2, lineName2Test);

        StopName stopName1Test = new StopName("stopName1");
        StopName stopName2Test = new StopName("stopName2");
        assertEquals(stopName1, stopName1Test);
        assertEquals(stopName2, stopName2Test);
    }
}
