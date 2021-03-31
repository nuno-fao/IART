package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private final List<Square> squares = new ArrayList<>();
    private boolean painted = false;
    private final Level nextLevel;

    public Level(Level level) {
        this.nextLevel = level;
    }

    public int size() {
        int out = 0;
        for (Square s : squares) {
            out += 15;
        }
        out += 8;
        out += 1;
        return out;
    }

    /**
     * Paints every square on the level and the ones below.
     */
    public void paint() {
        painted = true;
        for (Square s : squares) {
            s.paint();
        }
        if (nextLevel != null)
            nextLevel.paint();
    }

    /**
     * Unpaints every square on the level.
     */
    public void unpaint() {
        painted = false;
        for (Square s : squares) {
            s.unpaint();
        }
    }

    public boolean isPainted() {
        return painted;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public int getNSquares() {
        return squares.size();
    }

    public void addSquare(Square s) {
        squares.add(s);
    }

}
