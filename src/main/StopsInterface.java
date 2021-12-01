package main;

import datatypes.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface StopsInterface {
    Optional<Pair<StopName, Time>> earliestReachableStopAfter(Time time);
    void setStartingStop(StopName stopName, Time time) throws AlreadyLoadedStopException, FileNotFoundException;
    List<LineName> getLines(StopName stopName) throws AlreadyLoadedStopException, FileNotFoundException;
    Pair<Time, LineName> getReachableAt(StopName stopName) throws NotLoadedStopException;
    boolean isStopLoaded(StopName stopName);
    StopInterface getStop(StopName stopName) throws NotLoadedStopException;
    void clean();
}