package datatypes;

public class NegativeCapacityException extends Exception {

    public NegativeCapacityException() { /* empty, not needed */ }

    @Override
    public String getMessage() {
        return "Capacity of the bus cannot be negative";
    }
}
