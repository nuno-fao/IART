import UI.View;
import board.StateManager;
import board.State;
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

        String sol = "1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1";

        App a = new App();

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        a.stateManager = new StateManager(6,6,h,v);
        State currentState = a.stateManager.readBoard(board);
        Graph graph = new Graph(a.stateManager,currentState);
        //a.board.setSol(sol);


        View view = new View(400,400,a.stateManager,currentState);
    }
}
