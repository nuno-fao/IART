package graph;

import board.ProvState;
import board.State;

import java.util.Comparator;
import java.util.List;

public interface Order extends Comparator<ProvState> {
    void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v);
    String name();
}
