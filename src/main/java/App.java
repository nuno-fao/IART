import UI.View;
import board.State;
import board.StateManager;
import graph.AStar;
import graph.Graph;

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
                "0 1 1 2 2 2;" +
                        "0 3 4 5 6 6;" +
                        "7 8 4 5 9 9;" +
                        "10 8 4 5 11 11;" +
                        "12 12 4 13 13 14;" +
                        "15 15 16 16 13 17;";

        h = new ArrayList<>(Arrays.asList(5, 5, 2, 4, 5, 3));
        v = new ArrayList<>(Arrays.asList(4, 2, 3, 5, 5, 5));
        a.stateManager = new StateManager(6, 6, h, v);

        long startTime = System.currentTimeMillis();

        State initial = a.stateManager.readBoard(bs);

        a.view = new View(67 * h.size(), 67 * v.size(), a.stateManager, initial);

        Thread solve = new Thread(() -> {
            Graph graph = new Graph(new AStar(), h, v);
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
