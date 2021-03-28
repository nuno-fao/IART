package board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StateManager {
    public static int width, height;
    public static List<Integer> horizontalCount, verticalCount;
    public static int[][] board;

    private State currentState;

    public StateManager(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount) {
        StateManager.width = width;
        StateManager.height = height;
        StateManager.horizontalCount = horizontalCount;
        StateManager.verticalCount = verticalCount;
        board = new int[width][height];
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

        return currentState;
    }

    public void actOnClick(int clickX, int clickY) {
        int i = clickX / 67;
        int j = clickY / 67;
        Square aux = currentState.getMatrix().get(j).get(i);
        currentState.getAquariums().get(aux.getAquariumIdentifier()).squareIsClicked(aux);
        currentState.increaseDepth();
        System.out.println("iooioi");
    }

    public void reset() {
        State reset = restartBoard();
        this.currentState.setSol2(reset.getState2(), reset.getAquariums());
    }
}