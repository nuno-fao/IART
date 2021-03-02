package UI;

import board.Board;
import board.Square;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UI {
    JFrame frame;

    public UI(int width, int height){

    }
    public void draw(Board board){
        for (List<Square> a :board.getMatrix()) {
            for (Square s: a) {
                System.out.print(s.getCmdOutput());
            }
            System.out.print("\n");
        }
    }
}
