package board;

import java.util.List;

public class PredefinedProblem {
    final List<Integer> h;
    final List<Integer> v;
    final String boardString;

    public PredefinedProblem(List<Integer> h, List<Integer> v, String boardString) {
        this.h = h;
        this.v = v;
        this.boardString = boardString;
    }

    public List<Integer> getH() {
        return h;
    }

    public List<Integer> getV() {
        return v;
    }

    public String getBoardString() {
        return boardString;
    }
}
