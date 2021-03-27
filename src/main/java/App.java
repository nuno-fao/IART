import UI.View;
import board.StateManager;
import board.State;
import graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    StateManager stateManager;
    public static void main(String[] args) {
        Graph graph ;
        State solution ;
        StateManager stateManager;
        String bs;
        List<Integer> h ;
        List<Integer> v ;



        bs =
                "0 1 1 2 3 3;"+
                        "0 4 4 5 6 7;"+
                        "8 9 10 5 6 7;"+
                        "8 11 10 6 6 6;"+
                        "12 13 13 14 15 16;"+
                        "12 17 17 14 15 16;";

        h = new ArrayList<>(Arrays.asList(2, 5, 5, 5, 2, 3));
        v = new ArrayList<>(Arrays.asList(4, 4, 2, 5, 2, 5));
        stateManager = new StateManager(6,6, h , v);

        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(stateManager,new Greedy(),h,v);
        solution = graph.solve(initial);
        System.out.println("AStar explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
        for (String s:solution.getState().split(";")){
            System.out.println(s);
        }
    }
}
