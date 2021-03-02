package board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int aquariumCount = 0;
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

    public void addAquarium(List<Position> positions){
        for (Position pos:positions) {
            matrix.get(pos.getY()).set(pos.getX(),new Square(pos,false,aquariumCount));
        }
        aquariumCount++;
    }
}
