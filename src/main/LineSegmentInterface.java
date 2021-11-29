package main;

import datatypes.*;
import exceptions.*;

public interface LineSegmentInterface {
    Pair<Time, StopName> nextStop(Time time);
    Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime);
    void incrementCapacity(Time startTime) throws FullBusException;
    int getPassengers(Time time);
}
