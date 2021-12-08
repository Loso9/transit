package database;

import java.util.*;
import datatypes.*;
import main.*;

public class MemoryStopsFactory implements StopsFactoryInterface {

    private final List<StopInterface> stops;

    public MemoryStopsFactory(MemoryDatabase mdb) {
        this.stops = mdb.readStops();
    }

    @Override
    public Optional<StopInterface> createStop(StopName stopName) {
        for (StopInterface stop : stops) {
            if (stop.getStopName().equals(stopName)) {
                return Optional.of(new Stop(stopName, stop.getLines()));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<StopInterface> getStops() {
        return this.stops;
    }
}
