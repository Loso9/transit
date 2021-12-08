package database;

import datatypes.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import exceptions.NegativeCapacityException;
import exceptions.NegativeSegmentIndexException;
import main.*;

public class FileLinesFactory implements LinesFactoryInterface {

    private final List<LineInterface> lines;
    private final List<LineSegmentInterface> lineSegments;
    private final StopsFactoryInterface stopsFactory;

    public FileLinesFactory(StopsFactoryInterface stopsFactory) {
        this.stopsFactory = stopsFactory;
        this.lines = new ArrayList<>();
        this.lineSegments = new ArrayList<>();
    }

    @Override
    public Optional<LineInterface> createLine(LineName lineName) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException {
        if (lineName == null) {
            throw new NullPointerException();
        }
        Integer index = checkIfContainsLine(lineName); //is line already loaded in lines list
        if (index != null) {
            return Optional.of(new Line(lineName, lines.get(index).getFirstStop(), lines.get(index).getLineSegments(), lines.get(index).getStartingTimes()));
        }
        //look for the line in "Lines.txt"
        File linesFile = new File("src/database/Lines.txt");
        Scanner linesScanner = new Scanner(linesFile);
        while (linesScanner.hasNext()) {
            String nextString = linesScanner.next();
            if (nextString.equals(lineName.getName())) { //Line exists in Lines.txt
                File concreteFileLine = new File("src/database/" + nextString + ".txt"); //open Line file
                Scanner lineFileScanner = new Scanner(concreteFileLine);
                //create segments and line
                StopName startStop;
                List<LineSegmentInterface> segmentsForLine = new ArrayList<>();
                List<Time> startingTimes = new ArrayList<>();
                Scanner headerScanner = new Scanner(lineFileScanner.nextLine());

                //String[] headerArray = this.lineFileScanner.nextLine().split("\\s+");
                //startStop = new StopName(headerArray[0]);
                startStop = new StopName(headerScanner.next()); //read firststop
                /*
                for (int i = 2; i < headerArray.length; i++) { //first index "-"
                    startingTimes.add(new Time(Integer.parseInt(headerArray[i])));
                }
                 */
                while (headerScanner.hasNext()) {
                    startingTimes.add(new Time(headerScanner.nextInt())); //read startingtimes
                }
                TimeDiff totalTimeDiff = new TimeDiff(0);
                for (int i = 0; lineFileScanner.hasNextLine(); i++) {
                    if (checkIfContainsSegment(lineName, i)) { //it already has segment loaded
                        for (LineSegmentInterface segment : lineSegments) { //look for segment
                            if (segment.getLineName().equals(lineName) && segment.getSegmentIndex() == i) {
                                segmentsForLine.add(segment);
                                totalTimeDiff = new TimeDiff(totalTimeDiff.getTimeDiff() + segment.getTimeToNextStop().getTimeDiff());
                            }
                        }
                        //move scanner to nextLine
                        lineFileScanner.nextLine();
                        continue;
                    }
                    Scanner segmentReader = new Scanner(lineFileScanner.nextLine());
                    Optional<StopInterface> nextStop = stopsFactory.createStop(new StopName(segmentReader.next()));
                    TimeDiff timeToNextStop = new TimeDiff(segmentReader.nextInt());
                    totalTimeDiff = new TimeDiff(totalTimeDiff.getTimeDiff() + timeToNextStop.getTimeDiff());
                    int capacity = segmentReader.nextInt();
                    Map<Time, Integer> numOfPassengers = new HashMap<>();
                    for (Time startTime : startingTimes) {
                        Time newTime = new Time(startTime.getTime() + totalTimeDiff.getTimeDiff());
                        numOfPassengers.put(newTime, segmentReader.nextInt());
                    }
                    LineSegmentInterface lineSegment = new LineSegment(timeToNextStop, numOfPassengers, capacity, lineName, nextStop.get(), i);
                    segmentsForLine.add(lineSegment);
                    lineSegments.add(lineSegment);
                }
                Optional<LineInterface> newLine = Optional.of(new Line(lineName, startStop, segmentsForLine, startingTimes));
                newLine.ifPresent(lines::add);
                lineFileScanner.close();
                return newLine;
            }
        }
        linesScanner.close();
        return Optional.empty();
    }


