import UI.View;
import board.StateManager;
import board.State;
import graph.DepthFirst;
import graph.Djikstra;
import graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    StateManager stateManager;
    public static void main(String[] args) {
        String board =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        String board2 =
                "0 0 1 1 4 4 6 6 6 6 6 7 7 7 7;" +
                        "2 0 1 1 3 4 4 4 4 8 9 10 11 11 12;" +
                        "2 2 2 1 3 5 5 5 5 8 9 10 10 11 12;"+
                        "1 1 1 1 1 13 13 5 5 8 9 9 10 10 12;"+
                        "14 15 15 15 15 15 13 18 18 17 17 19 12 12 12;"+
                        "14 15 15 16 16 15 18 18 18 18 18 19 20 21 21;"+
                        "14 15 16 16 16 16 18 22 25 25 24 20 20 26 26;"+
                        "27 27 27 23 23 22 22 22 22 25 25 26 26 26 26;"+
                        "28 23 23 23 22 22 22 25 25 25 25 36 26 37 37;"+
                        "28 28 29 32 33 33 33 25 25 35 35 36 26 26 37;"+
                        "30 29 29 32 32 33 34 34 35 35 36 36 26 26 37;"+
                        "30 29 44 44 44 33 34 43 36 36 36 36 39 37 37;"+
                        "30 29 44 29 44 33 43 43 40 40 40 40 39 37 37;"+
                        "31 29 29 29 44 44 44 43 40 41 40 39 39 37 37;"+
                        "31 44 44 44 44 44 42 42 40 41 39 39 38 38 37;";

        String sol = "1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1";

        App a = new App();

        //List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        //List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        List<Integer> h =new ArrayList<>(Arrays.asList(6,6,5,4,6,10,11,12,8,5,6,7,11,8,5));
        List<Integer> v =  new ArrayList<>(Arrays.asList(11,7,10,5,7,8,10,9,7,6,8,3,5,3,11));

        a.stateManager = new StateManager(h.size(),v.size(),h,v);
        State currentState = a.stateManager.readBoard(board2);

        //Graph graph = new Graph(a.stateManager,new Djikstra(),h,v);

        View view = new View(67*h.size(),67*v.size(),a.stateManager,currentState);

    }
}
