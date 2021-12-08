package main;

import datatypes.*;
import java.io.FileNotFoundException;
import java.util.*;

public interface StopsInterface {
    Optional<Pair<StopName, Time>> earliestReachableStopAfter(Time time);
    void setStartingStop(StopName stopName, Time time) throws FileNotFoundException;
    List<LineName> getLines(StopName stopName) throws FileNotFoundException;
    Pair<Time, LineName> getReachableAt(StopName stopName) throws FileNotFoundException;
    boolean isStopLoaded(StopName stopName);
    StopInterface getStop(StopName stopName) throws FileNotFoundException;
    Map<StopName, StopInterface> getStops(); //for test purposes
    void clean();
}
