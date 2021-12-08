package database;

import datatypes.*;
import main.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface StopsFactoryInterface {
    Optional<StopInterface> createStop(StopName stopName) throws FileNotFoundException;
    default Integer checkIfContains(StopName stopName) {
        List<StopInterface> stops = getStops();
        int index = 0;
        for (StopInterface stop : stops) {
            if (stop.getStopName().equals(stopName)) {
                return index;
            }
            index++;
        }
        return null;
    } //returns Index or null;
    List<StopInterface> getStops();
}
