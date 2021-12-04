package main;

import datatypes.*;
import exceptions.*;

import java.util.Map;

public interface LineSegmentInterface {
    Pair<Time, StopName> nextStop(Time time);
    Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime);
    void incrementCapacity(Time startTime) throws FullBusException;
    int getPassengersAt(Time time);
    Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface> convertToQuintuplet();
    Map<Time, Integer> getBuses();
}
