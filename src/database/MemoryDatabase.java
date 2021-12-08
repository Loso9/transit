package database;

import datatypes.*;
import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;
import main.StopInterface;

import java.util.*;

public class MemoryDatabase implements DatabaseInterface {

    private final List<LineInterface> lines;
    private final List<LineName> lineNames;
    private final List<StopInterface> stops;
    private final List<StopName> stopNames;
    private final List<LineSegmentInterface> allLineSegments;
    public MemoryDatabase() throws NegativeCapacityException, NegativeSegmentIndexException {
        stops = new ArrayList<>();
        allLineSegments = new ArrayList<>();
        lines = new ArrayList<>();
        stopNames = new ArrayList<>();
        lineNames = new ArrayList<>();

        //*******STOPNAMES START*******//

        StopName trnava = new StopName("Trnava");
        StopName bratislava = new StopName("Bratislava");
        StopName kosice = new StopName("Kosice");
        StopName martin = new StopName("Martin");
        StopName presov = new StopName("Presov");
        StopName brezno = new StopName("Brezno");
        StopName partizanske = new StopName("Partizanske");

        stopNames.add(trnava);
        stopNames.add(bratislava);
        stopNames.add(kosice);
        stopNames.add(martin);
        stopNames.add(presov);
        stopNames.add(brezno);
        stopNames.add(partizanske);

        //*******STOPNAMES DONE*******//

        //*******LINENAMES START*******//

        LineName lineName1 = new LineName("Line1");
        LineName lineName2 = new LineName("Line2");
        LineName lineName3 = new LineName("Line3");

        lineNames.add(lineName1);
        lineNames.add(lineName2);
        lineNames.add(lineName3);

        //*******LINENAMES DONE*******//

        //*******STOPS START*******//

        //adding stops with stopNames and lines

        //Trnava
        List<LineName> trnavaLineNames = new ArrayList<>();
        trnavaLineNames.add(lineName1);
        StopInterface trnavaStop = new Stop(trnava, trnavaLineNames);
        stops.add(trnavaStop);

        //Bratislava
        List<LineName> bratislavaLineNames = new ArrayList<>();
        bratislavaLineNames.add(lineName1);
        bratislavaLineNames.add(lineName2);
        StopInterface bratislavaStop = new Stop(bratislava, bratislavaLineNames);
        stops.add(bratislavaStop);

        //Kosice
        List<LineName> kosiceLineNames = new ArrayList<>();
        kosiceLineNames.add(lineName1);
        StopInterface kosiceStop = new Stop(kosice, kosiceLineNames);
        stops.add(kosiceStop);

        //Martin
        List<LineName> martinLineNames = new ArrayList<>();
        martinLineNames.add(lineName2);
        StopInterface martinStop = new Stop(martin, martinLineNames);
        stops.add(martinStop);

        //Presov
        List<LineName> presovLineNames = new ArrayList<>();
        StopInterface presovStop = new Stop(presov, presovLineNames);
        stops.add(presovStop);

        //Brezno
        List<LineName> breznoLines = new ArrayList<>();
        breznoLines.add(lineName3);
        StopInterface breznoStop = new Stop(brezno, breznoLines);
        stops.add(breznoStop);

        //Partizanske
        List<LineName> partizanskeLines = new ArrayList<>();
        StopInterface partizanskeStop = new Stop(partizanske, partizanskeLines);
        stops.add(partizanskeStop);

        //*******STOPS DONE*******//

        //**********LINES START**********//

        //*******LINE1 START*******//

        int capacity;
        List<LineSegmentInterface> lineSegmentsForLine1 = new ArrayList<>();
        capacity = 15;
        List<Time> timesForLine1 = new ArrayList<>();

        timesForLine1.add(new Time(5));
        timesForLine1.add(new Time(8));
        timesForLine1.add(new Time(9));
        timesForLine1.add(new Time(10));

        //First linesegment Trnava - Bratislava
        TimeDiff timeDiffToBratislava = new TimeDiff(11);
        Map<Time, Integer> numOfPassengersTrnavaBratislava = new HashMap<>();
        for (Time time : timesForLine1) {
            Time newTime = new Time(time.getTime() + timeDiffToBratislava.getTimeDiff());
            numOfPassengersTrnavaBratislava.put(newTime, 0);
        }
        LineSegmentInterface trnavaBratislava = new LineSegment(timeDiffToBratislava, numOfPassengersTrnavaBratislava, capacity, lineName1, bratislavaStop, 0);
        lineSegmentsForLine1.add(trnavaBratislava);

        //Second lineSegment Bratislava - Kosice
        TimeDiff timeDiffToKosice = new TimeDiff(13);
        Map<Time, Integer> numOfPassengersBratislavaKosice = new HashMap<>();
        for (Time time : timesForLine1) {
            Time newTime = new Time(time.getTime() + timeDiffToBratislava.getTimeDiff() + timeDiffToKosice.getTimeDiff());
            numOfPassengersBratislavaKosice.put(newTime, 0);
        }
        LineSegmentInterface bratislavaKosice = new LineSegment(timeDiffToKosice, numOfPassengersBratislavaKosice, capacity, lineName1, kosiceStop, 1);
        lineSegmentsForLine1.add(bratislavaKosice);

        LineInterface line1 = new Line(lineName1, trnava, lineSegmentsForLine1, timesForLine1);
        lines.add(line1);
        allLineSegments.addAll(lineSegmentsForLine1);

        //*******LINE1 DONE*******//

        //*******LINE2 START*******//

        capacity = 20;
        List<LineSegmentInterface> lineSegmentsForLine2 = new ArrayList<>();
        List<Time> timesForLine2 = new ArrayList<>();
        timesForLine2.add(new Time(2));
        timesForLine2.add(new Time(4));
        timesForLine2.add(new Time(6));
        timesForLine2.add(new Time(8));
        timesForLine2.add(new Time(10));

        //1st linesegment Bratislava - Martin
        TimeDiff timeDiffToMartin = new TimeDiff(5);
        Map<Time, Integer> numOfPassengersBratislavaMartin = new HashMap<>();
        for (Time time : timesForLine2) {
            Time newTime = new Time(time.getTime() + timeDiffToMartin.getTimeDiff());
            numOfPassengersBratislavaMartin.put(newTime, 0);
        }

        LineSegmentInterface bratislavaMartin = new LineSegment(timeDiffToMartin, numOfPassengersBratislavaMartin, capacity, lineName2, martinStop, 0);
        lineSegmentsForLine2.add(bratislavaMartin);

        //2nd linesegment Martin - Presov
        TimeDiff timeDiffToPresov = new TimeDiff(3);
        Map<Time, Integer> numOfPassengersMartinPresov = new HashMap<>();
        for (Time time : timesForLine2) {
            Time newTime = new Time(time.getTime() + timeDiffToMartin.getTimeDiff() + timeDiffToPresov.getTimeDiff());
            numOfPassengersMartinPresov.put(newTime, 0);
        }

        LineSegmentInterface martinPresov = new LineSegment(timeDiffToPresov, numOfPassengersMartinPresov, capacity, lineName2, presovStop, 1);
        lineSegmentsForLine2.add(martinPresov);

        LineInterface line2 = new Line(lineName2, bratislava, lineSegmentsForLine2, timesForLine2);
        lines.add(line2);
        allLineSegments.addAll(lineSegmentsForLine2);

        //*******LINE2 DONE*******//

        //*******LINE3 START*******//

        capacity = 17;
        List<LineSegmentInterface> lineSegmentsForLine3 = new ArrayList<>();
        ArrayList<Time> timesForLine3 = new ArrayList<>();
        timesForLine3.add(new Time(1));
        timesForLine3.add(new Time(2));
        timesForLine3.add(new Time(3));
        timesForLine3.add(new Time(4));

        //1st and only linesegment Brezno - Partizanske
        TimeDiff timeDiffToPartizanske = new TimeDiff(2);
        Map<Time, Integer> numOfPassengersBreznoPartizanske = new HashMap<>();
        for (Time time : timesForLine3) {
            Time newTime = new Time(time.getTime() + timeDiffToPartizanske.getTimeDiff());
            numOfPassengersBreznoPartizanske.put(newTime, 0);
        }

        LineSegmentInterface breznoPartizanske = new LineSegment(timeDiffToPartizanske, numOfPassengersBreznoPartizanske, capacity, lineName3, partizanskeStop, 0);
        lineSegmentsForLine3.add(breznoPartizanske);

        Line line3 = new Line(lineName3, brezno, lineSegmentsForLine3, timesForLine3);
        lines.add(line3);
        allLineSegments.addAll(lineSegmentsForLine3);

        //*******LINE3 DONE*******//

        //*******LINES DONE*******//
    }

    @Override
    public List<LineName> readLineNames() {
        return Collections.unmodifiableList(lineNames);
    }

    @Override
    public List<StopName> readStopNames() {
        return Collections.unmodifiableList(stopNames);
    }

    @Override
    public List<LineInterface> readLines() {
        return Collections.unmodifiableList(lines);
    }

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
        return Collections.unmodifiableList(stops);
    }

    public List<Pair<StopName, List<LineName>>> readStopsAsPairs() {
        List<Pair<StopName, List<LineName>>> listToReturn = new ArrayList<>();
        for (StopInterface stop : stops) {
            listToReturn.add(stop.convertToPair());
        }
        return listToReturn;
    }

    @Override
    public List<LineSegmentInterface> readSegments() {
        return Collections.unmodifiableList(allLineSegments);
    }

    public List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> readSegmentsAsQuintuplets() {
        List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> listToReturn = new ArrayList<>();
        for (LineSegmentInterface segment : allLineSegments) {
            listToReturn.add(segment.convertToQuintuplet());
        }
        return listToReturn;
    }

}
