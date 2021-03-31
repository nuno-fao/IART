package graph;

import board.ProvState;
import board.State;
import board.StateManager;

import java.util.*;

public class Graph {
    private final Set<String> pastStates;
    private final PriorityQueue<ProvState> statePriorityQueue;
    private final Order comparator;
    private final List<Integer> horizontalCount;
    private final List<Integer> verticalCount;

    public Graph(Order comparator, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.comparator = comparator;
        this.pastStates = new HashSet<>();
        this.statePriorityQueue = new PriorityQueue<ProvState>(comparator);
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
    }

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

    public int getExploredStates() {
        return pastStates.size();
    }

    public State solveIterativeDeepening(State initial) {
        comparator = new DepthFirst();
        int max = 0;
        while(true){
            pastStates.add(initial.getUK());
            statePriorityQueue.addAll(getLeaves(initial));
            while (true) {
                State aux = statePriorityQueue.poll();
                if (aux != null) {
                    String auxState = aux.getUK();
                    if (!pastStates.contains(auxState)) {
                        if (aux.isFinished(horizontalCount, verticalCount)) {
                            return aux;
                        } else if(aux.getDepth()<max) {
                            pastStates.add(auxState);
                            statePriorityQueue.addAll(getLeaves(aux));
                        /*System.out.println(getExploredStates());
                        System.out.println(statePriorityQueue.size());
                        System.out.println();*/
                        }
                    }
                }
                else{
                    max++;
                    pastStates.clear();
                    statePriorityQueue.clear();
                    break;
                }
            }
        }

    }

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
                        if(getExploredStates()%10000==0) {
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
