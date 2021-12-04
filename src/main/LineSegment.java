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
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = new HashMap<>(Optional.ofNullable(numberOfPassengers).orElseThrow(NullPointerException::new));
        this.lineName = Optional.ofNullable(lineName).orElseThrow(NullPointerException::new);
        this.nextStop = nextStop;
        if (capacity < 0) {
            throw new NegativeCapacityException();
        }
        this.capacity = capacity;
    }

    @Override
    public Pair<Time, StopName> nextStop(Time startTime) {
        if (busDoesntDriveAtTime(startTime)) {
            throwException(); //shouldnt call method with parameter startTime which isnt in list of startTimes (map numOfPassengers)
        }
        Time newTime = new Time(startTime.getTime() + timeToNextStop.getTimeDiff());
        return new Pair<>(newTime, nextStop.getStopName());
    }

    @Override
    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime) {
        if (busDoesntDriveAtTime(startTime)) {
            throwException(); //shouldnt call method with parameter startTime which isnt in list of startTimes (map numOfPassengers)
        }
        Time nextStopTime = new Time(timeToNextStop.getTimeDiff() + startTime.getTime());
        boolean freeSeats = false;
        if (numberOfPassengers.get(startTime) < capacity) { //if the bus is full, it shouldnt be reachable
            nextStop.updateReachableAt(nextStopTime, lineName);
            freeSeats = true;
        }
        return new Triplet<>(nextStopTime, nextStop.getStopName(), freeSeats);
    }

    @Override
    public void incrementCapacity(Time startTime) throws FullBusException {
        if (busDoesntDriveAtTime(startTime)) { //
            throwException();
        }
        else if (numberOfPassengers.get(startTime) >= capacity) { //bus is full, throw exception
            throw new FullBusException();
        }
        numberOfPassengers.put(startTime, numberOfPassengers.get(startTime) + 1); //increase num of passengers
    }

    @Override
    public int getPassengersAt(Time startTime) {
        return numberOfPassengers.get(startTime);
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

    @Override
    public Map<Time, Integer> getBuses() {
        return Collections.unmodifiableMap(numberOfPassengers);
    }

}
