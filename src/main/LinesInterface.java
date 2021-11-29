package main;

import datatypes.*;
import exceptions.*;

import java.util.*;

public interface LinesInterface {
    boolean isLineLoaded(LineName lineName);
    void updateReachable(List<LineName> lineNameList, StopName stopName, Time time) throws AlreadyLoadedLineException;
    StopName updateCapacityAndGetPrevious(LineName lineName, StopName stopName, Time time);
    void clean();
}
