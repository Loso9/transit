package datatypes;

import java.util.Objects;

public class TimeDiff {

    private final int timeDiff;

    public TimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
    }

    public TimeDiff(Time time1, Time time2) {
        timeDiff = time1.getTime() - time2.getTime();
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
