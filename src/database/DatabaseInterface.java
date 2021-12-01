package database;

import java.util.*;
import datatypes.*;
import main.LineSegmentInterface;
import main.*;

public interface DatabaseInterface {
    List<LineInterface> readLines();
    List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> readLinesAsQuadruplets();
    List<StopInterface> readStops();
    List<Pair<StopName, List<LineName>>> readStopsAsPairs();
    List<Pair<LineName, List<LineSegmentInterface>>> readSegments();
    List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> readSegmentsAsQuintuplets();
}
