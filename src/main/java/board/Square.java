package board;

import java.io.Serializable;

/**
 * Representation of a square, it belongs to a aquarium and must be painted or not painted
 */
public class Square implements Serializable {
    final int y;

    public int getY() {
        return y;
    }

    boolean painted;
    int aquariumIdentifier;

    public Square(int y, boolean painted, int aquariumIdentifier) {
        this.y = y;
        this.painted = painted;
        this.aquariumIdentifier = aquariumIdentifier;
    }

    public void paint() {
        painted = true;
    }

    public void unpaint() {
        painted = false;
    }

    public boolean isPainted() {
        return painted;
    }


    public int getAquariumIdentifier() {
        return aquariumIdentifier;
    }

    public void setAquariumIdentifier(int aquariumIdentifier) {
        this.aquariumIdentifier = aquariumIdentifier;
    }

    public String getCmdOutput() {
        return aquariumIdentifier + (painted ? "F " : "  ");
    }




}
