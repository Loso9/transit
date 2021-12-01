package main;

import database.StopsFactoryInterface;
import datatypes.*;
import exceptions.*;

import java.io.FileNotFoundException;
import java.util.*;

public class Stops implements StopsInterface {

    private final StopsFactoryInterface stopsFactory;
    private HashMap<StopName, StopInterface> stops;

    public Stops(StopsFactoryInterface stopsFactory) {
        this.stopsFactory = stopsFactory;
        stops = new HashMap<>();
    }

    @Override
    public Optional<Pair<StopName, Time>> earliestReachableStopAfter(Time time) {
        StopInterface earliestReachableStopAfter = null; //init
        Time minTime = new Time(Integer.MAX_VALUE); //init for finding min
        for (Map.Entry<StopName, StopInterface> stop : stops.entrySet()) {
            StopInterface forStop = stop.getValue();
            Time forStopReachableAt = forStop.getReachableAt().getFirst();
            if (forStopReachableAt.getTime() > time.getTime())
                if (forStopReachableAt.getTime() < minTime.getTime()) {
                    minTime = forStopReachableAt;
                    earliestReachableStopAfter = forStop;
                }
        }
        if (earliestReachableStopAfter == null) {
            return Optional.of(new Pair<>(null, minTime));
        }
        return Optional.of(new Pair<>(earliestReachableStopAfter.getStopName(), minTime));
    }

    @Override
    public void setStartingStop(StopName stopName, Time time) throws AlreadyLoadedStopException, FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        stops.get(stopName).updateReachableAt(time, null);
    }

    @Override
    public List<LineName> getLines(StopName stopName) throws AlreadyLoadedStopException, FileNotFoundException {
        if (!isStopLoaded(stopName)) {
            loadStop(stopName);
        }
        return stops.get(stopName).getLines();
    }

    @Override
    public Pair<Time, LineName> getReachableAt(StopName stopName) throws NotLoadedStopException {
        if (!isStopLoaded(stopName)) {
            throw new NotLoadedStopException(stopName);
        }
        return stops.get(stopName).getReachableAt();
    }

    @Override
    public boolean isStopLoaded(StopName stopName) {
        return stops.containsKey(stopName);
    }

    @Override
    public StopInterface getStop(StopName stopName) throws NotLoadedStopException {
        if (!isStopLoaded(stopName)) {
            throw new NotLoadedStopException(stopName);
        }
        return stops.get(stopName);
    }

    @Override
    public void clean() {
        stops = new HashMap<>();
    }

    private void loadStop(StopName stopName) throws AlreadyLoadedStopException, FileNotFoundException {
        if (isStopLoaded(stopName)) {
            throw new AlreadyLoadedStopException(stopName);
        }
        Optional<StopInterface> stop = stopsFactory.createStop(stopName);
        if (stop.isEmpty()) {
            throw new NoSuchElementException("Such stop does not exist in memory/file");
        }
        stops.put(stopName, stop.get());
    }
}