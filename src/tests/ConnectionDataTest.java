package tests;

import org.junit.Test;
import static org.junit.Assert.*;
import datatypes.*;

public class ConnectionDataTest {

    private ConnectionData connectionData;

    public void setUp() {
        connectionData = new ConnectionData();

    }

    @Test
    public void addToConnectionAndGetTest() {
        setUp();
        LineName lineName1 = new LineName("lineName1");
        LineName lineName2 = new LineName("lineName2");
        StopName stopName1 = new StopName("stopName1");
        StopName stopName2 = new StopName("stopName2");
        StopName stopName3 = new StopName("stopName3");
        Time time1 = new Time(1);
        Time time2 = new Time(2);
        Time time3 = new Time(3);
        connectionData.addToConnection(lineName1, stopName1, time1);
        connectionData.addToConnection(lineName1, stopName2, time2);
        connectionData.addToConnection(lineName2, stopName3, time3);

        //output in reversed order
        Triplet<LineName, StopName, Time> firstTriplet = connectionData.getConnection().get(0);
        Triplet<LineName, StopName, Time> secondTriplet = connectionData.getConnection().get(1);
        Triplet<LineName, StopName, Time> thirdTriplet = connectionData.getConnection().get(2);

        assertEquals(new Triplet<>(lineName2, stopName3, time3), firstTriplet);
        assertEquals(new Triplet<>(lineName1, stopName2, time2), secondTriplet);
        assertEquals(new Triplet<>(lineName1, stopName1, time1), thirdTriplet);
    }
}
