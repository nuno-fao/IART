package graph;

import board.ProvState;
import board.State;

import java.util.List;

/**
 * Used to specify the cost and heuristic to calculate and to sort according to AStar algo
 */
public class AStar implements Order {
    int i = 0;

    /**
     * Changes the name of this Student.
     * This may involve a lengthy legal process.
     * @param o1 instane of ProvState.
     * @param o2 instance of ProvState.
     * @return int corresponding to the priority of o1 over o2. Negative if it has, positive if not and 0 if it is the same
     */
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
