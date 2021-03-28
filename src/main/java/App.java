import UI.View;
import board.*;
import graph.Graph;
import graph.Greedy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class App {
    private StateManager stateManager;
    private View view;
    static List<PredefinedProblem> problems = new ArrayList<>();

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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        App a = new App();
        State solution ;
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
        a.stateManager = new StateManager(6,6, h , v);

        long startTime = System.currentTimeMillis();
        State initial = a.stateManager.readBoard(bs);

        a.view = new View(67*h.size(),67*v.size(),a.stateManager,initial);

        Thread solve = new Thread(() -> {
            Graph graph = new Graph(a.stateManager, new Greedy(), h, v);
            State initial2 = graph.solve(a.stateManager.getCurrentState());
            System.out.println("AStar explored "+ graph.getExploredStates()+" states and solution has depth of "+ initial2.getDepth()+": "+(System.currentTimeMillis()-startTime));
            for (String s: initial2.getState().split(";")){
                System.out.println(s);
            }
            a.stateManager.getCurrentState().copy(initial2);
            a.view.reload();
        });
        solve.start();


    }
}


class PredefinedProblem {
    List<Integer> h;
    List<Integer> v;
    String boardString;

    public PredefinedProblem(List<Integer> h, List<Integer> v, String boardString) {
        this.h = h;
        this.v = v;
        this.boardString = boardString;
    }
}
