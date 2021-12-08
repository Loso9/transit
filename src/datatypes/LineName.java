package datatypes;

import java.util.Objects;

public class LineName {

    private final String name;

    public LineName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Linename should not be null.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineName lineName = (LineName) o;
        return Objects.equals(name, lineName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }
}
