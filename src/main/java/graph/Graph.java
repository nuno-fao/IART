package graph;

import board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Vertex, List<Vertex>> adjVertices;

    public Graph() { }

    void addVertex(Board board){
        adjVertices.putIfAbsent(new Vertex(board),new ArrayList<>());
    }

    void removeVertex(Board board){
        Vertex vertex = new Vertex(board);
        adjVertices.values().stream().forEach(vertices -> vertices.remove(vertex));
        adjVertices.remove(vertex);
    }

    void addEdge(Board board1,Board board2){
        Vertex vertex1 = new Vertex(board1);
        Vertex vertex2 = new Vertex(board2);
        adjVertices.get(vertex1).add(vertex2);
    }

    void removeEdge(Board board1,Board board2){
        Vertex vertex1 = new Vertex(board1);
        Vertex vertex2 = new Vertex(board2);

        List<Vertex> adjToVertex1 = adjVertices.get(vertex1);
        if( adjToVertex1 !=null)
            adjToVertex1.remove(vertex2);
    }

    List<Vertex> getAdjVertices(Board board) {
        return adjVertices.get(new Vertex(board));
    }
}
