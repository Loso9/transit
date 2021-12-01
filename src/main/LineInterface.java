package main;

import datatypes.*;
import java.util.*;

public interface LineInterface {
    void updateReachable(Time time, StopName stopName);
    StopName updateCapacityAndGetPreviousStop(StopName stopName, Time time);
    String getName();
    StopName getFirstStop();
    List<LineSegmentInterface> getLineSegments();
    Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>> convertToQuadruplet();
}
