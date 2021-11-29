package datatypes;

public class NoSuchLineException extends Exception {

    private final LineName lineName;

    public NoSuchLineException() {
        lineName = null;
    }

    public NoSuchLineException(LineName lineName) {
        this.lineName = lineName;
    }

    @Override
    public String getMessage() {
        if (lineName == null) {
            return "No such line exists.";
        }
        else return "No such line with name \"" + lineName.getName() + "\" exists.";
    }
}
