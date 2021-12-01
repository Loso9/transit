package database;

import datatypes.*;
import main.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface StopsFactoryInterface {
    Optional<StopInterface> createStop(StopName stopName) throws FileNotFoundException;
    default Integer checkIfContains(StopName stopName) {
        List<Pair<StopName, List<LineName>>> stops = getStops();
        int index = 0;
        for (Pair<StopName, List<LineName>> stop : stops) {
            if (stop.getFirst().equals(stopName)) {
                return index;
            }
            index++;
        }
        return null;
    } //returns Index or null;
    List<Pair<StopName, List<LineName>>> getStops();
}