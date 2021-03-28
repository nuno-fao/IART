package graph;

import board.State;

import java.util.List;

public class BreathFirst implements Order {
    @Override
    public int compare(State o1, State o2) {
        return o1.getDepth() - o2.getDepth();
    }


    @Override
    public void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v) {
        s.updateDepth(h, v);

    }
}
