package main;

import datatypes.*;

import java.util.*;

public interface StopInterface {
    List<LineName> getLines();
    Pair<Time, LineName> getReachableAt();
    void updateReachableAt(Time time, LineName lineName);
    StopName getStopName();

}
