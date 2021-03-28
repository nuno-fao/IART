package board;

import java.io.Serializable;
import java.util.Objects;

public class Square implements Serializable {
    Position pos;
    boolean painted;
    int aquariumIdentifier;

    public Square(Position pos, boolean painted,int aquariumIdentifier) {
        this.pos = pos;
        this.painted = painted;
        this.aquariumIdentifier = aquariumIdentifier;
    }

    public void paint(){
        painted = true;
    }

    public void unpaint() {painted = false;}

    public boolean isPainted() {
        return painted;
    }

    public int getAquariumIdentifier() {
        return aquariumIdentifier;
    }

    public String getCmdOutput(){
        return aquariumIdentifier + (painted ? "F " : "  ");
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    public void setAquariumIdentifier(int aquariumIdentifier) {
        this.aquariumIdentifier = aquariumIdentifier;
    }

}
