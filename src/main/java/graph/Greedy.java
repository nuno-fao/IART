package graph;

import board.State;

public class Greedy implements Order{
    @Override
    public int compare(State o1, State o2) {
        return o1.getHeuristic() - o2.getHeuristic();
    }
}
