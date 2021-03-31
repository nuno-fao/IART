package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aquarium implements Serializable {
    private final List<Level> levels;
    private final List<Square> squares;

    Aquarium() {
        levels = new ArrayList<>();
        squares = new ArrayList<>();
    }

    public int size() {
        int out = 0;
        for (Level l : levels) {
            out += l.size();
        }
        out += squares.size() * 8;
        return out;
    }

    public void addSquare(Square s) {
        squares.add(s);
    }

    public void process() {
        levels.add(new Level(null));
        int last_y = squares.get(squares.size() - 1).getY();
        for (int i = squares.size() - 1; i != -1; i--) {
            Square tempSquare = squares.get(i);
            if (tempSquare.getY() != last_y) {
                levels.add(new Level(levels.get(levels.size() - 1)));
                last_y = tempSquare.getY();
            }
            levels.get(levels.size() - 1).addSquare(tempSquare);
        }
        squares.clear();
    }

    public List<Level> getLevels() {
        return levels;
    }

    /**
     * Get number of unpainted squares in the aquarium.
     */
    public int getNotPainted() {
        int aux = 0;
        for (Level level : levels) {
            if (!level.isPainted()) {
                aux += level.getNSquares();
            }
        }
        return aux;
    }

    /**
     * Receives a square that was clicked on and paints the aquarium up to the square level.
     * If it is already painted, unpaints the aquarium down to the square level.
     * Also updates depth..
     */
    public void squareIsClicked(Square square) {
        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            if (level.getSquares().get(0).getY() == square.getY()) {
                if (level.isPainted()) {
                    unpaintDownTo(i);
                } else if (!level.isPainted()) {
                    level.paint();
                }
            }
        }

    }

    /**
     * Unpaints every level on the aquarium above the specified one.
     */
    public void unpaintDownTo(int level) {
        for (int i = level; i < levels.size(); i++) {
            levels.get(i).unpaint();
        }
    }


}
