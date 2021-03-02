package board;

public class Square {
    Position pos;
    boolean painted;
    int aquarium;

    public Square(Position pos, boolean painted,int aquarium) {
        this.pos = pos;
        this.painted = painted;
        this.aquarium = aquarium;
    }

    public void paint(){
        painted = true;
    }

    public boolean isPainted() {
        return painted;
    }

    public int getAquarium() {
        return aquarium;
    }

    public String getCmdOutput(){
        return aquarium + (painted ? "F " : "  ");
    }
}
