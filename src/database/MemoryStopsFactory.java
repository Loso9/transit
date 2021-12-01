package database;

import java.util.*;
import datatypes.*;
import main.*;

public class MemoryStopsFactory implements StopsFactoryInterface {

    private final List<Pair<StopName, List<LineName>>> stops;

    public MemoryStopsFactory(MemoryDatabase mdb) {
        this.stops = mdb.readStopsAsPairs();
    }

    @Override
    public Optional<StopInterface> createStop(StopName stopName) {
        for (Pair<StopName, List<LineName>> stop : stops) {
            if (stop.getFirst().equals(stopName)) {
                return Optional.of(new Stop(stopName, stop.getSecond()));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Pair<StopName, List<LineName>>> getStops() {
        return stops;
    }

}