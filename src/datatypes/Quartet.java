package datatypes;

import java.util.Objects;

public class Quartet<P, Q, R, S> {

    private P first;
    private Q second;
    private R third;
    private S forth;

    public Quartet(P first, Q second, R third, S forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    public P getFirst() {
        return first;
    }

    public Q getSecond() {
        return second;
    }

    public R getThird() {
        return third;
    }

    public S getForth() {
        return forth;
    }

    public void setFirst(P first) {
        this.first = first;
    }

    public void setSecond(Q second) {
        this.second = second;
    }

    public void setThird(R third) {
        this.third = third;
    }

    public void setForth(S forth) {
        this.forth = forth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quartet<?, ?, ?, ?> quartet = (Quartet<?, ?, ?, ?>) o;
        return Objects.equals(first, quartet.first) && Objects.equals(second, quartet.second) && Objects.equals(third, quartet.third) && Objects.equals(forth, quartet.forth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, forth);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "," + second.toString() + "," + third.toString() + "," + forth.toString() + ")";
    }
}
