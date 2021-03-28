package graph;

import board.State;

import java.util.List;

public class DepthFirst implements Order {
    @Override
    public int compare(State o1, State o2) {
        return o2.getDepth() - o1.getDepth();
    }

    @Override
    public void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v) {
        s.updateDepth(h, v);
    }
}
