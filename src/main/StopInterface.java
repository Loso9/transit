package main;

import datatypes.*;
import exceptions.NoSuchLineException;

import java.util.*;

public interface StopInterface {
    List<LineName> getLines();
    Pair<Time, LineName> getReachableAt();
    void updateReachableAt(Time time, LineName lineName) throws NoSuchLineException;
    StopName getStopName();

}
