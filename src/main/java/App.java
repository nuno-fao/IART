import UI.View;
import board.State;
import board.StateManager;
import graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    static final List<PredefinedProblem> problems = new ArrayList<>();
    private StateManager stateManager;
    private View view;

    App() {
        problems.add(new PredefinedProblem(new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2)), new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5)), "0 0 0 1 1 1;" +
                "2 1 1 1 1 3;" +
                "2 2 1 1 1 3;" +
                "2 2 4 1 3 3;" +
                "5 2 4 1 3 3;" +
                "5 4 4 4 4 4;"));

        problems.add(new PredefinedProblem(new ArrayList<>(Arrays.asList(4, 4, 5, 3, 4, 4)), new ArrayList<>(Arrays.asList(1, 4, 5, 5, 5, 4)), "0 0 1 1 1 2;" +
                "0 0 3 1 2 2" +
                "4 5 3 1 2 17;" +
                "6 7 12 14 2 17;" +
                "8 9 13 14 14 16;" +
                "10 11 13 15 16;"));
    }

    public static void main(String[] args) {
        App a = new App();
        String bs;
        List<Integer> h;
        List<Integer> v;


        bs =
                "0 0 1 2 2 2 2 2 2 2;"+
                        "0 1 1 2 2 2 2 3 3 4;"+
                        "0 1 1 5 5 6 7 7 3 4;"+
                        "0 0 5 5 5 6 7 8 8 4;"+
                        "9 0 9 9 5 7 7 7 4 4;"+
                        "9 9 9 10 5 11 7 11 4 4;"+
                        "10 10 9 10 5 11 7 11 4 4;"+
                        "10 10 10 10 11 11 11 11 4 12;"+
                        "10 13 13 11 11 12 12 11 4 12;"+
                        "10 13 11 11 11 12 12 12 12 12;";

        h = new ArrayList<>(Arrays.asList(6,6,7,4,4,4,4,4,7,2));
        v = new ArrayList<>(Arrays.asList(8,8,4,5,1,3,3,5,2,9));
        a.stateManager = new StateManager(10,10, h , v);

        long startTime = System.currentTimeMillis();

        State initial = a.stateManager.readBoard(bs);

        a.view = new View(67 * h.size(), 67 * v.size(), a.stateManager, initial);

        Thread solve = new Thread(() -> {
            Graph graph = new Graph(new Greedy(), h, v);
            State initial2 = graph.solve(a.stateManager.getCurrentState());
            System.out.println("AStar explored " + graph.getExploredStates() + " states and solution has depth of " + initial2.getDepth() + ": " + (System.currentTimeMillis() - startTime));
            for (String s : initial2.getState().split(";")) {
                System.out.println(s);
            }
            a.stateManager.getCurrentState().copy(initial2);
            a.view.reload();
        });

        solve.start();
    }
}


class PredefinedProblem {
    final List<Integer> h;
    final List<Integer> v;
    final String boardString;

    public PredefinedProblem(List<Integer> h, List<Integer> v, String boardString) {
        this.h = h;
        this.v = v;
        this.boardString = boardString;
    }
}
