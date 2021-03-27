package graph;

import board.State;

public class BreathFirst implements Order{
    @Override
    public int compare(State o1, State o2) {
        return o1.getDepth() - o2.getDepth();
    }
}
