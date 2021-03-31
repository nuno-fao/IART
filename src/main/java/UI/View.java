package UI;

import board.PredefinedProblem;
import board.Square;
import board.State;
import board.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for User input and UI
 */
public class View {
    /**
     * JPanel extender.
     */
    final Rects a;

    private final JFrame mainFrame;

    public View(int width, int height, StateManager stateManager, State currentState, List<PredefinedProblem> problems) {
        //layout.setHgap(10);
        //layout.setVgap(10);
        mainFrame = new JFrame("Aquarium");
        mainFrame.setSize(width + 200, height + 5);
        Dimension d = new Dimension();
        d.setSize(width + 200, height + 100);
        mainFrame.getContentPane().setPreferredSize(d);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a = new Rects(stateManager, currentState, width + 10, height + 10, Color.white,problems);
        mainFrame.add(a);
        mainFrame.setVisible(true);
    }

    /**
     * Repaints the app window.
     */
    public void reload() {
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

/**
 * Responsible for Panel drawing
 */
class Rects extends JPanel {
    /**
     * State that will be painted.
     */
    State currentState;

    /**
     * StateManager that will handle operations on the state.
     */
    StateManager stateManager;

    /**
     * Window width.
     */
    int w;

    /**
     * Window height.
     */
    int h;

    /**
     * Background color.
     */
    Color color;

    /**
     * List of problems.
     */
    final List<PredefinedProblem> problems;

    /**
     * Boolean used to know if the problem is solved or not
     */
    Boolean solved = false;


    public Rects(StateManager stateManager, State currentState, int w, int h, Color color, List<PredefinedProblem> problems) {
        super();
        this.currentState = currentState;
        this.stateManager = stateManager;
        this.w = w;
        this.h = h;
        this.color = color;
        this.problems=problems;

        setBackground(Color.darkGray);
        this.setFocusable(true);
        this.requestFocus();
        this.addMouseListener(new MouseClick());

        drawButtons();
        setLayout(null);
    }

    /**
     * Draws buttons to change between problems and reset to problem back to the beginning
     */
    private void drawButtons(){
        JButton easy = new JButton("EASY");
        easy.setBounds(w + 15, 20, 150, 50);
        easy.addActionListener(new LevelChanger(0));
        add(easy);

        JButton medium = new JButton("MEDIUM");
        medium.setBounds(w + 15, 80, 150, 50);
        medium.addActionListener(new LevelChanger(1));
        add(medium);

        JButton hard = new JButton("HARD");
        hard.setBounds(w + 15, 140, 150, 50);
        hard.addActionListener(new LevelChanger(2));
        add(hard);

        JButton reset = new JButton("RESET");
        reset.setBounds(w + 15, 200, 150, 50);
        reset.addActionListener(new Resetter());
        add(reset);

        drawHelpers();
    }

    /**
     * Draws a message on the screen for the user to know he has reached the end
     */
    private void drawWinnerMessage() {
        JLabel jLabel = new JLabel("<html><font color='orange' size='4'>Congratulations you have completed the puzzle!!!</font></html>");
        jLabel.setBounds(w/2-200 , h, 450, 25);
        if (stateManager.reachedToTheSolution() && !solved)
            add(jLabel);
        solved = true;
    }

    /**
     * Periodically verifies if the problem has been solved internally (not by the user). If so it draws the buttons to ask for hints and the solution.
     */
    private void drawHelpers(){

        Thread thread = new Thread(()->{

            while(true){
                if(stateManager.getSolution()!=null){
                    JButton hint = new JButton("HINT");
                    hint.setBounds(w + 15, 260, 150, 50);
                    hint.addActionListener(new Helper());
                    add(hint);

                    JButton solver = new JButton("SOLVE");
                    solver.setBounds(w + 15, 320, 150, 50);
                    solver.addActionListener(new Solver());
                    add(solver);
                    revalidate();
                    repaint();
                    break;
                }
                else{
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


        });

        thread.start();
    }

    @Override
    public void revalidate(){
        super.revalidate();
        solved = false;
    }


    /**
     * Main method that paints the app window.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Setup background

        Color borderColor = Color.BLACK;

        int amountH = (int) ((h - 10) / (currentState.getMatrix().size() + (currentState.getMatrix().get(0).size() - 1) * 0.1));
        int amountW = (int) ((w - 10) / (currentState.getMatrix().get(0).size() + (currentState.getMatrix().get(0).size() - 1) * 0.1));

        paintMainSquares(g, amountW, amountH);  //paint squares
        paintRightSquares(g, amountW, amountH, borderColor);    //paint vertical borders on squares
        paintDownSquares(g, amountW, amountH, borderColor);     //paint horizontal borders on squares
        paintBorders(g, amountW, amountH, borderColor);     //paint borders around the board

        paintNumbers(g, amountW, amountH);      //draw the horizontal and vertical numbers

        drawWinnerMessage();
    }

    /**
     * Draw the horizontal and vertical numbers on the board.
     * @param g Graphics from paintComponent.
     * @param w screen width
     * @param h screen height
     */
    private void paintNumbers(Graphics g, int w, int h) {
        g.setFont(new Font("default", Font.BOLD, 16));
        List<Integer> horizontal = new ArrayList<>();
        List<Integer> vertical = new ArrayList<>();

        stateManager.getLeftSquares(horizontal,vertical);

        //paint with different colors according to the numbers of squares filled
        for (int i = 0; i < stateManager.getHorizontalCount().size(); i++) {
            if(horizontal.get(i) < 0)
                g.setColor(Color.RED);
            else if(horizontal.get(i) == 0)
                g.setColor(Color.blue);
            else
                g.setColor(Color.BLACK);

            g.drawString(stateManager.getHorizontalCount().get(i).toString(), i * (w + 6) + 30, 18);
        }
        for (int i = 0; i < stateManager.getVerticalCount().size(); i++) {
            if(vertical.get(i) < 0)
                g.setColor(Color.RED);
            else if(vertical.get(i) == 0)
                g.setColor(Color.blue);
            else
                g.setColor(Color.BLACK);
            g.drawString(stateManager.getVerticalCount().get(i).toString(), 10, i * (h + 6) + 40);
        }
    }

    /**
     * Draw the main board (all squares).
     * @param g Graphics from paintComponent.
     * @param w screen width
     * @param h screen height
     */
    private void paintMainSquares(Graphics g, int w, int h) {
        int x ;
        int y = 0;
        for (List<Square> lines : currentState.getMatrix()) {
            x = 0;
            for (Square s : lines) {
                int l_x = (int) (w * x * 1.1 + 5);
                int l_y = (int) (h * y * 1.1 + 5);
                if (!s.isPainted())
                    drawRectangle(g, color, l_x - 2.5, l_y - 2.5, w + 5, h + 5);
                else {
                    if(stateManager.reachedToTheSolution())
                        drawRectangle(g, Color.orange, l_x - 2.5, l_y - 2.5, w + 5, h + 5);
                    else
                        drawRectangle(g, Color.cyan, l_x - 2.5, l_y - 2.5, w + 5, h + 5);
                }
                x++;
            }
            y++;
        }
    }

    /**
     * Draw the board borders.
     * @param g Graphics from paintComponent.
     * @param w screen width
     * @param h screen height
     * @param border color of the borders
     */
    private void paintBorders(Graphics g, int w, int h, Color border) {
        drawRectangle(g, border, 0, 0, currentState.getMatrix().get(0).size() * (w + 6), h * 0.08);
        drawRectangle(g, border, 0, currentState.getMatrix().size() * (h + 6), currentState.getMatrix().get(0).size() * (w + 6), h * 0.08);

        drawRectangle(g, border, 0, 0, w * 0.08, currentState.getMatrix().size() * (h + 6));
        drawRectangle(g, border, currentState.getMatrix().get(0).size() * (w + 6), 0, w * 0.08, currentState.getMatrix().size() * (h + 6) + 5);
    }

    /**
     * Draw the vertical border on the squares.
     * @param g Graphics from paintComponent.
     * @param w screen width
     * @param h screen height
     * @param border color of the borders
     */
    private void paintRightSquares(Graphics g, int w, int h, Color border) {
        for (int y = 0; y < currentState.getMatrix().size(); y++) {
            for (int x = 0; x < currentState.getMatrix().get(0).size() - 1; x++) {
                int l_x = (int) (w + w * x * 1.1 + 5 + w * 0.02);
                int l_y = (int) (h * y * 1.1);
                if (currentState.getMatrix().get(y).get(x).getAquariumIdentifier() != currentState.getMatrix().get(y).get(x + 1).getAquariumIdentifier()) {
                    drawRectangle(g, border, l_x, l_y, w * 0.08, h + 10);
                }
            }
        }
    }

    /**
     * Draw the horizontal border on the squares.
     * @param g Graphics from paintComponent.
     * @param w screen width
     * @param h screen height
     * @param border color of the borders
     */
    private void paintDownSquares(Graphics g, int w, int h, Color border) {
        for (int y = 0; y < currentState.getMatrix().size() - 1; y++) {
            for (int x = 0; x < currentState.getMatrix().get(0).size(); x++) {
                int l_x = (int) (w * x * 1.1);
                int l_y = (int) (h + h * y * 1.1 + 5 + h * 0.02);
                if (currentState.getMatrix().get(y).get(x).getAquariumIdentifier() != currentState.getMatrix().get(y + 1).get(x).getAquariumIdentifier()) {
                    drawRectangle(g, border, l_x, l_y, w + 10, h * 0.08);
                }
            }
        }
    }


    /**
     * Centers rectangle.
     */
    private void drawRectangle(Graphics g, Color color, double x, double y, double width, double height)
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


    /**
     * Class that will receive mouse events and handle them.
     */
    private class MouseClick extends MouseAdapter {

        /**
         * Receives mouse click event and if is inside the main board, sends the coordinates to the state manager for it to paint/unpaint levels on the state.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            if ((x <= w && y <= h) && (!stateManager.reachedToTheSolution())) {
                stateManager.actOnClick(x, y);
                revalidate();
                repaint();
            }

        }
    }

    /**
     * Listener for the reset button.
     */
    private class Resetter implements ActionListener {
        /**
         * Resets state back to beggining.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            removeAll();
            drawButtons();
            stateManager.reset();
            revalidate();
            repaint();
        }
    }

    /**
     * Listener for the level buttons.
     */
    private class LevelChanger implements ActionListener {
        final int level;

        public LevelChanger(int level) {
            this.level = level;
        }

        /**
         * Changes level to the specified on the contructor.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            w=problems.get(level).getH().size()*67 + 10;
            h=problems.get(level).getV().size()*67 + 10;

            removeAll();
            drawButtons();

            stateManager.changeLevel(problems.get(level).getBoardString(),problems.get(level).getH(),problems.get(level).getV());

            setSize(w + 200, h + 5);

            //next 3 lines should resize window but don't
            Dimension d = new Dimension();
            d.setSize(w + 200, h + 5);
            getRootPane().getContentPane().setPreferredSize(d);

            currentState=stateManager.getCurrentState();
            revalidate();
            repaint();

        }
    }

    /**
     * Listener for the hint button.
     */
    class Helper implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.giveHint();
            revalidate();
            repaint();
        }
    }

    /**
     * Listener for the solve button.
     */
    class Solver implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.giveSolution();
            revalidate();
            repaint();
        }
    }




}

