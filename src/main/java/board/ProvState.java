package board;

import java.util.List;

/**
 * Pre state, it has enough info to create a full state but occupies less than a full state(used to reduce runtime memory usage)
 * real states are created once the pre state is polled from the graph priority queue
 */
public class ProvState {
    List<int[]> painted;
    int depth;
    int heuristic;

    public ProvState(List<int[]> painted,int d,int h){
        this.painted = painted;
        heuristic = h;
        depth = d;
    }
    public State getState(){
        State temp =  StateManager.restartBoard();
        temp.setSol(painted);
        temp.setDepth(depth);
        return temp;
    }

    public int getHeuristic(){
        return heuristic;
    }
    public int getDepth(){
        return depth;
    }
}
