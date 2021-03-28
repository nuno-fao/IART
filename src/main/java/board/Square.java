package board;

import java.io.Serializable;

public class Square implements Serializable {
    final int y;

    public int getY() {
        return y;
    }

    boolean painted;
    final int aquariumIdentifier;

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


    public String getCmdOutput() {
        return aquariumIdentifier + (painted ? "F " : "  ");
    }



}
