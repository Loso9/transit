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

    public LineSegment(TimeDiff timeToNextStop, Map<Time, Integer> numberOfPassengers, int capacity, LineName lineName,
                       StopInterface nextStop) throws NegativeCapacityException {
        this.timeToNextStop = Optional.ofNullable(timeToNextStop).orElseThrow(NullPointerException::new);
        this.numberOfPassengers = new HashMap<>(Optional.ofNullable(numberOfPassengers).orElseThrow(NullPointerException::new));
        this.lineName = Optional.ofNullable(lineName).orElseThrow(NullPointerException::new);
        this.nextStop = Optional.ofNullable(nextStop).orElseThrow(NullPointerException::new);
        if (capacity < 0) {
            throw new NegativeCapacityException();
        }
        this.capacity = capacity;
    }

    @Override
    public Pair<Time, StopName> nextStop(Time time) {
        if (busDoesntDriveAtTime(time)) {
            throwException();
        }
        Time newTime = new Time(time.getTime() + timeToNextStop.getTimeDiff());
        return new Pair<>(newTime, nextStop.getStopName());
    }

    @Override
    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time time) {
        if (busDoesntDriveAtTime(time)) {
            throwException();
        }
        Time nextStopTime = new Time(timeToNextStop.getTimeDiff() + time.getTime());
        boolean freeSeats = false;
        if (numberOfPassengers.get(time) < capacity) {
            nextStop.updateReachableAt(nextStopTime, lineName);
            freeSeats = true;
        }
        return new Triplet<>(nextStopTime, nextStop.getStopName(), freeSeats);
    }

    @Override
    public void incrementCapacity(Time time) throws FullBusException {
        if (busDoesntDriveAtTime(time)) {
            throwException();
        }
        else if (numberOfPassengers.get(time) >= capacity) {
            throw new FullBusException();
        }
        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1);
    }

    @Override
    public int getPassengers(Time time) {
        return numberOfPassengers.get(time);
    }

    private boolean busDoesntDriveAtTime(Time time) {
        return !numberOfPassengers.containsKey(time);
    }

    private void throwException() {
        throw new NoSuchElementException("No such bus arrives at the stop at that time.");
    }

    @Override
    public Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface> convertToQuintuplet() {
        return new Quintuplet<>(timeToNextStop, numberOfPassengers, capacity, lineName, nextStop);
    }

}
