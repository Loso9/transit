package exceptions;

import datatypes.*;

public class AlreadyLoadedStopException extends Exception {

    private final StopName stopName;

    public AlreadyLoadedStopException() {
        this.stopName = null;
    }

    public AlreadyLoadedStopException(StopName stopName) {
        this.stopName = stopName;
    }

    @Override
    public String getMessage() {
        if (stopName == null) {
            return "You have already loaded this stop.";
        }
        return "You have already loaded stop with name \"" + stopName.getName() + "\".";
    }
}
