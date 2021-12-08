package main;

import datatypes.*;
import database.LinesFactoryInterface;
import exceptions.FullBusException;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;

import java.io.FileNotFoundException;
import java.util.*;

public class Lines implements LinesInterface {

    private Map<LineName, LineInterface> lines;
    private final LinesFactoryInterface linesFactory;

    //constructor for tests purposes
    public Lines(Map<LineName, LineInterface> lines) {
        this.lines = new HashMap<>(lines);
        this.linesFactory = null;
    }

    public Lines(LinesFactoryInterface linesFactory) {
        this.linesFactory = linesFactory;
        this.lines = new HashMap<>();
    }

    @Override
    public boolean isLineLoaded(LineName lineName) {
        return lines.containsKey(lineName);
    }

    @Override
    public void updateReachable(List<LineName> lineNameList, StopName stopName, Time time) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException {
        for (LineName line : lineNameList) {
            if (!lines.containsKey(line)) {
                loadLine(line);
            }
            lines.get(line).updateReachable(time, stopName);
        }
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time) throws FileNotFoundException, NegativeCapacityException, FullBusException, NegativeSegmentIndexException {
        if (!lines.containsKey(lineName)) {
            loadLine(lineName);
        }
        return lines.get(lineName).updateCapacityAndGetPreviousStop(stopName,time);
    }

    @Override
    public void clean() {
        lines = new HashMap<>();
    }

    @Override
    public void loadLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException {
        if (isLineLoaded(lineName)) {
            System.out.println("Line has already been loaded.");
            return;
        }
        assert linesFactory != null; //due to test constructor
        Optional<LineInterface> line = linesFactory.createLine(lineName);
        if (line.isEmpty()) {
            throw new NoSuchElementException("Such line does not exist in memory/file.");
        }
        lines.put(lineName, line.get());
    }

    @Override
    public void updateLineSegments() throws FileNotFoundException {
        List<LineSegmentInterface> segmentsToUpdate = new ArrayList<>();
        for (LineName line : lines.keySet()) {
            List<LineSegmentInterface> lineSegmentsForLine = lines.get(line).getLineSegments(); //get all lineSegments for line
            for (LineSegmentInterface lineSegment : lineSegmentsForLine) {
                if (lineSegment.wasUpdated()) { //if lineSegment was updated, add to list of segments, which need to be updated in database
                    segmentsToUpdate.add(lineSegment);
                    lineSegment.wasNotUpdated(); //set back to not updated
                }
            }
        }
        assert linesFactory != null; //due to test constructor
        linesFactory.updateDatabase(segmentsToUpdate);
    }

    @Override
    public Map<LineName, LineInterface> getLines() {
        return this.lines;
    }
}
