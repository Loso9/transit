package main;


import datatypes.*;
import exceptions.FullBusException;

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
        this.startingTimes = new ArrayList<>(startingTimes);
    }

    @Override
    public void updateReachable(Time time, StopName stopName) {
        for (Time startingTime : startingTimes) {
            if(stopName.equals(firstStop)) {
                if(startingTime.getTime() >= time.getTime()) {
                    Time nextTime = startingTime;
                    boolean travelSegmentPossibleFirst;
                    for (LineSegmentInterface lineSegment : lineSegments) {
                        Triplet<Time, StopName, Boolean> nextStop = lineSegment.nextStopAndUpdateReachable(nextTime);
                        nextTime = nextStop.getFirst();
                        travelSegmentPossibleFirst = nextStop.getThird();
                        if (!travelSegmentPossibleFirst) break;
                    }
                }
                continue;
            }

            Time nextTime = startingTime;

            for (int i = 0; i < lineSegments.size(); i++) {
                Pair<Time, StopName> nextStop = lineSegments.get(i).nextStop(nextTime);
                if(nextStop.getSecond().equals(stopName)) {
                    if(nextStop.getFirst().getTime() >= time.getTime()) {
                        Time nextTimeInnerCycle = nextStop.getFirst();
                        boolean travelSegmentPossibleSecond;
                        for (int j = i + 1; j < lineSegments.size(); j++) {
                            Triplet<Time, StopName, Boolean> nextStopInnerCycle = lineSegments.get(j).nextStopAndUpdateReachable(nextTimeInnerCycle);
                            nextTime = nextStopInnerCycle.getFirst();
                            travelSegmentPossibleSecond = nextStopInnerCycle.getThird();
                            if (!travelSegmentPossibleSecond) break;
                        }
                    }
                    continue;
                }
                nextTime = nextStop.getFirst();
            }
        }
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(StopName stopName, Time time) throws FullBusException {
        if (stopName.equals(firstStop)) {
            throw new NoSuchElementException("You are calling the method for first stop - there is no previous stop.");
        }
        for (Time forTime: startingTimes) {
            StopName stopToReturn = firstStop;
            Time nextStopTime = forTime;
            for (LineSegmentInterface lineSegment : lineSegments) {
                Pair<Time, StopName> nextStop = lineSegment.nextStop(nextStopTime);
                if (nextStop.getFirst().equals(time) && nextStop.getSecond().equals(stopName)) {
                    lineSegment.incrementCapacity(time);
                    return stopToReturn;
                }
                stopToReturn = nextStop.getSecond();
                nextStopTime = nextStop.getFirst();
            }
        }
        return null;
    }

    @Override
    public LineName getLineName() {
        return lineName;
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
    public List<Time> getStartingTimes() {
        return startingTimes;
    }

    @Override
    public Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>> convertToQuadruplet() {
        return new Quadruplet<>(lineName, firstStop, lineSegments, startingTimes);
    }
}
