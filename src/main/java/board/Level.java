package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Level implements Serializable {
    private List<Square> squares = new ArrayList<>();
    private boolean painted = false;
    private Level nextLevel;

    public Level getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Level(List<Square> squares, Level level) {
        this.squares = squares;
        this.nextLevel = level;
    }

    public Level(Level level) {
        this.nextLevel = level;
    }

    public void paint() {
        painted = true;
        for (Square s : squares) {
            s.paint();
        }
        if (nextLevel != null)
            nextLevel.paint();
    }

    public boolean isPainted() {
        return painted;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public int getNSquares() {
        return squares.size();
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public void addSquare(Square s) {
        squares.add(s);
    }

}
