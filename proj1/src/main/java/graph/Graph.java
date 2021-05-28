package graph;

import board.ProvState;
import board.State;
import board.StateManager;

import java.util.*;

/**
 * Class used to hold the tree/graph representation and explore states acoding to the order set by Order
 */
public class Graph {
    /**
     * Container for states that were already explored.
     */
    private final Set<String> pastStates;

    /**
     * Container with priority for states not yet explored.
     */
    private PriorityQueue<ProvState> statePriorityQueue;

    /**
     * Search method for solving the problem. Used for ordering the argument statePriorityQueue.
     */
    private Order comparator;

    /**
     * Horizontal numbers on the board.
     */
    private final List<Integer> horizontalCount;

    /**
     * Vertical numbers on the board.
     */
    private final List<Integer> verticalCount;
    private int explored = 0;

    public Graph(Order comparator, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.comparator = comparator;
        this.pastStates = new HashSet<>();
        this.statePriorityQueue = new PriorityQueue<ProvState>(comparator);
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
    }

    /**
     * Creates a list of new VALID states by applying all possible moves to the argument state
     * @param state state from which to obtain the new states.
     * @return list of new VALID states
     */
    private List<ProvState> getLeaves(State state) {
        List<ProvState> out = new ArrayList<>();
        List<int[]> boardState = state.getPainted();

        for (int i = 0; i < state.getAquariums().size(); i++) {
            for (int j = 0; j < state.getAquariums().get(i).getLevels().size(); j++) {
                if (!state.getAquariums().get(i).getLevels().get(j).isPainted()) {
                    State aux = state.copy();
                    if (!aux.paint(i, j)) {
                        System.out.println("Level " + i + " does not exist on aquarium " + j + " or the aquarium itself.");
                        continue;
                    }

                        comparator.setCostAndHeuristic(aux, horizontalCount, verticalCount);
                        if (aux.getHeuristic() != -1 && !pastStates.contains(aux.getState())) {
                            List<int[]> l = new ArrayList<>(boardState);
                            l.add(new int[]{i, j});
                            out.add(new ProvState(l, aux.getDepth(), aux.getHeuristic()));
                        }
                }
            }
        }
        return out;
    }

    /**
     * Returns the size of container of already explored states
     */
    public int getExploredStates() {
        return explored;
    }

    /**
     * Solves the problem used the search method Iterative Deepening
     * @param initial initial state.
     * @return state corresponding to the final solution
     */
    public State solveIterativeDeepening(State initial) {
        this.comparator = new DepthFirst();
        statePriorityQueue = new PriorityQueue<>(this.comparator);
        int max = 0;
        while(true){
            pastStates.add(initial.getUK());
            statePriorityQueue.addAll(getLeaves(initial));
            initial.setDepth(0);
            while (true) {
                ProvState preState = statePriorityQueue.poll();
                if (preState != null) {  //check if there are any states on the queue
                    State aux = preState.getState();
                    String auxState = aux.getUK();
                    if (!pastStates.contains(auxState)) {
                        explored++;
                        if (aux.isFinished(horizontalCount, verticalCount)) {   //solution found
                            return aux;
                        } else if(aux.getDepth()<max) { //only add new leaves to the queue if it is not on the max depth
                            pastStates.add(auxState);
                            statePriorityQueue.addAll(getLeaves(aux));
                        }
                    }
                }
                else{ //if there aren't states on the queue, increase max depth, reset containers and start over
                    max++;
                    pastStates.clear();
                    statePriorityQueue.clear();
                    break;
                }
            }
        }
    }

    /**
     * Solves the problem used the search method specified comparator
     * @param initial initial state.
     * @return state corresponding to the final solution
     */
    public State solve(State initial) {
        pastStates.add(initial.getUK());
        statePriorityQueue.addAll(getLeaves(initial));
        while (true) {
            State aux = statePriorityQueue.poll().getState();
            if (aux != null) {
                String auxState = aux.getUK();
                if (!pastStates.contains(auxState)) {
                    explored++;
                    if (aux.isFinished(horizontalCount, verticalCount)) {
                        return aux;
                    } else {
                        pastStates.add(auxState);
                        statePriorityQueue.addAll(getLeaves(aux));
                    }
                }
            }
        }
    }

    public Order getComparator() {
        return comparator;
    }
}
