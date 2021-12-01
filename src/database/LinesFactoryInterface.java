package database;

import datatypes.*;
import exceptions.NegativeCapacityException;
import main.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface LinesFactoryInterface {
    Optional<LineInterface> createLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException;
    Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex, int capacity) throws NegativeCapacityException, FileNotFoundException; //included in linesfactory, because it shares objects
    default Integer checkIfContainsLine(LineName lineName) {
        List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> lines = getLines();
        int index = 0;
        for (Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>> line : lines) {
            if (line.getFirst().equals(lineName)) {
                return index;
            }
            index++;
        }
        return null;
    } //returns Index or null;

    default boolean checkIfContainsSegment(LineName lineName, int segmentIndex) {
        List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> lineSegments = getSegments();
        for (Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>> segment : lineSegments) {
            if (segment.getFirst().equals(lineName)) { //we want to look at segments which connected to lineName
                if (segment.getSecond().get(segmentIndex) != null) { //there exists Quintuplet (segment)
                    return true;
                }
            }
        }
        return false;

    }
    List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> getLines();
    List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> getSegments();
}
