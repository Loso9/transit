package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import datatypes.*;
import main.*;

public class FileStopsFactory implements StopsFactoryInterface {

    private final List<Pair<StopName, List<LineName>>> stops;

    public FileStopsFactory() {
        stops = new ArrayList<>();
    }

    @Override
    public Optional<StopInterface> createStop(StopName stopName) throws FileNotFoundException {
        Integer index = checkIfContains(stopName); //check if its not already loaded in stops
        if (index != null) { //it is already loaded, index is not null
            return Optional.of(new Stop(stopName, stops.get(index).getSecond()));
        }
        //look for the stopName in Stops file
        File stopsFile = new File("Stops.txt");
        Scanner fileScanner = new Scanner(stopsFile);
        ArrayList<LineName> stopLines = new ArrayList<>();
        boolean found = false;
        while (fileScanner.hasNextLine()) {
            String[] nextLine = fileScanner.nextLine().split("\\s+");
            if (nextLine[0].equals(stopName.getName())) {
                found = true;
                for (int i = 1; i < nextLine.length; i++) {
                    stopLines.add(new LineName(nextLine[i]));
                }
            }
        }
        //if it was found, return stop with according lines
        if (found) {
            Stop newStop = new Stop(stopName, stopLines);
            stops.add(new Pair<>(stopName, stopLines));
            return Optional.of(newStop);
        } //return empty
        return Optional.empty();
    }

    private Integer checkIfContains(StopName stopName) {
        int index = 0;
        for (Pair<StopName, List<LineName>> stop : stops) {
            if (stop.getFirst().equals(stopName)) {
                return index;
            }
            index++;
        }
        return null;
    }

    public List<Pair<StopName, List<LineName>>> getCurrentLoadedStops() {
        return stops;
    }
}
