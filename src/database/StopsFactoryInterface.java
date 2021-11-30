package database;

import datatypes.*;
import main.*;

import java.io.FileNotFoundException;
import java.util.*;

public interface StopsFactoryInterface {
    Optional<StopInterface> createStop(StopName stopName) throws FileNotFoundException;
}
