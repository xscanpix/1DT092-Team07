package warehouse;

import javafx.util.Pair;

public class WarehousePath {
    private final Pair<Tuple2<Integer>, Tuple2<Integer>> pair;

    public WarehousePath(Tuple2<Integer> start, Tuple2<Integer> end) {
        pair = new Pair<>(start, end);
    }

    public Tuple2<Integer> getFirst() {
        return pair.getKey();
    }

    public Tuple2<Integer> getSecond() {
        return pair.getValue();
    }

    public Pair<Tuple2<Integer>, Tuple2<Integer>> getPair() {
        return pair;
    }
}
