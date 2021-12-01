package datatypes;

import java.util.*;

public class StopName {

    private final String name;

    public StopName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StopName stopName = (StopName) o;
        return Objects.equals(name, stopName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}