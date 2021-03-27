package graph;

import board.State;

import java.util.Comparator;
import java.util.List;

public interface Order extends Comparator<State> {
    void setCostAndHeuristic(State s,List<Integer> h,List<Integer> v);
}
