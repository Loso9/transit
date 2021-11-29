package exceptions;

import datatypes.*;

public class NotLoadedLineException extends Exception {

    private final LineName lineName;

    public NotLoadedLineException() {
        this.lineName = null;
    }

    public NotLoadedLineException(LineName lineName) {
        this.lineName = lineName;
    }

    @Override
    public String getMessage() {
        if (lineName == null) {
            return "This line has not been loaded yet.";
        }
        return "Line \"" + lineName.getName() + "\" has not been loaded yet.";
    }
}
