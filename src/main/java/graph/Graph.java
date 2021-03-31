package graph;

import board.ProvState;
import board.State;
import board.StateManager;

import java.util.*;

public class Graph {
    /**
     * Container for states that were already explored.
     */
    private final Set<String> pastStates;

    /**
     * Container with priority for states not yet explored.
     */
    private final PriorityQueue<ProvState> statePriorityQueue;

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
        List<int[]> boardState = state.getState2();

        for (int i = 0; i < state.getAquariums().size(); i++) {
            for (int j = 0; j < state.getAquariums().get(i).getLevels().size(); j++) {
                if (!state.getAquariums().get(i).getLevels().get(j).isPainted()) {
                    State aux = state.copy();
                    if (!aux.paint(i, j))
                        System.out.println("Level " + i + " does not exist on aquarium " + j + " or the aquarium itself.");

                    comparator.setCostAndHeuristic(aux, horizontalCount, verticalCount);
                    if (aux.getHeuristic() != -1 && !pastStates.contains(aux.getUK())) {
                        List<int[]> l = new ArrayList<>(boardState);
                        l.add(new int[]{i, j});
                        out.add(new ProvState(l,aux.getDepth(),aux.getHeuristic()));
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
        return pastStates.size();
    }

    /**
     * Solves the problem used the search method Iterative Deepening
     * @param initial initial state.
     * @return state corresponding to the final solution
     */
    public State solveIterativeDeepening(State initial) {
        comparator = new DepthFirst();
        int max = 0;
        while(true){
            pastStates.add(initial.getUK());
            statePriorityQueue.addAll(getLeaves(initial));
            while (true) {
                State aux = statePriorityQueue.poll().getState();
                if (aux != null) {  //check if there are any states on the queue
                    String auxState = aux.getUK();
                    if (!pastStates.contains(auxState)) {
                        if (aux.isFinished(horizontalCount, verticalCount)) {   //solution found
                            return aux;
                        } else if(aux.getDepth()<max) { //only add new leaves to the queue if it is not on the max depth
                            pastStates.add(auxState);
                            statePriorityQueue.addAll(getLeaves(aux));
                        /*System.out.println(getExploredStates());
                        System.out.println(statePriorityQueue.size());
                        System.out.println();*/
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
                    if (aux.isFinished(horizontalCount, verticalCount)) {
                        return aux;
                    } else {
                        pastStates.add(auxState);
                        statePriorityQueue.addAll(getLeaves(aux));
                        if(getExploredStates()%10000==0) {  //print every X explored states
                            System.out.println(statePriorityQueue.size());
                            System.out.println(getExploredStates());
                            System.out.println();
                        }
                    }
                }
            }
        }
    }
}
