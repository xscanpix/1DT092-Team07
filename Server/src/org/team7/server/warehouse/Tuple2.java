package org.team7.server.warehouse;

public class Tuple2<T> {

    private T first;
    private T second;

    public Tuple2(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T value) {
        first = value;
    }

    public void setSecond(T value) {
        second = value;
    }
}
