package tests;

import datatypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

public class TuplesTest {

    private Pair<String, Integer> pair1;
    private Pair<LineName, StopName> pair2;
    private Triplet<String, LineName, Time> triplet1;
    private Triplet<Pair<Integer, Integer>, Time, TimeDiff> triplet2;
    private Quadruplet<Integer, String, List<Time>, TimeDiff> quadruplet1;
    private Quadruplet<Integer, Triplet<Integer, Integer, String>, StopName, StopName> quadruplet2;

    public void setUp() {
        pair1 = new Pair<>("pair1String", 1);
        pair2 = new Pair<>(new LineName("pair2"), new StopName("pair22"));

        triplet1 = new Triplet<>("triplet1", new LineName("triplet1"), new Time(1));
        triplet2 = new Triplet<>(new Pair<>(2, 2), new Time(2), new TimeDiff(2));

        quadruplet1 = new Quadruplet<>(1, "quartet1", null, new TimeDiff(1));
        quadruplet2 = new Quadruplet<>(2, new Triplet<>(2, 2, "quartet2"), new StopName("quartet2"), new StopName("quartet22"));
    }

    @Test
    public void getNthElement() {
        setUp();
        //pairs
        assertEquals("pair1String", pair1.getFirst());
        assertEquals(Integer.valueOf(1), pair1.getSecond());
        assertEquals(new LineName("pair2"), pair2.getFirst());
        assertEquals(new StopName("pair22"), pair2.getSecond());

        //triplets
        assertEquals("triplet1", triplet1.getFirst());
        assertEquals(new LineName("triplet1"), triplet1.getSecond());
        assertEquals(new Time(1), triplet1.getThird());
        assertEquals(new Pair<>(2, 2), triplet2.getFirst());
        assertEquals(new Time(2), triplet2.getSecond());
        assertEquals(new TimeDiff(2), triplet2.getThird());

        //quartets
        assertEquals(Integer.valueOf(1), quadruplet1.getFirst());
        assertEquals("quartet1", quadruplet1.getSecond());
        assertNull(quadruplet1.getThird());
        assertEquals(new TimeDiff(1), quadruplet1.getForth());
        assertEquals(Integer.valueOf(2), quadruplet2.getFirst());
        assertEquals(new Triplet<>(2, 2, "quartet2"), quadruplet2.getSecond());
        assertEquals(new StopName("quartet2"), quadruplet2.getThird());
        assertEquals(new StopName("quartet22"), quadruplet2.getForth());
    }


}
