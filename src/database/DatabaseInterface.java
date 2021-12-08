package database;

import java.util.*;
import datatypes.LineName;
import datatypes.StopName;
import main.LineSegmentInterface;
import main.*;

public interface DatabaseInterface {
    List<LineName> readLineNames();
    List<StopName> readStopNames();
    List<LineInterface> readLines();
    List<StopInterface> readStops();
    List<LineSegmentInterface> readSegments();

}
