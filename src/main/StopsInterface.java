package main;

import datatypes.*;
import java.util.*;

public interface StopsInterface {
    Optional<Pair<List<StopName>, Time>> earliestReachableStopAfter(Time startTime); //potentially more stops -> list of StopName
    boolean setStartingStop(StopName stopName, Time time);
    List<LineName> getLines(StopName stopName);
    Pair<Time, LineName> getReachableAt(StopName stopName);
    void clean();
}
