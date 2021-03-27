import board.StateManager;
import board.State;
import graph.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    /*
    @Test
    void Test1(){
        String board =
                        "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(6,6, h , v);
        toSolve.readBoard(board);
        Board solution = Solver.solve(toSolve);

        Board original = new Board(6,6, h , v);
        original.readBoard(board);
        original.setSol("1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");

        assertEquals(original,solution);
    }


    @Test
    void Test2(){
        String board =
                "1 1 2 2 5 5 7 7 7 7 7 8 8 8 8;" +
                        "3 1 2 2 4 5 5 5 5 9 10 11 12 12 13;" +
                        "3 3 3 2 4 6 6 6 6 9 10 11 11 12 13;" +
                        "2 2 2 2 2 14 14 6 6 9 10 10 11 11 13;" +
                        "15 16 16 16 16 16 14 19 19 18 18 20 13 13 13;" +
                        "15 16 16 17 17 16 19 19 19 19 19 20 21 22 22;" +
                        "15 16 17 17 17 17 19 23 26 26 25 21 21 27 27;" +
                        "28 28 28 24 24 23 23 23 23 26 26 27 27 27 27;" +
                        "29 24 24 24 23 23 23 26 26 26 26 37 27 38 38;" +
                        "29 29 30 33 34 34 34 26 26 36 36 37 27 27 38;" +
                        "31 30 30 33 33 34 35 35 36 36 37 37 27 27 38 ;" +
                        "31 30 45 45 45 34 35 44 37 37 37 37 40 38 38;" +
                        "31 30 45 30 45 34 44 44 41 41 41 41 40 38 38;" +
                        "32 30 30 30 45 45 45 44 41 42 41 40 40 38 38;" +
                        "32 45 45 45 45 45 43 43 41 42 40 40 39 39 38;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));


        Board toSolve = new Board(15,15, h , v);
        toSolve.readBoard(board);
        Board solution = Solver.solve(toSolve);
    }

    @Test
    public void test3(){
        String board =
                        "1 2;"+
                        "1 2;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1));

        Board toSolve = new Board(2,2, h , v);
        toSolve.readBoard(board);
        assertEquals(2,toSolve.getAquariums().get(1).getLevels().size());
        assertEquals(toSolve.getAquariums().get(1).getLevels().get(0),toSolve.getAquariums().get(1).getLevels().get(1).getNextLevel());
    }

    @Test
    void Test4(){
        String board =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(6,6, h , v);
        toSolve.readBoard(board);
        Board solution = Solver.solve(toSolve);

        Board original = new Board(6,6, h , v);
        original.readBoard(board);
        original.setSol(
                "0 0 0 0 0 0;" +
                        "0 0 0 0 0 0;" +
                        "0 0 0 0 0 0;" +
                        "0 0 0 0 0 0;" +
                        "1 0 0 0 0 0;" +
                        "1 0 0 0 0 0;");

        solution.getAquariums().get(1).getLevels().get(4).paint();

        //assertEquals(original,solution);

        View view = new View(400,400,solution);

        while (true){}

    }

    @Test
    void TestStateAndComplete(){
        String board =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(6,6, h , v);
        toSolve.readBoard(board);

        assertFalse(toSolve.isFinished());

        toSolve.setSol("1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");

        assertEquals(toSolve.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");

        assertTrue(toSolve.isFinished());

    }

    @Test
    void TestHAndUnpainted(){
        String board =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        Board toSolve = new Board(6,6, h , v);
        toSolve.readBoard(board);

        assertFalse(toSolve.isFinished());
        assertEquals(toSolve.getSquaresLeft(),19);
        assertEquals(toSolve.getHeuristic(),2);
        assertEquals(toSolve.getAllUnpaintedLevels().size(),19);

        toSolve.setSol("1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");


        //como ainda não pinta o level mas sim os squares falha
        //assertTrue(toSolve.isFinished());
        //assertEquals(toSolve.getSquaresLeft(),0);
        //assertEquals(toSolve.getAllUnpaintedLevels().size(),0);

    }*/

    @Test
    void TestDeepCopy() throws IOException, ClassNotFoundException {
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);
        State copy = initial.copy();


        copy.paint(1,3);
        assertTrue(copy.getAquariums().get(1).getLevels().get(3).isPainted());
        assertTrue(copy.getMatrix().get(2).get(3).isPainted());
        assertTrue(copy.getAquariums().get(1).getLevels().get(2).isPainted());
        assertFalse(initial.getAquariums().get(1).getLevels().get(2).isPainted());
        assertFalse(initial.getMatrix().get(2).get(3).isPainted());

    }

    @Test
    void TestBreathFirst(){
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);

        Graph graph = new Graph(stateManager,new BreathFirst(),h,v);
        State solution = graph.solve(initial);

        assertEquals(solution.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");
    }

    @Test
    void TestDepthFirst(){
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);

        Graph graph = new Graph(stateManager,new DepthFirst(),h,v);
        State solution = graph.solve(initial);

        assertEquals(solution.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");
    }

    @Test
    void TestGreedy(){
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);

        Graph graph = new Graph(stateManager,new Greedy(),h,v);
        State solution = graph.solve(initial);

        assertEquals(solution.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");
    }

    @Test
    void TestDjikstra(){
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);

        Graph graph = new Graph(stateManager,new Djikstra(),h,v);
        State solution = graph.solve(initial);

        assertEquals(solution.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");
    }

    @Test
    void TestAStar(){
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        StateManager stateManager = new StateManager(6,6, h , v);
        State initial = stateManager.readBoard(bs);

        Graph graph = new Graph(stateManager,new AStar(),h,v);
        State solution = graph.solve(initial);

        assertEquals(solution.getState(),"1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;");
    }

}