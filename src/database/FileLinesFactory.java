package database;

import datatypes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import exceptions.NegativeCapacityException;
import main.*;

public class FileLinesFactory implements LinesFactoryInterface {

    private final List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> lines;
    private final List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> lineSegments;
    //for each line add list of linesegments
    Scanner lineFileScanner;
    private StopsFactoryInterface stopsFactory;
    private Time firstTimeDiff = new Time(0);

    public FileLinesFactory(StopsFactoryInterface stopsFactory) {
        this.stopsFactory = stopsFactory;
        this.lines = new ArrayList<>();
        this.lineSegments = new ArrayList<>();
        this.lineFileScanner = null;
    }

    @Override
    public Optional<LineInterface> createLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException {
        Integer index = checkIfContainsLine(lineName);
        if (index != null) {
            return Optional.of(new Line(lineName, lines.get(index).getSecond(), lines.get(index).getThird(), lines.get(index).getForth()));
        }
        //look for the line in "Lines.txt"
        Line newLine;
        File linesFile = new File("Lines.txt");
        Scanner linesScanner = new Scanner(linesFile);
        while (linesScanner.hasNext()) {
            String nextString = linesScanner.next();
            if (nextString.equals(lineName.getName())) { //Line exists in Lines.txt
                File concreteFileLine = new File(nextString + ".txt"); //open Line file
                this.lineFileScanner = new Scanner(concreteFileLine);
                //create segments and line
                StopName startStop;
                StopInterface nextStop;
                List<LineSegmentInterface> segments = new ArrayList<>();
                List<Time> startingTimes = new ArrayList<>();
                int capacity = this.lineFileScanner.nextInt(); //should read capicity first with defined file format
                String[] headerArray = this.lineFileScanner.nextLine().split("\\s+");
                startStop = new StopName(headerArray[0]);
                for (int i = 2; i < headerArray.length; i++) { //first index "-"
                    startingTimes.add(new Time(Integer.parseInt(headerArray[i])));
                }
                for (int i = 0; this.lineFileScanner.hasNext(); i++) {
                    Optional<LineSegmentInterface> lineSegment = createLineSegment(lineName, i, capacity);
                    lineSegment.ifPresent(segments::add);
                }
                newLine = new Line(lineName, startStop, segments, startingTimes);
                lines.add(newLine.convertToQuadruplet());
                List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> segmentsToAdd = new ArrayList<>();
                for (LineSegmentInterface segment : segments) {
                    segmentsToAdd.add(segment.convertToQuintuplet());
                }
                lineSegments.add(new Pair<>(lineName, segmentsToAdd));
                this.lineFileScanner.close();
                return Optional.of(newLine);
            }

        }
        return Optional.empty();
    }


    @Override
    public Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex, int capacity) throws NegativeCapacityException, FileNotFoundException {
        boolean exists = checkIfContainsSegment(lineName, segmentIndex);
        List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>> listToFind = null;
        if (exists) {//already loaded
            for (Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>> pair : lineSegments) {
                if (pair.getFirst().equals(lineName)) {
                    listToFind = pair.getSecond();
                }
            }
            assert listToFind != null;
            return Optional.of(new LineSegment(listToFind.get(segmentIndex).getFirst(),
                    listToFind.get(segmentIndex).getSecond(),
                    listToFind.get(segmentIndex).getThird(),
                    listToFind.get(segmentIndex).getForth(),
                    listToFind.get(segmentIndex).getFifth()));

        }
        while (lineFileScanner.hasNextLine()) {
            Scanner rowScanner = new Scanner(lineFileScanner.nextLine());
            Optional<StopInterface> nextStop = stopsFactory.createStop(new StopName(rowScanner.next()));
            Time time = new Time(rowScanner.nextInt());
            /* NOT FINISHED, have to think about this more //TODO */
        }
        return Optional.empty();
    }

    @Override
    public List<Quadruplet<LineName, StopName, List<LineSegmentInterface>, List<Time>>> getLines() {
        return lines;
    }

    @Override
    public List<Pair<LineName, List<Quintuplet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopInterface>>>> getSegments() {
        return lineSegments;
    }
}
