package graph;

import board.State;
import board.StateManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;
    private Vertex root; // graph root
    private StateManager stateManager;

    public Graph(StateManager stateManager,State initialState) {
        this.root = new Vertex(initialState);
        this.stateManager = stateManager;
        this.adjVertices = new HashMap<>();
        this.adjVertices.put(this.root,new ArrayList<>());
    }

    public Vertex getRoot() {
        return this.root;
    }

    /**
     * from a given vertex generates its childs and adds them to the graph
     * @param currentVertex vertex being analyzed
     */
    public void generateChilds(Vertex currentVertex){
        List<State> childs = new StateManager().getLeaves(currentVertex.getState());
        for (State child : childs) {
            addVertex(currentVertex,child);
        }
    }

    private void addVertex(Vertex currentVertex,State childState){
        if (!adjVertices.containsKey(childState)){
            adjVertices.put(new Vertex(childState),new ArrayList<>());
        }
        addEdge(currentVertex,childState);
    }

    private void addEdge(Vertex vertex1,State childState){
        Vertex vertex2 = new Vertex(childState);
        adjVertices.get(vertex1).add(vertex2);
    }

    /**
     * cleans all the graph and adds the root node
     */
    public void resetGraph(){
        this.adjVertices.clear();
        this.adjVertices.put(this.root,new ArrayList<>());
    }

    /**
     * from a given vertex verifies whether it has reached the solution
     * @param currentVertex vertex being analyzed
     * @return true if reached the solution, false otherwise
     */
    public boolean reachedToTheSolution(Vertex currentVertex){
        return currentVertex.getState().isFinished(this.stateManager.getHorizontalCount(),this.stateManager.getVerticalCount());
    }

    /**
     * from a given vertex it returns all of its children
     * @param currentVertex vertex being analyzed
     * @return list with all currentVertex childs
     */
    public List<Vertex> getAdjVertices(Vertex currentVertex) {
        return adjVertices.get(currentVertex);
    }
}
