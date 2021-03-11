package board;

import java.util.ArrayList;
import java.util.List;

public class Aquarium {
    private List<Level> levels = new ArrayList<>();
    private List<Square> squares = new ArrayList<>();

    Aquarium(){

    }

    public void addSquare(Square s){
        squares.add(s);
    }

    public void process(){
        levels.add(new Level(null));
        int last_y = squares.get(squares.size()-1).getPos().getY();
        for ( int  i = squares.size()-1; i!= -1; i--){
            Square tempSquare = squares.get(i);
            if(tempSquare.getPos().getY() != last_y){
                levels.add(new Level(levels.get(levels.size()-1)));
                last_y = tempSquare.getPos().getY();
            }
            levels.get(levels.size()-1).addSquare(tempSquare);
        }
    }

    public List<Level> getLevels() {
        return levels;
    }
}
