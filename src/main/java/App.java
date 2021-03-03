import UI.View;
import board.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    Board board;
    public static void main(String[] args) {
        String board =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        String sol = "1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1";

        App a = new App();

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3));
        List<Integer> v = new ArrayList<>(Arrays.asList(2,2,2));
        a.board = new Board(6,6,h,v);
        a.board.readBoard(board);
        a.board.setSol(sol);


        View view = new View(400,400,a.board);
    }
}
