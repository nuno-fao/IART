import UI.UI;
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
        App a = new App();
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3,1,2,3,5,5));
        a.board = new Board(6,6,h,v);
        a.board.readBoard(board);
        a.board.getMatrix().get(0).get(0).paint();


        new UI(500,500).draw(a.board);
    }
}
