package main;


import datatypes.*;
import java.util.*;

public class Line implements LineInterface {

    private final LineName lineName;
    private final StopName firstStop;
    private final ArrayList<LineSegmentInterface> lineSegments;
    private final ArrayList<Time> startingTimes;

    public Line(LineName lineName, StopName firstStop, List<LineSegmentInterface> lineSegments, List<Time> startingTimes) {
        this.lineName = lineName;
        this.firstStop = firstStop;
        this.lineSegments = new ArrayList<>(lineSegments);
        if (lineSegments.isEmpty() || startingTimes.isEmpty()) {
            throw new NoSuchElementException();
        }
        startingTimes.sort(Comparator.comparingInt(Time::getTime));
        this.startingTimes = new ArrayList<>(startingTimes);
    }

    @Override
    public void updateReachable(Time time, StopName stopName) {
        //TODO
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(StopName stopName, Time time) {
        if (stopName.equals(firstStop)) {
            throw new NoSuchElementException("You are calling method for first stop - there is no previous stop.");
        }

        //TODO
        return null;
    }

    @Override
    public String getName() {
        return lineName.getName();
    }

    @Override
    public StopName getFirstStop() {
        return firstStop;
    }

    @Override
    public List<LineSegmentInterface> getLineSegments() {
        return lineSegments;
    }

    @Override
    public Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>> convertToQuadruplet() {
        return new Quadruplet<>(lineName, firstStop, lineSegments, startingTimes);
    }
}