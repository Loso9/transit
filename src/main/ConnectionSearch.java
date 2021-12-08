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
        ConnectionData connection = null;
        try {
            boolean existsPath = existsPath(from, to, time, stops, lines);
            if (existsPath) {
                connection = new ConnectionData();
                StopName previousStop = to;
                while (!previousStop.equals(from)) {
                    Pair<Time, LineName> data = stops.getReachableAt(previousStop);
                    connection.addToConnection(data.getSecond(), previousStop, data.getFirst());
                    //update capacity and get previous stop
                    if (!previousStop.equals(from)) {
                        previousStop = lines.updateCapacityAndGetPreviousStop(data.getSecond(), previousStop, data.getFirst());
                    }
                }
                //add finish
                connection.addToConnection(null, previousStop, time);
            }
        }
        catch (FileNotFoundException | NegativeCapacityException | FullBusException | NegativeSegmentIndexException e) {
            System.out.println(e.getMessage());
            return null;
        }
        finally {
            lines.clean();
            stops.clean();
        }
        return connection;
    }

    private static boolean existsPath(StopName from, StopName to, Time time, StopsInterface stops, LinesInterface lines) {
        try {
            stops.setStartingStop(from, time);
            List<LineName> getLinesFrom =  new LinkedList<>(stops.getLines(from));
            lines.updateReachable(getLinesFrom, from, time);
            Optional<Pair<StopName, Time>> earliestReachableStopAfter = stops.earliestReachableStopAfter(time);
            while (earliestReachableStopAfter.isPresent()) {
                StopName earliestReachable = earliestReachableStopAfter.get().getFirst();
                Time earliestReachableTime = earliestReachableStopAfter.get().getSecond();
                if (earliestReachable.getName().equals(to.getName())) {
                    return true;
                }
                earliestReachableStopAfter = stops.earliestReachableStopAfter(earliestReachableTime);
            }
        }
        catch (FileNotFoundException | NegativeCapacityException | NegativeSegmentIndexException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
}
