package graph;


import board.State;

import java.util.Objects;

public class Vertex {
    private State state;
    private Boolean visited;

    public Vertex(State state) {
        this.visited = false;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Boolean getVisited() {
        return visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return state.equals(vertex.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
