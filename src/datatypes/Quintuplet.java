package datatypes;

import java.util.Objects;

public class Quintuplet<P, Q, R, S, T> {

    private P first;
    private Q second;
    private R third;
    private S forth;
    private T fifth;

    public Quintuplet(P first, Q second, R third, S forth, T fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
        this.fifth = fifth;
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

    public T getFifth() {
        return fifth;
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

    public void setFifth(T fifth) {
        this.fifth = fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quintuplet<?, ?, ?, ?, ?> that = (Quintuplet<?, ?, ?, ?, ?>) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second) && Objects.equals(third, that.third) && Objects.equals(forth, that.forth) && Objects.equals(fifth, that.fifth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, forth, fifth);
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "," + second.toString() + "," + third.toString() + "," + forth.toString() + "," + fifth.toString() + ")";
    }
}