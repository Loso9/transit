package main;

import datatypes.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface LinesInterface {
    boolean isLineLoaded(LineName lineName);
    void updateReachable(List<LineName> lineNameList, StopName stopName, Time time) throws AlreadyLoadedLineException, FileNotFoundException, NegativeCapacityException;
    StopName updateCapacityAndGetPrevious(LineName lineName, StopName stopName, Time time) throws AlreadyLoadedLineException, FileNotFoundException, NegativeCapacityException;
    void clean();
}