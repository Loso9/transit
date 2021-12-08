package datatypes;

import java.util.Objects;

public class TimeDiff {

    private final int timeDiff;

    public TimeDiff(Integer timeDiff) {
        if (timeDiff < 0) {
            throw new IllegalArgumentException("TimeDiff should not be negative.");
        }
        this.timeDiff = timeDiff;
    }

    public TimeDiff(Time time1, Time time2) {
        if (Objects.requireNonNull(time1).getTime() < 0 || Objects.requireNonNull(time2).getTime() < 0) {
            throw new IllegalArgumentException("TimeDiff should not be calculated with null values or with negative times.");
        }
        if (time1.getTime() < time2.getTime()) {
            timeDiff = time2.getTime() - time1.getTime();
        }
        else {
            timeDiff = time1.getTime() - time2.getTime();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDiff timeDiff1 = (TimeDiff) o;
        return timeDiff == timeDiff1.timeDiff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeDiff);
    }

    public int getTimeDiff() {
        return timeDiff;
    }

    @Override
    public String toString() {
        return String.valueOf(timeDiff);
    }
}
