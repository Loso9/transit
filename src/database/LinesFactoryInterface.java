package database;

import datatypes.*;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface LinesFactoryInterface {
    Optional<LineInterface> createLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException;
    //included in linesfactory, because it shares objects
    Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex) throws NegativeCapacityException, FileNotFoundException, NegativeSegmentIndexException;
    default Integer checkIfContainsLine(LineName lineName) {
        List<LineInterface> lines = getLines();
        int index = 0;
        for (LineInterface line : lines) {
            if (line.getLineName().equals(lineName)) {
                return index;
            }
            index++;
        }
        return null;
    } //returns Index or null;

    default boolean checkIfContainsSegment(LineName lineName, int segmentIndex) {
        List<LineSegmentInterface> lineSegments = getSegments();
        for (LineSegmentInterface segment : lineSegments) {
            if (segment.getSegmentIndex() == segmentIndex && segment.getLineName().equals(lineName)) { //we want to look at segments which connected to lineName
                return true;
            }
        }
        return false;
    }
    List<LineInterface> getLines();
    List<LineSegmentInterface> getSegments();
    void updateDatabase(List<LineSegmentInterface> lineSegments) throws FileNotFoundException;
}
