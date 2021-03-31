package board;

import java.util.List;

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
