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
    void wasNotUpdated();
    //constructor gets
    TimeDiff getTimeToNextStop();
    Map<Time, Integer> getBuses();
    int getCapacity();
    LineName getLineName();
    StopInterface getNextStop();
    int getSegmentIndex();
    boolean wasUpdated();

}