    @Override
    public Optional<LineSegmentInterface> createLineSegment(LineName lineName, int segmentIndex) throws FileNotFoundException, NegativeCapacityException, NegativeSegmentIndexException {
        if (lineName == null) {
            throw new NullPointerException();
        }
        if (segmentIndex < 0) {
            throw new NegativeSegmentIndexException();
        }
        boolean segmentExists = checkIfContainsSegment(lineName, segmentIndex);
        if (segmentExists) { // segment already loaded
            for (LineSegmentInterface segment : lineSegments) { //look for segment
                if (segment.getLineName().equals(lineName) && segment.getSegmentIndex() == segmentIndex) {
                    return Optional.of(segment);
                }
            }

        }
        //look for the line in "Lines.txt"
        File linesFile = new File("src/database/Lines.txt");
        Scanner linesScanner = new Scanner(linesFile);
        while (linesScanner.hasNext()) {
            String nextString = linesScanner.next();
            if (nextString.equals(lineName.getName())) { //Line exists in Lines.txt
                File concreteFileLine = new File("src/database/" + nextString + ".txt"); //open Line file
                Scanner lineScanner = new Scanner(concreteFileLine);
                //need to read startingTimes and init timediff
                TimeDiff totalTimeDiff = new TimeDiff(0);
                List<Time> startingTimes = new ArrayList<>();
                Scanner headerScanner = new Scanner(lineScanner.nextLine()); //scanner on first line
                //skip firststop
                headerScanner.next();
                for (int i = 1; headerScanner.hasNext(); i++) {
                    startingTimes.add(new Time(headerScanner.nextInt()));
                }
                int lineCounter = 0;
                while ((lineCounter + 1) != segmentIndex) { //until we are not on the correct line, I will only read timediff
                    Scanner rowScanner = new Scanner(lineScanner.nextLine());
                    rowScanner.next(); //ignore stop
                    totalTimeDiff = new TimeDiff(totalTimeDiff.getTimeDiff() + rowScanner.nextInt());
                    lineCounter++;
                }
                //we are on the correct line
                Optional<StopInterface> nextStop = stopsFactory.createStop(new StopName(lineScanner.next()));
                TimeDiff timeToNextStop = new TimeDiff(lineScanner.nextInt());
                totalTimeDiff = new TimeDiff(totalTimeDiff.getTimeDiff() + timeToNextStop.getTimeDiff());
                int capacity = lineScanner.nextInt();
                Map<Time, Integer> numOfPassengers = new HashMap<>();
                for (Time startTime : startingTimes) {
                    Time newTime = new Time(startTime.getTime() + totalTimeDiff.getTimeDiff());
                    numOfPassengers.put(newTime, linesScanner.nextInt());
                }
                Optional<LineSegmentInterface> lineSegmentToReturn = Optional.of(new LineSegment(timeToNextStop, numOfPassengers, capacity, lineName, nextStop.get(), segmentIndex));
                lineSegments.add(lineSegmentToReturn.get());
                return lineSegmentToReturn;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<LineInterface> getLines() {
        return lines;
    }

    @Override
    public List<LineSegmentInterface> getSegments() {
        return lineSegments;
    }

    //have not found a good way to update single point (x,y) in text file other than deleting whole content and putting it back
    @Override
    public void updateDatabase(List<LineSegmentInterface> updatedSegments) throws FileNotFoundException {
        List<LineSegmentInterface> segmentsToBeRemoved = new ArrayList<>();
        List<LineSegmentInterface> segmentsToBeUpdated = new ArrayList<>();
        for (LineSegmentInterface lineSegment : updatedSegments) {
            //inner update
            if (checkIfContainsSegment(lineSegment.getLineName(), lineSegment.getSegmentIndex())) { //if contains remove old one and put updated one
                for (LineSegmentInterface oldSegment : lineSegments) { //pinpoint the oldSegment which needs to be updated
                    if (oldSegment.getLineName().equals(lineSegment.getLineName()) && oldSegment.getSegmentIndex() == lineSegment.getSegmentIndex()) {
                        segmentsToBeRemoved.add(oldSegment);
                        segmentsToBeUpdated.add(lineSegment);
                    }
                    break;
                }
            }
            //if updated segment isnt loaded yet, simply add him to addLineSegments
            else {
                lineSegments.add(lineSegment);
            }
        }

        lineSegments.removeAll(segmentsToBeRemoved);
        lineSegments.addAll(segmentsToBeUpdated);
        //get files which need to be updated
        Map<LineName, List<LineSegmentInterface>> linesToBeUpdated = new HashMap<>();
        for (LineInterface line : lines) {
            List<LineSegmentInterface> lineSegmentsForLine = new ArrayList<>();
            for (LineSegmentInterface segment : updatedSegments) {
                if (line.getLineName().equals(segment.getLineName())) {
                    lineSegmentsForLine.add(segment);
                }
            }
            linesToBeUpdated.put(line.getLineName(), lineSegmentsForLine);
        }

        //update all files that need to be updated
        printToFile(linesToBeUpdated);
    }

    //fileName in this case is "lineName(number)"
    private static List<String> readFile(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            throw new NullPointerException();
        }
        List<String> fileRows = new ArrayList<>();
        File fileToRead = new File("src/database/" + fileName + ".txt");
        Scanner fileScanner = new Scanner(fileToRead);
        while (fileScanner.hasNextLine()) {
            fileRows.add(fileScanner.nextLine());
        }
        fileScanner.close();
        return fileRows;
    }

    private static void printToFile(Map<LineName, List<LineSegmentInterface>> linesToGetUpdate) throws FileNotFoundException {
        for (LineName lineName : linesToGetUpdate.keySet()) {
            if (lineName == null) {
                throw new NullPointerException();
            }
        }
        //according to my format
        for (LineName lineName : linesToGetUpdate.keySet()) {
            LinkedList<LineSegmentInterface> lineSegmentsForLineName = new LinkedList<>(linesToGetUpdate.get(lineName));
            lineSegmentsForLineName.sort(Comparator.comparingInt(LineSegmentInterface::getSegmentIndex)); //sort updated segments according to segmentIndex
            List<String> rows = readFile(lineName.getName());
            PrintStream outputToFile = new PrintStream("src/database/" + lineName.getName() + ".txt");
            //header wont be changed
            outputToFile.print(rows.get(0));
            //check if segment needs to be new or can copy from rowsList
            for (int i = 1; i < rows.size(); i++) {
                //first to find is 0-th index (smallest segmentIndex)
                if (i != lineSegmentsForLineName.get(0).getSegmentIndex()) { //if segmentIndices dont match, then the lineSegment wasnt updated, hence we can print row
                    outputToFile.print(rows.get(i));
                }
                else {
                    //format
                    StringBuilder lineSegmentString = new StringBuilder();
                    lineSegmentString.append(lineSegmentsForLineName.get(0).getNextStop().getStopName().getName());
                    lineSegmentString.append("\t");
                    lineSegmentString.append(lineSegmentsForLineName.get(0).getTimeToNextStop().getTimeDiff());
                    lineSegmentString.append("\t");
                    lineSegmentString.append(lineSegmentsForLineName.get(0).getCapacity());
                    lineSegmentString.append("\t");
                    int numOfEntries = lineSegmentsForLineName.get(0).getBuses().size();
                    int counter = 1;
                    for (Map.Entry<Time, Integer> entry : lineSegmentsForLineName.get(0).getBuses().entrySet()) {
                        if (counter == numOfEntries) { //last number from numOfPassengers
                            lineSegmentString.append(entry.getValue());
                        }
                        else {
                            lineSegmentString.append(entry.getValue());
                            lineSegmentString.append("\t");
                        }
                        counter++;
                    }
                    lineSegmentsForLineName.removeFirst();
                    outputToFile.println(lineSegmentString);
                }
            }
            outputToFile.close();
        }
    }

    //not used
    //return pair - first boolean whether they are the
    private static boolean lineSegmentCheckSame(String row, int rowNumber, LineSegmentInterface lineSegment) {
        if (row == null || lineSegment == null) {
            throw new NullPointerException();
        }
        Scanner rowScanner = new Scanner(row);
        String nextStop = rowScanner.next();
        //check if first string is nextStop of lineSegment
        if (!nextStop.equals(lineSegment.getNextStop().getStopName().getName())) return false;
        int timeDiff = rowScanner.nextInt();
        //check if first integer is timeDiff of lineSegment
        if (timeDiff != lineSegment.getTimeToNextStop().getTimeDiff()) return false;
        int capacity = rowScanner.nextInt();
        //check if second integer is capacity of linesegment
        if (capacity != lineSegment.getCapacity()) return false;
        List<Integer> passengers = new ArrayList<>();
        while (rowScanner.hasNextInt()) {
            passengers.add(rowScanner.nextInt());
        }
        //check if map (numOfIntegers) has same amount of keys as lineSegment numOfPassengers
        if (passengers.size() != lineSegment.getBuses().keySet().size()) {
            return false;
        }
        if ((rowNumber + 1) != lineSegment.getSegmentIndex()) return false;
        //check if passengers on each time are same
        return passengers.equals(lineSegment.getBuses().values());
    }

}
