package main;

import datatypes.*;
import exceptions.NoSuchLineException;

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
        LineName reachableLine = Optional.ofNullable(reachableVia).orElseThrow(NullPointerException::new);
        return new Pair<>(reachableAt, reachableLine);
    }

    @Override
    public void updateReachableAt(Time time, LineName lineName) {
        Time newTime = Optional.ofNullable(time).orElseThrow(NullPointerException::new);
        if (newTime.getTime() < reachableAt.getTime()) {
            reachableAt = newTime;
            reachableVia = lineName;
        }
    }

}