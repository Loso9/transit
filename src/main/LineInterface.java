package main;

import datatypes.*;
import java.util.*;

public interface LineInterface {
    void updateReachable(Time time, StopName stopName);
    void updateCapacityAndGetPreviousStop(StopName stopName, Time time);
    String getName();
    StopName getFirstStop();
    List<LineSegment> getLineSegments();
}
