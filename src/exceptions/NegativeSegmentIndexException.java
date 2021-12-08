package exceptions;

public class NegativeSegmentIndexException extends Exception {

    public NegativeSegmentIndexException() { /* empty, not needed */ }

    @Override
    public String getMessage() {
        return "Segment index cannot be negative";
    }
}
