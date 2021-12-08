package main;

import datatypes.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface LinesInterface {
    boolean isLineLoaded(LineName lineName);
    void updateReachable(List<LineName> lineNameList, StopName stopName, Time time) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException;
    StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time) throws FileNotFoundException, NegativeCapacityException, FullBusException, NegativeSegmentIndexException;
    void loadLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException;
    void updateLineSegments() throws FileNotFoundException;
    Map<LineName, LineInterface> getLines();
    void clean();
}
