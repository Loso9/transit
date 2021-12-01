package main;

import datatypes.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.*;

public class ConnectionSearch {

    private final StopsInterface stops;
    private final LinesInterface lines;

    public ConnectionSearch(StopsInterface stops, LinesInterface lines) {
        this.stops = stops;
        this.lines = lines;
    }

    public ConnectionData search(StopName from, StopName to, Time time) {
        try {
            ConnectionData connection = new ConnectionData();
            stops.setStartingStop(from, time);
            LinkedList<StopName> earliestStopsFrom = new LinkedList<>();
            earliestStopsFrom.add(from);
            while (!earliestStopsFrom.contains(to)) {
                do {
                    StopName stop = earliestStopsFrom.removeLast();
                    List<LineName> linesFromStop = stops.getLines(stop);
                    lines.updateReachable(linesFromStop, stop, time);
                } while (!earliestStopsFrom.isEmpty());
                Optional<Pair<StopName, Time>> connectionData = stops.earliestReachableStopAfter(time);
                if (connectionData.isEmpty()) {
                    return null;
                }
                earliestStopsFrom.add(connectionData.get().getFirst());
            }
            return connection;
        }
        catch (AlreadyLoadedStopException | AlreadyLoadedLineException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            lines.clean();
            stops.clean();
        }

    }
}