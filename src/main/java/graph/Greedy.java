package graph;

import board.ProvState;
import board.State;

import java.util.List;

public class Greedy implements Order {
    @Override
    public int compare(ProvState o1, ProvState o2) {
        return o1.getHeuristic() - o2.getHeuristic();
    }


    @Override
    public void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v) {
        s.updateDepth(h, v);
    }
}
