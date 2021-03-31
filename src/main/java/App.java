import UI.View;
import board.PredefinedProblem;
import board.State;
import board.StateManager;
import graph.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    private final List<PredefinedProblem> problems = new ArrayList<>();
    private StateManager stateManager;
    private View view;

    App() {
        problems.add(new PredefinedProblem(new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2)), new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5)), "0 0 0 1 1 1;" +
                "2 1 1 1 1 3;" +
                "2 2 1 1 1 3;" +
                "2 2 4 1 3 3;" +
                "5 2 4 1 3 3;" +
                "5 4 4 4 4 4;"));

        problems.add(new PredefinedProblem(new ArrayList<>(Arrays.asList(1,5,3,2,2,2)), new ArrayList<>(Arrays.asList(2,3,3,1,3,3)), "0 1 1 2 3 4;" +
                "0 5 5 6 6 4;" +
                "7 8 8 8 9 9;" +
                "7 10 10 11 11 12;" +
                "7 13 14 15 16 12;" +
                "7 13 14 15 16 17;"));

        problems.add(new PredefinedProblem(new ArrayList<>(Arrays.asList(6,6,7,4,4,4,4,4,7,2)), new ArrayList<>(Arrays.asList(8,8,4,5,1,3,3,5,2,9)), "0 0 1 2 2 2 2 2 2 2;"+
                "0 1 1 2 2 2 2 3 3 4;"+
                "0 1 1 5 5 6 7 7 3 4;"+
                "0 0 5 5 5 6 7 8 8 4;"+
                "9 0 9 9 5 7 7 7 4 4;"+
                "9 9 9 10 5 11 7 11 4 4;"+
                "10 10 9 10 5 11 7 11 4 4;"+
                "10 10 10 10 11 11 11 11 4 12;"+
                "10 13 13 11 11 12 12 11 4 12;"+
                "10 13 11 11 11 12 12 12 12 12;"));
    }

    public static void main(String[] args) {
        App a = new App();
        String bs;
        int startingProblem=0;

        bs = a.problems.get(startingProblem).getBoardString();
        List<Integer> h =  a.problems.get(startingProblem).getH();
        List<Integer> v =  a.problems.get(startingProblem).getV();

        if(args.length != 0){
            switch (args[0]){
                case "astar":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new AStar());
                    break;
                }
                case "breathfirst":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new BreathFirst());
                    break;
                }
                case "depthfirst":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new DepthFirst());
                    break;
                }
                case "greedy":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new Greedy());
                    break;
                }
                case "uniform":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new UniformCost());
                    break;
                }
                case "iterative":{
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,null);
                    break;
                }
                default:
                    a.stateManager = new StateManager(h.size(),v.size(), h , v,new Greedy());
            }
        }else{
            a.stateManager = new StateManager(h.size(),v.size(), h , v,new Greedy());
        }

        State initial = a.stateManager.readBoard(bs);

        a.view = new View(67 * h.size(), 67 * v.size(), a.stateManager, initial, a.problems);

    }
}