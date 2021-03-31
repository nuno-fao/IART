package board;

import graph.AStar;
import graph.Graph;
import graph.Greedy;
import graph.Order;

import java.util.*;

public class StateManager {
    /**
     * Board's width and height.
     */
    public static int width, height;

    /**
     * Board's horizontal and vertical numbers.
     */
    public static List<Integer> horizontalCount, verticalCount;

    /**
     * The board.
     */
    public static int[][] board;

    /**
     * Thread that will solve the problem.
     */
    private Thread solver;

    /**
     * The state that the user will operate on.
     */
    private State currentState;

    /**
     * Solution reached by the solver thread. Null by default
     */
    private State solution;

    /**
     * Search method that will be used to solve the problem
     */
    private Order algorithm;

    public Thread getSolver() {
        return solver;
    }

    public State getSolution() {
        return solution;
    }

    /**
     * Verifies if the current state is a solution to the problem.
     */
    public boolean reachedToTheSolution(){
        return currentState.getSquaresLeft(horizontalCount,verticalCount) == 0;
    }

    public StateManager(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount,Order algorithm) {
        StateManager.width = width;
        StateManager.height = height;
        StateManager.horizontalCount = horizontalCount;
        StateManager.verticalCount = verticalCount;
        board = new int[width][height];
        solver=null;
        solution=null;
        this.algorithm = algorithm;
    }

    /**
     * Resets the board.
     */
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


    /**
     * Creates initial state from a board string.
     * @param s board string.
     * @return initial state.
     */
    public State readBoard(String s) {

        board = new int[width][height];

        HashMap<Integer, Aquarium> aqMap = new HashMap<>();

        //create square matrix

        List<Aquarium> aquariums = new ArrayList<>();

        List<List<Square>> matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < width; j++) {
                matrix.get(i).add(null);
            }
        }

        //create separaate aquariums
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

        //work on the aquariums before starting
        for (Aquarium aquarium : aquariums)
            aquarium.process();

        //create current state
        this.currentState = new State(matrix, aquariums, 0);

        //start solver thread
        solve();

        return currentState;
    }

    /**
     * Receives coordenates, identifies the squares that was clicked on and paints the aquarium up to the square level.
     * If it is already painted, unpaints the aquarium down to the square level.
     * Also updates depth.
     * @param clickX x coordinate.
     * @param clickY y coordinate.
     */
    public void actOnClick(int clickX, int clickY) {
        int i = clickX / 67;
        int j = clickY / 67;
        Square aux = currentState.getMatrix().get(j).get(i);
        currentState.getAquariums().get(aux.getAquariumIdentifier()).squareIsClicked(aux);
        currentState.increaseDepth();
    }

    /**
     * Unpaints every level on the current state.
     */
    public void reset() {
        //State reset = restartBoard();
        //this.currentState.setSol2(reset.getState2(), reset.getAquariums());
        currentState.reset();
    }

    /**
     * Copies the solution reched by the solver thread to the current state.
     */
    public void giveSolution(){
        currentState.setSol2(solution.getAquariums());
    }

    /**
     * Changes the problem shown.
     * @param board board string.
     * @param hc horizontal numbers.
     * @param vc vertical numbers.
     */
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

    /**
     * Initiates solver thread to reach the solution.
     */
    public void solve(){

        solver = new Thread(() -> {
            Graph graph = new Graph(this.algorithm, horizontalCount, verticalCount);
            long startTime = System.currentTimeMillis();
            if(this.algorithm == null)//if no algorythm was specified in the constructor it means iterative deepening should be used
                solution = graph.solveIterativeDeepening(getCurrentState());//TODO
            else
                solution = graph.solve(getCurrentState());
            System.out.println("Explored " + graph.getExploredStates() + " states and solution has depth of " + solution.getDepth() + ": " + (System.currentTimeMillis() - startTime));
            for (String s : solution.getState().split(";")) {
                System.out.println(s);
            }
        });
        solver.start();
    }

    /**
     * Compares the current state to the solution given by the solver thread.
     * Checks if there are any levels that shouldn't painted and unpaints them. If none of those are found it paints a level that should be, starting from the lower ones.
     * Only does one change per call.
     */
    public void giveHint(){

        for(int i=0;i<currentState.getAquariums().size();i++){
            for(int j=0;j<currentState.getAquariums().get(i).getLevels().size();j++){
                if(currentState.getAquariums().get(i).getLevels().get(j).isPainted() && !solution.getAquariums().get(i).getLevels().get(j).isPainted()){
                    currentState.getAquariums().get(i).unpaintDownTo(j);
                    return;
                }
            }
        }

        for(int i=0;i<currentState.getAquariums().size();i++){
            for(int j=0;j<currentState.getAquariums().get(i).getLevels().size();j++){
                if(!currentState.getAquariums().get(i).getLevels().get(j).isPainted() && solution.getAquariums().get(i).getLevels().get(j).isPainted()){
                    currentState.getAquariums().get(i).getLevels().get(j).paint();
                    return;
                }
            }
        }
    }

    /**
     * Updates 2 lists with the number of squares left to paint, horizontally and vertically.
     */
    public void getLeftSquares(List<Integer> h,List<Integer> v){


        int  y = 0;
        if(h == null)
            h = new ArrayList<>();
        if(v == null)
            v = new ArrayList<>();
        for(List<Square> l: currentState.getMatrix()){
            int sum = 0;
            for(Square s:l){
                if(s.isPainted())
                    sum++;
            }
            v.add(verticalCount.get(y)-sum);
            y++;
        }

        for(int x = 0; x < currentState.getMatrix().get(0).size(); x++){
            int sum = 0;
            for(y = 0; y < currentState.getMatrix().size(); y++){
                if(currentState.getMatrix().get(y).get(x).isPainted())
                    sum++;
            }
            h.add(horizontalCount.get(x)-sum);
        }
    }

}