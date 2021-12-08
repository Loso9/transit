package main;

import datatypes.*;
import exceptions.*;

import java.util.*;

public class LineSegment implements LineSegmentInterface {

    private final TimeDiff timeToNextStop;
    private final Map<Time, Integer> numberOfPassengers;
    private final int capacity;
    private final LineName lineName;
    private final StopInterface nextStop;
    private final int segmentIndex;
    private boolean wasUpdated;

    public LineSegment(TimeDiff timeToNextStop, Map<Time, Integer> numberOfPassengers, int capacity, LineName lineName,
                       StopInterface nextStop, int segmentIndex) throws NegativeCapacityException, NegativeSegmentIndexException {
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = new HashMap<>(Optional.ofNullable(numberOfPassengers).orElseThrow(NullPointerException::new));
        this.lineName = Optional.ofNullable(lineName).orElseThrow(NullPointerException::new);
        this.nextStop = nextStop;
        if (capacity < 0) {
            throw new NegativeCapacityException();
        }
        this.capacity = capacity;
        if (segmentIndex < 0) {
            throw new NegativeSegmentIndexException();
        }
        this.segmentIndex = segmentIndex;
        wasUpdated = false;
    }

    @Override
    public TimeDiff getTimeToNextStop() {
        return this.timeToNextStop;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public LineName getLineName() {
        return this.lineName;
    }

    @Override
    public StopInterface getNextStop() {
        return this.nextStop;
    }

    @Override
    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    @Override
    public Pair<Time, StopName> nextStop(Time startTime) {
        Time newTime = new Time(startTime.getTime() + timeToNextStop.getTimeDiff());
        return new Pair<>(newTime, nextStop.getStopName());
    }

    @Override
    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime) {
        Time nextStopTime = new Time(timeToNextStop.getTimeDiff() + startTime.getTime());
        boolean freeSeats = false;
        if (numberOfPassengers.get(nextStopTime) < capacity) { //if the bus is full, it shouldnt be reachable
            nextStop.updateReachableAt(nextStopTime, lineName);
            freeSeats = true;
        }
        return new Triplet<>(nextStopTime, nextStop.getStopName(), freeSeats);
    }

    @Override
    public void incrementCapacity(Time time) throws FullBusException {
        if (numberOfPassengers.get(time) >= capacity) { //bus is full, throw exception
            throw new FullBusException();
        }
        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1); //increase num of passengers
        wasUpdated = true;
    }

    @Override
    public int getPassengersAt(Time startTime) {
        if (busDoesntDriveAtTime(startTime)) {
            throwException();
        }
        return numberOfPassengers.get(startTime);
    }

    private boolean busDoesntDriveAtTime(Time time) {
        return !numberOfPassengers.containsKey(time);
    }

    private void throwException() {
        throw new NoSuchElementException("No such bus arrives at the stop at that time.");
    }

    @Override
    public Map<Time, Integer> getBuses() {
        return this.numberOfPassengers;
    }

    @Override
    public boolean wasUpdated() {
        return this.wasUpdated;
    }

    @Override
    public void wasNotUpdated() {
        this.wasUpdated = false;
    }

    @Override
    public Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface> convertToQuintuplet() {
        return new Quintuplet<>(timeToNextStop, numberOfPassengers, capacity, lineName, nextStop);
    }

}
