package main;

import database.StopsFactoryInterface;
import datatypes.*;
import java.io.FileNotFoundException;
import java.util.*;

public class Stops implements StopsInterface {

    private final StopsFactoryInterface stopsFactory;
    private Map<StopName, StopInterface> stops;

    //constructor for test purposes
    public Stops(Map<StopName, StopInterface> stops) {
        this.stops = new HashMap<>(stops);
        this.stopsFactory = null;
    }

    public Stops(StopsFactoryInterface stopsFactory) {
        this.stopsFactory = stopsFactory;
        stops = new HashMap<>();
    }

    @Override
    public Optional<Pair<StopName, Time>> earliestReachableStopAfter(Time time) {
        //if there wasnt assumption in the diagram that no starting times were different
        // - then we would have to return List of StopNames
        StopName earliestReachableStopAfter = null; //init
        Time minTime = new Time(Integer.MAX_VALUE); //init for finding min
        for (Map.Entry<StopName, StopInterface> stop : stops.entrySet()) {
            StopInterface forStop = stop.getValue();
            Time forStopReachableAt = forStop.getReachableAt().getFirst();
            if (forStopReachableAt.getTime() > time.getTime()) {
                if (forStopReachableAt.getTime() < minTime.getTime()) {
                    minTime = forStopReachableAt;
                    earliestReachableStopAfter = forStop.getStopName();
                }
            }
        }
        if (earliestReachableStopAfter == null) {
            return Optional.empty();
        }
        return Optional.of(new Pair<>(earliestReachableStopAfter, minTime));
    }

    @Override
    public void setStartingStop(StopName stopName, Time time) throws FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        stops.get(stopName).updateReachableAt(time, null);
    }

    @Override
    public List<LineName> getLines(StopName stopName) throws FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        return stops.get(stopName).getLines();
    }

    @Override
    public Pair<Time, LineName> getReachableAt(StopName stopName) throws FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        return stops.get(stopName).getReachableAt();
    }

    @Override
    public boolean isStopLoaded(StopName stopName) {
        return stops.containsKey(stopName);
    }

    @Override
    public StopInterface getStop(StopName stopName) throws FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        return stops.get(stopName);
    }

    @Override
    public void clean() {
        stops = new HashMap<>();
    }

    private void loadStop(StopName stopName) throws FileNotFoundException {
        if (isStopLoaded(stopName)) {
            System.out.println("Stop " + stopName.getName() + " has already been loaded");
            return;
        }
        assert stopsFactory != null; //due to test constructor
        Optional<StopInterface> stop = stopsFactory.createStop(stopName);
        if (stop.isEmpty()) {
            throw new NoSuchElementException("Such stop does not exist in memory/file");
        }
        stops.put(stopName, stop.get());
    }

    public Map<StopName, StopInterface> getStops() {
        return stops;
    }
}
