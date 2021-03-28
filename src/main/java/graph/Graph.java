package graph;

import board.State;
import board.StateManager;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Graph {
    private Map<String, State> pastStates;
    private PriorityQueue<State> statePriorityQueue;
    private StateManager stateManager;
    private Order comparator;
    private List<Integer> horizontalCount,verticalCount;

    public Graph(StateManager stateManager,Order comparator, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.stateManager = stateManager;
        this.comparator=comparator;
        this.pastStates=new HashMap<>();
        this.statePriorityQueue=new PriorityQueue<>(comparator);
        this.horizontalCount=horizontalCount;
        this.verticalCount=verticalCount;
    }

    private List<State> getLeaves(State state)  {

        List<State> out = new CopyOnWriteArrayList<>();

        for(int i=0;i<state.getAquariums().size();i++){
            for(int j=0;j<state.getAquariums().get(i).getLevels().size();j++) {
                if (!state.getAquariums().get(i).getLevels().get(j).isPainted()) {
                        State aux = state.copy();
                        if (!aux.paint(i, j))
                            System.out.println("Level " + i + " does not exist on aquarium " + j + " or the aquarium itself.");

                        comparator.setCostAndHeuristic(aux,horizontalCount,verticalCount);
                        if (aux.getHeuristic() != -1) {
                            out.add(aux);
                        }
                }
            }
            }
        return out;
    }

    public int getExploredStates(){
        return pastStates.size();
    }

    public void clear(){
        pastStates=new HashMap<>();
        statePriorityQueue=new PriorityQueue<>(comparator);
    }

    public Order getComparator() {
        return comparator;
    }

    public void setComparator(Order comparator) {
        this.comparator = comparator;
    }

    public State solve(State initial){
        pastStates.put(initial.getUK(),initial);
        statePriorityQueue.addAll(getLeaves(initial));
        while(true){
            State aux = statePriorityQueue.poll();
            if(aux!=null){
                String auxState = aux.getUK();
                if(!pastStates.containsKey(auxState)){
                    if(aux.isFinished(horizontalCount,verticalCount)){
                        return aux;
                    }
                    else {
                        pastStates.put(auxState, aux);
                        statePriorityQueue.addAll(getLeaves(aux));
                    }
                }
            }
        }
    }
}
