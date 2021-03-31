import board.StateManager;
import board.State;
import graph.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unused")
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
    Graph graph ;
    State solution ;
    StateManager stateManager;
    String bs;
    List<Integer> h ;
    List<Integer> v ;
    String sol;
    boolean testSol;


    //@BeforeEach
    void setup1(){
        bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";

        h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));
        stateManager = new StateManager(6,6, h , v,new DepthFirst());
        sol = "1 1 1 0 0 0;" +
                "1 0 0 0 0 0;" +
                "1 1 0 0 0 0;" +
                "1 1 0 1 0 0;" +
                "0 1 1 1 1 1;" +
                "0 1 1 1 1 1;";
        testSol = true;
    }

    void setup2(){
        bs =
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


        h = new ArrayList<>(Arrays.asList(6,6,5,4,6,10,11,12,8,5,6,7,11,8,5));
        v = new ArrayList<>(Arrays.asList(11,7,10,5,7,8,10,9,7,6,8,3,5,3,11));
        stateManager = new StateManager(15,15, h , v,new DepthFirst());

        testSol = false;
    }

    //@BeforeEach
    void setup3(){
        bs =
                "0 1 1 2 2 2;"+
                        "0 3 4 5 6 6;"+
                        "7 8 4 5 9 9;"+
                        "10 8 4 5 11 11;"+
                        "12 12 4 13 13 14;"+
                        "15 15 16 16 13 17;";

        h = new ArrayList<>(Arrays.asList(5, 5, 2, 4, 5, 3));
        v = new ArrayList<>(Arrays.asList(4, 2, 3, 5, 5, 5));
        stateManager = new StateManager(6,6, h , v,new DepthFirst());
        testSol = false;
    }

    void setup4(){
        bs =
                "0 1 1 2 3 3;"+
                        "0 4 4 5 6 7;"+
                        "8 9 10 5 6 7;"+
                        "8 11 10 6 6 6;"+
                        "12 13 13 14 15 16;"+
                        "12 17 17 14 15 16;";

        h = new ArrayList<>(Arrays.asList(2, 5, 5, 5, 2, 3));
        v = new ArrayList<>(Arrays.asList(4, 4, 2, 5, 2, 5));
        stateManager = new StateManager(6,6, h , v,new DepthFirst());
        testSol = false;
    }

    //@BeforeEach
    void setup5(){
        bs =
                "0 1 1 1 1 2;"+
                        "0 3 3 3 1 2;"+
                        "4 3 1 1 1 5;"+
                        "4 5 5 5 5 5;"+
                        "4 4 4 6 6 5;"+
                        "7 7 8 8 8 8;";

        h = new ArrayList<>(Arrays.asList(2, 3, 5, 4, 5, 3));
        v = new ArrayList<>(Arrays.asList(4, 2, 3, 5, 4, 4));
        stateManager = new StateManager(6,6, h , v,new DepthFirst());
        testSol = false;
    }

    @BeforeEach
    void setup6(){
        bs =
                "0 0 1 2 2 2 2 2 2 2;"+
                        "0 1 1 2 2 2 2 3 3 4;"+
                        "0 1 1 5 5 6 7 7 3 4;"+
                        "0 0 5 5 5 6 7 8 8 4;"+
                        "9 0 9 9 5 7 7 7 4 4;"+
                        "9 9 9 10 5 11 7 11 4 4;"+
                        "10 10 9 10 5 11 7 11 4 4;"+
                        "10 10 10 10 11 11 11 11 4 12;"+
                        "10 13 13 11 11 12 12 11 4 12;"+
                        "10 13 11 11 11 12 12 12 12 12;";

        h = new ArrayList<>(Arrays.asList(6,6,7,4,4,4,4,4,7,2));
        v = new ArrayList<>(Arrays.asList(8,8,4,5,1,3,3,5,2,9));
        stateManager = new StateManager(10,10, h , v,new DepthFirst());
        testSol = false;
    }

    //@Test
    void TestDeepCopy() {
        String bs =
                "0 0 0 1 1 1;" +
                        "2 1 1 1 1 3;" +
                        "2 2 1 1 1 3;" +
                        "2 2 4 1 3 3;" +
                        "5 2 4 1 3 3;" +
                        "5 4 4 4 4 4;";
        List<Integer> h = new ArrayList<>(Arrays.asList(4, 5, 3, 3, 2, 2));
        List<Integer> v = new ArrayList<>(Arrays.asList(3, 1, 2, 3, 5, 5));

        StateManager stateManager = new StateManager(6,6, h , v,new DepthFirst());
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
        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(new BreathFirst(),h,v);
        solution = graph.solve(initial);
        if(testSol)
            assertEquals(solution.getState(),sol);
        System.out.println("Breath explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
    }

    @Test
    void TestDepthFirst(){
        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(new DepthFirst(),h,v);
        solution = graph.solve(initial);
        if(testSol)
            assertEquals(solution.getState(),sol);
        System.out.println("Depth explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
    }

    @Test
    void TestGreedy(){
        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(new Greedy(),h,v);
        solution = graph.solve(initial);
        if(testSol)
            assertEquals(solution.getState(),sol);
        System.out.println("Greedy explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
    }

    @Test
    void TestDjikstra(){
        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(new UniformCost(),h,v);
        solution = graph.solve(initial);
        if(testSol)
            assertEquals(solution.getState(),sol);
        System.out.println("Djikstra explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
    }

    @Test
    void TestAStar(){
        long startTime = System.currentTimeMillis();
        State initial = stateManager.readBoard(bs);
        graph = new Graph(new AStar(),h,v);
        solution = graph.solve(initial);
        if(testSol)
            assertEquals(solution.getState(),sol);
        System.out.println("AStar explored "+graph.getExploredStates()+" states and solution has depth of "+solution.getDepth()+": "+(System.currentTimeMillis()-startTime));
        for(String s : solution.getState().split(";")){
            System.out.println(s);
        }
    }

}