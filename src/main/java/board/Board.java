package board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int width,height;
    List<Integer> horizontalCount,verticalCount;
    List<List<Square>> matrix = new ArrayList<>();


    public Board(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.width = width;
        this.height = height;
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
        for (int i = 0; i < height ;i++){
            matrix.add(new ArrayList<>());
            for(int j = 0; j < width ; j++){
                matrix.get(i).add(null);
            }
        }
    }

    public List<List<Square>> getMatrix() {
        return matrix;
    }


    public void readBoard(String s){
        String []lines = s.split(";");
        for (int y = 0; y < lines.length; y++) {
            String []pos = lines[y].split(" ");
            for(int x = 0; x < pos.length; x++){
                matrix.get(y).set(x,new Square(new Position(x,y),false, Integer.parseInt(pos[x])));
            }
        }
    }
}
