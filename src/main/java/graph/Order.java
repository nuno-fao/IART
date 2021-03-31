package graph;

import board.ProvState;
import board.State;

import java.util.Comparator;
import java.util.List;

public interface Order extends Comparator<ProvState> {
    /**
     * Updates the heuristic and cost of a given state.
     * This may involve a lengthy legal process.
     * @param s state to update
     * @param h horizontal numbers on the board.
     * @param v vertical numbers on the board.
     */
    void setCostAndHeuristic(State s, List<Integer> h, List<Integer> v);
}
