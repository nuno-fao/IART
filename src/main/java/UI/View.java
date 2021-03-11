package UI;

import board.Board;
import board.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class View {

    private JFrame mainFrame;

    public View(int width, int height, Board board){
        //layout.setHgap(10);
        //layout.setVgap(10);


        mainFrame = new JFrame("Aquarium");
        mainFrame.setSize(width+10,height+10);
        Dimension d = new Dimension();
        d.setSize(width + 10,height + 10);
        mainFrame.getContentPane().setPreferredSize(d);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(new Rects(mainFrame,board,width + 10,height + 10,Color.white));
        mainFrame.setVisible(true);
    }
}

class Rects extends JPanel
{
    Board board;
    Frame mainFrame;
    int w,h;
    Color color;

    public Rects(JFrame frame,Board board,int w,int h,Color color) {
        super();
        mainFrame = frame;
        this.board = board;
        this.w = w;
        this.h = h;
        this.color = color;
        setBackground(Color.darkGray);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //Setup background


        int amountH = (int) ((h-10) / (board.getMatrix().size()+(board.getMatrix().get(0).size()-1)*0.1));
        int amountW = (int) ((w-10) / (board.getMatrix().get(0).size()+(board.getMatrix().get(0).size()-1)*0.1));
        painMainSquares(g,amountW,amountH);
        painRightSquares(g,(int) (amountW),(int) (amountH));
        painDownSquares(g,(int) (amountW),(int) (amountH));

    }

    private  void painMainSquares(Graphics g,int w,int h){
        int x = 0;
        int y = 0;
        for (List<Square> lines: board.getMatrix()) {
            x=0;
            for (Square s: lines) {
                int l_x = (int) (w*x*1.1+5);
                int l_y = (int) (h*y*1.1+5);
                if(!s.isPainted())
                    drawRectangle(g, color,l_x, l_y, w, h);
                else
                    drawRectangle(g, Color.cyan,l_x, l_y, w, h);
                x++;
            }
            y++;
        }
    }
    private  void painRightSquares(Graphics g,int w,int h){
        for (int y = 0; y < board.getMatrix().size(); y++) {
            for (int x = 0; x < board.getMatrix().get(0).size() -1 ; x++) {
                int l_x = (int) (w+w*x*1.1+5+w * 0.01);
                int l_y = (int) (h*y*1.1+5);
                if(board.getMatrix().get(y).get(x).getAquariumIdentifier() != board.getMatrix().get(y).get(x+1).getAquariumIdentifier()) {
                    drawRectangle(g, Color.black, l_x, l_y, w * 0.08, h);
                }
                else {
                    drawRectangle(g, color, l_x, l_y, w * 0.08, h);
                }
            }
        }
    }
    private  void painDownSquares(Graphics g,int w,int h){
        for (int y = 0; y < board.getMatrix().size() - 1; y++) {
            for (int x = 0; x < board.getMatrix().get(0).size() ; x++) {
                int l_x = (int) (w*x*1.1+5);
                int l_y = (int) (h + h*y*1.1+5+ h*0.01);
                if(board.getMatrix().get(y).get(x).getAquariumIdentifier() != board.getMatrix().get(y+1).get(x).getAquariumIdentifier()) {
                    drawRectangle(g, Color.black,l_x, l_y, w*1, h*0.08);
                }
                else {
                    drawRectangle(g, color,l_x, l_y, w*1, h*0.08);
                }
            }
        }
    }

    private void drawRectangle(Graphics g, Color color, double x, double y, double width, double height) //centers rectangle
    {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
        RenderingHints render = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        render.put(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.addRenderingHints(render);
        g2.setColor(color);
        g2.fill(rect);
    }
}