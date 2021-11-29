package exceptions;

import datatypes.*;

public class NotLoadedStopException extends Exception {

    private StopName stopName;

    public NotLoadedStopException() {
        this.stopName = null;
    }

    public NotLoadedStopException(StopName stopName) {
        this.stopName = stopName;
    }

    @Override
    public String getMessage() {
        if (stopName == null) {
            return "This stop has not been loaded yet.";
        }
        return "Stop with name \"" + stopName.getName() + "\" has not been loaded yet.";
    }
}
