package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aquarium implements Serializable {
    private List<Level> levels;
    private List<Square> squares;

    Aquarium(){
        levels = new ArrayList<>();
        squares = new ArrayList<>();
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

    public List<Level> getUnpaintedLevels(){
        List<Level> out = new ArrayList<>();
        for(Level level:levels){
            if(!level.isPainted()){
                out.add(level);
            }
        }
        return out;
    }

    public int getNotPainted(){
        int aux = 0;
        for(Level level : levels){
            if(!level.isPainted()){
                aux+=level.getNSquares();
            }
        }
        return aux;
    }

    public void squareIsClicked(Square square){
        for(int i =0;i<levels.size();i++){
            Level level = levels.get(i);
            if(level.getSquares().get(0).getPos().getY() == square.getPos().getY()){
                if(level.isPainted()){
                    unpaintDownTo(i);
                }
                else if(!level.isPainted()){
                    level.paint();
                }
            }
        }

    }

    public void unpaintDownTo(int level){
        for(int i =level;i<levels.size();i++){
            levels.get(i).unpaint();
        }
    }

}
