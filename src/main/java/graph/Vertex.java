package graph;

import board.Board;

import java.util.Objects;

public class Vertex {
    private Board board;

    public Vertex(Board board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return board.equals(vertex.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }
}
