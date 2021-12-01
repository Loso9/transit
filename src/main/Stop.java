package main;

import datatypes.*;

import java.util.*;

public class Stop implements StopInterface {

    private final StopName stopName;
    private Time reachableAt;
    private LineName reachableVia;
    private final ArrayList<LineName> lines;

    public Stop(StopName stopName, List<LineName> lines) {
        this.stopName = stopName;
        this.lines = new ArrayList<>(lines);
        //temporary init
        reachableAt = new Time(Integer.MAX_VALUE);
        reachableVia = null;
    }

    @Override
    public StopName getStopName() {
        return stopName;
    }

    @Override
    public List<LineName> getLines() {
        return lines;
    }

    @Override
    public Pair<Time, LineName> getReachableAt() {
        Optional<LineName> reachableLine = Optional.ofNullable(reachableVia);
        return reachableLine.map(lineName -> new Pair<>(reachableAt, lineName)).orElseGet(() -> new Pair<>(reachableAt, null));
    }

    @Override
    public void updateReachableAt(Time time, LineName lineName) {
        Time newTime = Optional.ofNullable(time).orElseThrow(NullPointerException::new);
        Optional<LineName> newLineName = Optional.of(lineName);
        if (newTime.getTime() < reachableAt.getTime()) {
            reachableAt = newTime;
            newLineName.ifPresent(name -> reachableVia = name);
        }
    }

    @Override
    public Pair<StopName, List<LineName>> convertToPair() {
        return new Pair<>(stopName, lines);
    }

}