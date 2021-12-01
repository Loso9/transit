package database;

import datatypes.*;
import exceptions.NegativeCapacityException;
import main.*;
import main.StopInterface;

import java.util.*;

public class MemoryDatabase implements DatabaseInterface {

    private final List<LineInterface> lines;
    private final List<LineName> lineNames;
    private final List<StopInterface> stops;
    private final List<StopName> stopNames;
    ArrayList<LineSegmentInterface> lineSegments;
    List<Pair<LineName, List<LineSegmentInterface>>> allLineSegments;
    public MemoryDatabase() throws NegativeCapacityException {
        stops = new ArrayList<>();
        allLineSegments = new ArrayList<>();
        lines = new ArrayList<>();
        ArrayList<LineName> linesForStop = new ArrayList<>();
        stopNames = new ArrayList<>();
        createStopNames();
        lineNames = new ArrayList<>();
        createLineNames();
        //adding stops with stopNames and lines
        for (StopName stopName : stopNames) {
            switch (stopName.getName()) {
                case "Trnava":
                case "Kosice":
                    linesForStop.add(lineNames.get(0));
                    stops.add(new Stop(stopName, linesForStop));
                    linesForStop.clear();
                    break;
                case "Bratislava":
                    linesForStop.add(lineNames.get(0));
                    linesForStop.add(lineNames.get(1));
                    stops.add(new Stop(stopName, linesForStop));

                    linesForStop.clear();
                    break;
                case "Martin":
                    linesForStop.add(lineNames.get(1));
                    stops.add(new Stop(stopName, linesForStop));
                    linesForStop.clear();
                case "Brezno":
                    linesForStop.add(lineNames.get(2));
                    stops.add(new Stop(stopName, linesForStop));
                    linesForStop.clear();
                    break;
                default:
                    stops.add(new Stop(stopName, linesForStop));
                    break;
            }

            int capacity;
            StopName startStop;
            StopInterface nextStop;
            lineSegments = new ArrayList<>();
            //Line1
            capacity = 15;
            startStop = stopNames.get(0); //trnava
            ArrayList<Time> timesForLine1 = new ArrayList<>();

            timesForLine1.add(new Time(5));
            timesForLine1.add(new Time(8));
            timesForLine1.add(new Time(9));
            timesForLine1.add(new Time(10));

            nextStop = new Stop(stops.get(1).getStopName(), stops.get(1).getLines()); //bratislava
            HashMap<Time, Integer> numOfPassengers = new HashMap<>();
            for (Time time : timesForLine1) {
                numOfPassengers.put(time, 0);
            }

            lineSegments.add(new LineSegment(new TimeDiff(11), numOfPassengers, capacity, lineNames.get(0), nextStop));
            nextStop = new Stop(stops.get(2).getStopName(), stops.get(2).getLines()); //kosice
            lineSegments.add(new LineSegment(new TimeDiff(13), numOfPassengers, capacity, lineNames.get(0), nextStop));

            lines.add(new Line(lineNames.get(0), startStop, lineSegments, timesForLine1));
            allLineSegments.add(new Pair<>(lineNames.get(0), lineSegments));

            //Line2
            capacity = 20;
            lineSegments = new ArrayList<>();
            startStop = stopNames.get(1); //bratislava
            ArrayList<Time> timesForLine2 = new ArrayList<>();
            numOfPassengers = new HashMap<>();
            timesForLine2.add(new Time(2));
            timesForLine2.add(new Time(4));
            timesForLine2.add(new Time(6));
            timesForLine2.add(new Time(8));
            timesForLine2.add(new Time(10));

            nextStop = new Stop(stops.get(3).getStopName(), stops.get(3).getLines()); //martin
            for (Time time : timesForLine2) {
                numOfPassengers.put(time, 0);
            }

            lineSegments.add(new LineSegment(new TimeDiff(5), numOfPassengers, capacity, lineNames.get(1), nextStop));
            nextStop = new Stop(stops.get(5).getStopName(), stops.get(5).getLines()); //presov
            lineSegments.add(new LineSegment(new TimeDiff(5), numOfPassengers, capacity, lineNames.get(1), nextStop));

            lines.add(new Line(lineNames.get(1), startStop, lineSegments, timesForLine2));
            allLineSegments.add(new Pair<>(lineNames.get(1), lineSegments));

            //Line3
            capacity = 17;
            lineSegments = new ArrayList<>();
            startStop = stopNames.get(6); //brezno
            ArrayList<Time> timesForLine3 = new ArrayList<>();
            numOfPassengers = new HashMap<>();

            timesForLine3.add(new Time(1));
            timesForLine3.add(new Time(2));
            timesForLine3.add(new Time(3));
            timesForLine3.add(new Time(4));

            for (Time time : timesForLine3) {
                numOfPassengers.put(time, 0);
            }

            nextStop = new Stop(stops.get(4).getStopName(), stops.get(5).getLines());
            lineSegments.add(new LineSegment(new TimeDiff(2), numOfPassengers, capacity, lineNames.get(2), nextStop));

            lines.add(new Line(lineNames.get(2), startStop, lineSegments, timesForLine3));
            allLineSegments.add(new Pair<>(lineNames.get(2), lineSegments));
        }

    }

    public void createStopNames() {
        StopName trnava = new StopName("Trnava");
        StopName bratislava = new StopName("Bratislava");
        StopName kosice = new StopName("Kosice");
        StopName martin = new StopName("Martin");
        StopName partizanske = new StopName("Partizanske");
        StopName presov = new StopName("Presov");
        StopName brezno = new StopName("Brezno");
        stopNames.add(trnava);
        stopNames.add(bratislava);
        stopNames.add(kosice);
        stopNames.add(martin);
        stopNames.add(partizanske);
        stopNames.add(presov);
        stopNames.add(brezno);
    }

    public void createLineNames() {
        LineName lineName1 = new LineName("Line 1");
        LineName lineName2 = new LineName("Line 2");
        LineName lineName3 = new LineName("Line 3");
        lineNames.add(lineName1);
        lineNames.add(lineName2);
        lineNames.add(lineName3);
    }

    @Override
    public List<LineInterface> readLines() {
        return lines;
    }

    @Override
    public List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> readLinesAsQuadruplets() {
        //saved them in List of LineInterfaces
        List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> linesToReturn = new ArrayList<>();
        for (LineInterface line : lines) {
            linesToReturn.add(line.convertToQuadruplet());
        }
        return linesToReturn;
    }

    @Override
    public List<StopInterface> readStops() {
        //saved them already as Pairs of StopName and List of Linenames
        return stops;
    }

    @Override
    public List<Pair<StopName, List<LineName>>> readStopsAsPairs() {
        List<Pair<StopName, List<LineName>>> listToReturn = new ArrayList<>();
        for (StopInterface stop : stops) {
            listToReturn.add(stop.convertToPair());
        }
        return listToReturn;
    }

    @Override
    public List<Pair<LineName, List<LineSegmentInterface>>> readSegments() {
        return allLineSegments;
    }

    @Override
    public List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> readSegmentsAsQuintuplets() {
        List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> listToReturn = new ArrayList<>();
        for (LineSegmentInterface segment : lineSegments) {
            listToReturn.add(segment.convertToQuintuplet());
        }
        return listToReturn;
    }

}