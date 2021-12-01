package main;

import datatypes.*;
import exceptions.AlreadyLoadedLineException;
import database.LinesFactoryInterface;
import exceptions.NegativeCapacityException;

import java.io.FileNotFoundException;
import java.util.*;

public class Lines implements LinesInterface {

    private HashMap<LineName, LineInterface> lines;
    private final LinesFactoryInterface linesFactory;

    public Lines(LinesFactoryInterface linesFactory) {
        this.linesFactory = linesFactory;
        this.lines = new HashMap<>();
    }

    @Override
    public boolean isLineLoaded(LineName lineName) {
        return lines.containsKey(lineName);
    }

    @Override
    public void updateReachable(List<LineName> lineNameList, StopName stopName, Time time) throws AlreadyLoadedLineException, FileNotFoundException, NegativeCapacityException {
        for (LineName line : lineNameList) {
            if (!lines.containsKey(line)) {
                loadLine(line);
            }
            lines.get(line).updateReachable(time, stopName);
        }
    }

    @Override
    public StopName updateCapacityAndGetPrevious(LineName lineName, StopName stopName, Time time) throws AlreadyLoadedLineException, FileNotFoundException, NegativeCapacityException {
        if (!lines.containsKey(lineName)) {
            loadLine(lineName);
        }
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName,time);
    }

    @Override
    public void clean() {
        lines = new HashMap<>();
    }

    private void loadLine(LineName lineName) throws AlreadyLoadedLineException, FileNotFoundException, NegativeCapacityException {
        if (isLineLoaded(lineName)) {
            throw new AlreadyLoadedLineException(lineName);
        }
        Optional<LineInterface> line = linesFactory.createLine(lineName);
        if (line.isEmpty()) {
            throw new NoSuchElementException("Such line does not exist in memory/file.");
        }
        lines.put(lineName, line.get());
    }

    /* NOT DONE YET //TODO */
    public void updateSegments() {
        //linesFactory.updateDatabase(segments);
    }

    /*
    public LineInterface getLine(LineName lineName) {
        if (!lines.containsKey(lineName)) {
            LineInterface line = linesFactory.createLine()
        }
    }
    */
}
