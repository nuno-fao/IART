package UI;

import javax.swing.*;
import java.awt.*;

public class UI {
    Board board;
    JFrame frame;

    public UI(int width, int height){
        frame =new JFrame();

        /*JButton b=new JButton("click");//creating instance of JButton
        b.setBounds(130,100,100, 40);
        f.add(b);//adding button in JFrame*/

        frame.setSize(width,height);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void newBoard(int xCount,int yCount){
        board = new Board(xCount,yCount);
    }
    void draw(){
        if(board != null){

        }
    }
}

class MyPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // do your superclass's painting routine first, and then paint on top of it.
        g.setColor(Color.RED);
        g.fillRect(squareX,squareY,squareW,squareH);
    }
}
