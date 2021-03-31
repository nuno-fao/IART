package graph;

import board.ProvState;
import board.State;

import java.util.List;

public class AStar implements Order {
    int i = 0;

    @Override
    public int compare(ProvState o1, ProvState o2) {
        i = (o1.getHeuristic() + o1.getDepth()) - (o2.getHeuristic() + o2.getDepth());
        if (i == 0) {
            return o1.getHeuristic() - o2.getHeuristic();
        }
        return i;
    }

    @Override
    public void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v) {
        s.updateCostAndHeuristic(h, v);
    }

    @Override
    public String name() {
        return "A*";
    }
}
