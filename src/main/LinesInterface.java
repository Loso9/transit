package main;

import datatypes.*;
import java.util.*;

public interface LinesInterface {
    void updateReachable(List<LineName> lineNameList, StopName stopName, Time time);
    StopName updateCapacityAndGetPrevious(LineName lineName, StopName stopName, Time time);
    void clean();
}
