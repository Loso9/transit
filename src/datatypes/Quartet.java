package datatypes;

import java.util.Objects;

public class Quartet<P, R, Q, S> {

    private P first;
    private R second;
    private Q third;
    private S forth;

    public Quartet(P first, R second, Q third, S forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    public P getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    public Q getThird() {
        return third;
    }

    public S getForth() {
        return forth;
    }

    public void setFirst(P first) {
        this.first = first;
    }

    public void setSecond(R second) {
        this.second = second;
    }

    public void setThird(Q third) {
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
        return "(" + first + "," + second + "," + third + "," + forth + ")";
    }
}
