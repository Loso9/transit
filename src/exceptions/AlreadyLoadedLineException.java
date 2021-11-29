package exceptions;

import datatypes.*;

public class AlreadyLoadedLineException extends Exception {

    private final LineName lineName;

    public AlreadyLoadedLineException() {
        this.lineName = null;
    }

    public AlreadyLoadedLineException(LineName lineName) {
        this.lineName = lineName;
    }

    @Override
    public String getMessage() {
        if (lineName == null) {
            return "This line has already been loaded.";
        }
        return "Line \"" + lineName.getName() + "\" has already been loaded.";
    }
}
