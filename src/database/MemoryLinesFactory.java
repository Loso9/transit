package database;

import datatypes.*;
import exceptions.NegativeSegmentIndexException;
import main.*;
import java.util.*;

public class MemoryLinesFactory implements LinesFactoryInterface {

    private final List<LineInterface> lines;
    private final List<LineSegmentInterface> segments;

    public MemoryLinesFactory(MemoryDatabase mdb) {
        lines = new ArrayList<>(mdb.readLines());
        segments = new ArrayList<>(mdb.readSegments());
    }

    @Override
    public Optional<LineInterface> createLine(LineName lineName) {
        if (lineName == null) {
            throw new NullPointerException();
        }
        Integer index = checkIfContainsLine(lineName);
        if (index != null) return Optional.of(new Line(lines.get(index).getLineName(), lines.get(index).getFirstStop(), lines.get(index).getLineSegments(), lines.get(index).getStartingTimes()));
        return Optional.empty();
    }

    @Override
    public Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex) throws NegativeSegmentIndexException {
        if (segmentIndex < 0) {
            throw new NegativeSegmentIndexException();
        }
        boolean exists = checkIfContainsSegment(lineName, segmentIndex);
        if (exists) {
            for (LineSegmentInterface segment : segments) {
                if (segment.getSegmentIndex() == segmentIndex && segment.getLineName().equals(lineName)) {
                    return Optional.of(segment);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<LineInterface> getLines() {
        return lines;
    }

    @Override
    public List<LineSegmentInterface> getSegments() {
        return segments;
    }

    @Override
    public void updateDatabase(List<LineSegmentInterface> lineSegments) {
        List<LineSegmentInterface> segmentsToBeRemoved = new ArrayList<>();
        List<LineSegmentInterface> segmentsToBeUpdated = new ArrayList<>();
        for (LineSegmentInterface lineSegmentOuter : segments) {
            for (LineSegmentInterface lineSegmentInner : lineSegments) {
                //common lineName and common segmentIndex is enough to determine linesegment
                if (lineSegmentOuter.getLineName().equals(lineSegmentInner.getLineName()) &&
                        lineSegmentOuter.getSegmentIndex() == lineSegmentInner.getSegmentIndex()) {
                    segmentsToBeRemoved.add(lineSegmentOuter); //add old lineSegment to list of lineSegments, which are going to be removed
                    segmentsToBeUpdated.add(lineSegmentInner); //add new updated segment to list of lineSegments, which are meant to be added
                }
            }
        }
        segments.removeAll(segmentsToBeRemoved);
        segments.addAll(segmentsToBeUpdated);
    }
}
