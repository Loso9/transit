package datatypes;

public class FullBusException extends Exception {

    public FullBusException() { /* empty, not needed */ }

    @Override
    public String getMessage() {
        return "Bus is already full, new passengers cannot enter the bus.";
    }
}
