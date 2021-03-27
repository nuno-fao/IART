package graph;

import board.State;

public class AStar implements Order{
    @Override
    public int compare(State o1, State o2) {
        return (o1.getHeuristic()+o1.getDepth()) - (o2.getHeuristic()+o2.getDepth());
    }
}
