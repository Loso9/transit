package main;

import datatypes.*;
import exceptions.FullBusException;

import java.util.*;

public interface LineInterface {
    void updateReachable(Time time, StopName stopName);
    StopName updateCapacityAndGetPreviousStop(StopName stopName, Time time) throws FullBusException;
    Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>> convertToQuadruplet();
    //constructor gets
    LineName getLineName();
    StopName getFirstStop();
    List<LineSegmentInterface> getLineSegments();
    List<Time> getStartingTimes();
}
