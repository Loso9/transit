package tests;

import datatypes.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeAndTimeDiffTest {

    private Time time1;
    private Time time2;
    private TimeDiff timeDiff1;
    private TimeDiff timeDiff2;

    public void setUp() {
        time1 = new Time(Integer.MAX_VALUE);
        time2 = new Time(24);

        timeDiff1 = new TimeDiff(time1.getTime());
        timeDiff2 = new TimeDiff(time1, time2);
    }

    @Test
    public void getTimeTest() {
        //expected values
        setUp();
        assertEquals(Integer.MAX_VALUE, time1.getTime());
        assertEquals(24, time2.getTime());

        assertEquals(Integer.MAX_VALUE, timeDiff1.getTimeDiff());
        //Integer.MAX_VALUE = 2 147 483 647
        assertEquals(2147483623, timeDiff2.getTimeDiff());
    }

    @Test
    public void equalsTest() {
        setUp();
        Time time1Test = new Time(Integer.MAX_VALUE);
        Time time2Test = new Time(24);
        assertEquals(time1, time1Test);
        assertEquals(time2, time2Test);

        TimeDiff timeDiff1Test = new TimeDiff(Integer.MAX_VALUE);
        TimeDiff timeDiff2Test = new TimeDiff(2147483623);
        assertEquals(timeDiff1, timeDiff1Test);
        assertEquals(timeDiff2, timeDiff2Test);
    }
}
