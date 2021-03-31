package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representation of a game state
 */
public class State implements Serializable {

    /**
     * Sqaure matrix.
     */
    private final List<List<Square>> matrix;

    /**
     * Lists of aquariums.
     */
    private final List<Aquarium> aquariums;

    /**
     * Depth of the state.
     */
    private int depth;

    /**
     * Heuristic of the state.
     */
    private int heuristic;

    /**
     * State unique identifier.
     */
    private String uk = null;

    /**
     * Number of squares left to paint till the solution is reached.
     */
    private Integer squaresLeft = null;

    public State(List<List<Square>> matrix, List<Aquarium> aquariums, int depth) {
        this.matrix = matrix;
        this.aquariums = aquariums;
        this.depth = depth;
        this.heuristic = 999;//default value until it is updated;
    }

    public int size() {
        int out = 0;
        out += matrix.size() * matrix.get(0).size() * 8;
        for (Aquarium a : aquariums) {
            out += a.size();
        }
        out += 12;
        out += uk.length();
        return out;
    }

    public int getDepth() {
        return depth;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public List<List<Square>> getMatrix() {
        return matrix;
    }

    public List<Aquarium> getAquariums() {
        return aquariums;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        State comp = (State) obj;
        for (int y = 0; y < this.getMatrix().size(); y++) {
            for (int x = 0; x < this.getMatrix().get(0).size(); x++) {
                if (!matrix.get(y).get(x).getCmdOutput().equals(comp.getMatrix().get(y).get(x).getCmdOutput())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Paints a certain aquarium up to a certain level.
     * @param aquarium aquarium to paint.
     * @param level level to paint up to.
     * @return true if it is a valid move, false otherwise.
     */
    public boolean paint(int aquarium, int level) {
        try {
            aquariums.get(aquarium).getLevels().get(level).paint();
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * Builds and returns board string from the current state.
     */
    public String getState() {
        StringBuilder out = new StringBuilder();
        for (List<Square> squares : matrix) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (squares.get(j).isPainted()) {
                    out.append("1");
                } else {
                    out.append("0");
                }
                if (j == matrix.get(0).size() - 1) {
                    out.append(";");
                } else {
                    out.append(" ");
                }
            }
        }
        return out.toString();
    }

    /**
     * Builds and returns state identifier.
     */
    public String getUK() {
        if (uk == null) {
            StringBuilder out = new StringBuilder();
            for (List<Square> squares : matrix) {
                for (int j = 0; j < matrix.get(0).size(); j++) {
                    if (squares.get(j).isPainted()) {
                        out.append("1");
                    } else {
                        out.append("0");
                    }
                }
            }
            uk = out.toString();
        }
        return uk;
    }

    /**
     * Verifies is the current state a solution to the problem.
     * @param horizontalCount horizontal numbers.
     * @param verticalCount vertical numbers.
     * @return true if it is a solution, false otherwise.
     */
    public boolean isFinished(List<Integer> horizontalCount, List<Integer> verticalCount) {
        if (squaresLeft == null)
            squaresLeft = getSquaresLeft(horizontalCount, verticalCount);
        return squaresLeft == 0;
    }


    /**
     * Used for checking heuristic and game end.
     */
    public int getSquaresLeft(List<Integer> horizontalCount, List<Integer> verticalCount) {
        int out = 0, aux;
        for (int i = 0; i < verticalCount.size(); i++) {
            aux = checkHorizontalLine(verticalCount.get(i), matrix.get(i));
            if (aux == -1) {
                return -1;
            } else {
                out += aux;
            }

        }
        for (int i = 0; i < horizontalCount.size(); i++) {
            aux = checkVerticalLine(horizontalCount.get(i), matrix, i);
            if (aux == -1) {
                return -1;
            }
        }

        return out;

    }

    /**
     * Used for checking heuristic and game end.
     */
    private int checkHorizontalLine(Integer number, List<Square> line) {
        Integer i = 0;
        for (Square square : line) {
            if (square.isPainted()) i++;
        }
        if (i > number) {
            return -1;
        } else if (i.equals(number)) {
            return 0;
        } else {
            return number - i;
        }
    }

    /**
     * Used for checking heuristic and game end.
     */
    private int checkVerticalLine(Integer number, List<List<Square>> columns, int col) {
        Integer i = 0;
        for (List<Square> list : columns) {
            if (list.get(col).isPainted()) i++;
        }

        if (i > number) {
            return -1;
        } else if (i.equals(number)) {
            return 0;
        } else {
            return number - i;
        }
    }


    //returns heuristic for the current state. If -1 - invalid, if 0 - finished, anything else - the actual heuristic
    /**
     * Used for checking heuristic
     * @param horizontalCount horizontal numbers.
     * @param verticalCount vertical numbers.
     * @return  -1 if invalid, 0 if solution, anything else is the actual heuristic.
     */
    public int updateHeuristic(List<Integer> horizontalCount, List<Integer> verticalCount) {
        squaresLeft = getSquaresLeft(horizontalCount, verticalCount);
        int out = 0, nLeft = squaresLeft;
        //return nLeft;
        this.squaresLeft = nLeft;
        if (nLeft == -1 || nLeft == 0) {
            return nLeft;
        }
        List<Integer> aux = new ArrayList<>();

        for (Aquarium aquarium : aquariums) {
            aux.add(aquarium.getNotPainted());
        }
        aux.sort(Comparator.naturalOrder());

        int counter = 0;
        int s = aux.size() - 1;
        int m = 0;
        while (counter < nLeft) {
            if (s - m >= 0) {
                counter += aux.get(s - m);
                out++;
                m++;
            }
        }
        return out;
    }

    /**
     * Deep copies the state.
     */
    public State copy() {
        State out = StateManager.restartBoard();
        out.setSol2(aquariums);
        out.depth = depth;
        return out;
    }

    /**
     * Used for setting the solution.
     */
    public void setSol2(List<Aquarium> aquariums) {
        for (int a = 0; a < aquariums.size(); a++) {
            List<Level> local_level = this.aquariums.get(a).getLevels();
            List<Level> copied_level = aquariums.get(a).getLevels();
            for (int l = 0; l < local_level.size(); l++) {
                if (copied_level.get(l).isPainted())
                    local_level.get(l).paint();
                else {
                    local_level.get(l).unpaint();
                }
            }
        }
    }

    /**
     * Used for setting the solution.
     */
    public void setSol(List<int[]> aqs){
        for(int a[]:aqs){
            this.aquariums.get(a[0]).getLevels().get(a[1]).paint();
        }
    }

    public List<int[]> getState2() {
        int y = 0;
        int x;
        List<int[]> out = new ArrayList<>();
        for(Aquarium aquarium:aquariums){
            x=0;
            for(Level level:aquarium.getLevels()){
                if(level.isPainted())
                    out.add(new int[]{y, x});
                x++;
            }
            y++;
        }
        return out;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }


    public void updateCostAndHeuristic(List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.depth++;
        this.heuristic = updateHeuristic(horizontalCount, verticalCount);
    }

    public void updateDepth(List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.depth++;
        if (squaresLeft == null)
            squaresLeft = getSquaresLeft(horizontalCount, verticalCount);
        this.heuristic = squaresLeft;
    }

    /**
     * Unpaint everything.
     */
    public void reset(){
        for(Aquarium aquarium:aquariums){
            aquarium.unpaintDownTo(0);
        }
    }

    public void increaseDepth() {
        this.depth++;
    }

}
