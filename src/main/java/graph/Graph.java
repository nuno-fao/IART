package graph;

import board.State;

import java.util.*;

public class Graph {
    private final Set<String> pastStates;
    private final PriorityQueue<State> statePriorityQueue;
    private final Order comparator;
    private final List<Integer> horizontalCount;
    private final List<Integer> verticalCount;

    public Graph(Order comparator, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.comparator = comparator;
        this.pastStates = new HashSet<>();
        this.statePriorityQueue = new PriorityQueue<>(comparator);
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
    }

    private List<State> getLeaves(State state) {
        List<State> out = new ArrayList<>();

        for (int i = 0; i < state.getAquariums().size(); i++) {
            for (int j = 0; j < state.getAquariums().get(i).getLevels().size(); j++) {
                if (!state.getAquariums().get(i).getLevels().get(j).isPainted()) {
                    State aux = state.copy();
                    if (!aux.paint(i, j))
                        System.out.println("Level " + i + " does not exist on aquarium " + j + " or the aquarium itself.");

                    comparator.setCostAndHeuristic(aux, horizontalCount, verticalCount);
                    if (aux.getHeuristic() != -1 && !pastStates.contains(aux.getUK())) {
                        out.add(aux);
                    }
                }
            }
        }
        return out;
    }

    public int getExploredStates() {
        return pastStates.size();
    }

    public State solve(State initial) {
        int max = 0;
        pastStates.add(initial.getUK());
        statePriorityQueue.addAll(getLeaves(initial));
        while (true) {
            State aux = statePriorityQueue.poll();
            if (aux != null) {
                String auxState = aux.getUK();
                if (!pastStates.contains(auxState)) {
                    if (aux.isFinished(horizontalCount, verticalCount)) {
                        return aux;
                    } else {
                        pastStates.add(auxState);
                        statePriorityQueue.addAll(getLeaves(aux));
                        System.out.println(getExploredStates());
                        System.out.println(statePriorityQueue.size());
                        System.out.println();
                    }
                }
            }
        }
    }
}
