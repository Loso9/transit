package database;

import datatypes.*;
import main.*;
import java.util.*;

public class MemoryLinesFactory implements LinesFactoryInterface {

    private final List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> lines;
    private final List<Pair<LineName, List<LineSegmentInterface>>> segments;

    public MemoryLinesFactory(MemoryDatabase mdb) {
        lines = mdb.readLinesAsQuadruplets();
        segments = mdb.readSegments();
    }

    @Override
    public Optional<LineInterface> createLine(LineName lineName) {
        Integer index = checkIfContainsLine(lineName);
        if (index != null) return Optional.of(new Line(lines.get(index).getFirst(), lines.get(index).getSecond(), lines.get(index).getThird(), lines.get(index).getForth()));
        return Optional.empty();
    }

    @Override
    public Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex, int capacity) {
        boolean exists = checkIfContainsSegment(lineName, segmentIndex);
        if (exists) {
            for (Pair<LineName, List<LineSegmentInterface>> segment : segments) {
                if (segment.getFirst().equals(lineName)) {
                    return Optional.of(segment.getSecond().get(segmentIndex));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> getLines() {
        return lines;
    }

    @Override
    public List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> getSegments() {
        List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> list;
        List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> returnList = new ArrayList<>();
        for (Pair<LineName, List<LineSegmentInterface>> x : segments) {
            list = new ArrayList<>();
            for (LineSegmentInterface segment : x.getSecond()) {
                list.add(segment.convertToQuintuplet());
            }
            returnList.add(new Pair<>(x.getFirst(), list));
        }
        return returnList;
    }
}