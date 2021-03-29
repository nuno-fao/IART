package board;

import graph.Graph;
import graph.Greedy;

import java.util.*;

public class StateManager {
    public static int width, height;
    public static List<Integer> horizontalCount, verticalCount;
    public static int[][] board;
    private Thread solver;

    private State currentState;
    private State solution;

    public StateManager(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount) {
        StateManager.width = width;
        StateManager.height = height;
        StateManager.horizontalCount = horizontalCount;
        StateManager.verticalCount = verticalCount;
        board = new int[width][height];
        solver=null;
        solution=null;
    }

    public static State restartBoard() {
        List<Aquarium> aquariums = new ArrayList<>();

        List<List<Square>> matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                matrix.get(i).add(null);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Square tempSquare = new Square(y, false, StateManager.board[y][x]);
                Aquarium tempAquarium;
                if (aquariums.size() <= tempSquare.aquariumIdentifier) {
                    tempAquarium = new Aquarium();
                    aquariums.add(tempAquarium);
                } else {
                    tempAquarium = aquariums.get(tempSquare.getAquariumIdentifier());
                }
                tempAquarium.addSquare(tempSquare);
                matrix.get(y).set(x, tempSquare);
            }
        }

        for (Aquarium aquarium : aquariums)
            aquarium.process();

        return new State(matrix, aquariums, 0);
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<Integer> getHorizontalCount() {
        return horizontalCount;
    }

    public List<Integer> getVerticalCount() {
        return verticalCount;
    }

    //creates initial state from a board string
    public State readBoard(String s) {

        board = new int[width][height];
        //todo
        HashMap<Integer, Aquarium> aqMap = new HashMap<>();
        List<Aquarium> aquariums = new ArrayList<>();

        List<List<Square>> matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                matrix.get(i).add(null);
            }
        }

        String[] lines = s.split(";");
        for (int y = 0; y < lines.length; y++) {
            String[] pos = lines[y].split(" ");
            for (int x = 0; x < pos.length; x++) {
                Square tempSquare = new Square( y, false, Integer.parseInt(pos[x]));
                Aquarium tempAquarium;
                if (!aqMap.containsKey(tempSquare.getAquariumIdentifier())) {
                    tempAquarium = new Aquarium();
                    aqMap.put(tempSquare.getAquariumIdentifier(), tempAquarium);
                    aquariums.add(tempAquarium);
                } else {
                    tempAquarium = aqMap.get(tempSquare.getAquariumIdentifier());
                }
                tempAquarium.addSquare(tempSquare);
                matrix.get(y).set(x, tempSquare);
                StateManager.board[y][x] = Integer.parseInt(pos[x]);
            }
        }

        for (Aquarium aquarium : aquariums)
            aquarium.process();

        this.currentState = new State(matrix, aquariums, 0);

        solve();

        return currentState;
    }

    public void actOnClick(int clickX, int clickY) {
        int i = clickX / 67;
        int j = clickY / 67;
        Square aux = currentState.getMatrix().get(j).get(i);
        currentState.getAquariums().get(aux.getAquariumIdentifier()).squareIsClicked(aux);
        currentState.increaseDepth();
    }

    public void reset() {
        State reset = restartBoard();
        this.currentState.setSol2(reset.getState2(), reset.getAquariums());
    }

    public void changeLevel(String board,List<Integer> hc,List<Integer> vc){
        if(solver!=null){
            solver.stop();
        }
        width=hc.size();
        height=vc.size();
        horizontalCount=hc;
        verticalCount=vc;
        solver=null;
        solution=null;
        readBoard(board);
    }

    public void solve(){

        solver = new Thread(() -> {
            Graph graph = new Graph(new Greedy(), horizontalCount, verticalCount);
            long startTime = System.currentTimeMillis();
            solution = graph.solve(getCurrentState());
            System.out.println("AStar explored " + graph.getExploredStates() + " states and solution has depth of " + solution.getDepth() + ": " + (System.currentTimeMillis() - startTime));
            for (String s : solution.getState().split(";")) {
                System.out.println(s);
            }
        });
        solver.start();
    }

}