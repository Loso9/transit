package main;

import datatypes.*;

public interface LineSegmentInterface {
    Pair<Time, StopName> nextStop(Time time);
    Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime);
    void incrementCapacity(Time startTime);
}
