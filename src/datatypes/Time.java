package datatypes;

import java.util.Objects;

public class Time {

    private final int time;

    public Time(Integer time) {
        if (time < 0) {
            throw new IllegalArgumentException("Time should not be negative.");
        }
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time1 = (Time) o;
        return time == time1.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.valueOf(time);
    }
}
