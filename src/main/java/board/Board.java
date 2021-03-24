package board;

import java.util.*;

public class Board {
    private int width,height;
    private List<Integer> horizontalCount,verticalCount;


    public Board(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.width = width;
        this.height = height;
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;

    }

    public List<Integer> getHorizontalCount() {
        return horizontalCount;
    }

    public List<Integer> getVerticalCount() {
        return verticalCount;
    }

    public State readBoard(String s){

        Map<Integer,Aquarium> aqMap = new HashMap<>();

        List<List<Square>> matrix = new ArrayList<>();
        for (int i = 0; i < height ;i++){
            matrix.add(new ArrayList<>());
            for(int j = 0; j < width ; j++){
                matrix.get(i).add(null);
            }
        }

        String []lines = s.split(";");
        for (int y = 0; y < lines.length; y++) {
            String []pos = lines[y].split(" ");
            for(int x = 0; x < pos.length; x++){
                Square tempSquare = new Square(new Position(x,y),false, Integer.parseInt(pos[x]));
                Aquarium tempAquarium;
                if(!aqMap.containsKey(tempSquare.getAquariumIdentifier())){
                     tempAquarium = new Aquarium();
                    aqMap.put(tempSquare.getAquariumIdentifier(),tempAquarium);
                }
                else {
                    tempAquarium = aqMap.get(tempSquare.getAquariumIdentifier());
                }
                tempAquarium.addSquare(tempSquare);
                matrix.get(y).set(x,tempSquare);
            }
        }

        List<Aquarium> aquariums = new ArrayList<>(aqMap.values());

        for (Aquarium a:aquariums) {
            a.process();
        }

        return new State(matrix,aquariums);
    }

    /*
    public void setSol(String s){
        String []lines = s.split(";");
        for (int y = 0; y < lines.length; y++) {
            String []pos = lines[y].split(" ");
            for(int x = 0; x < pos.length; x++){
                if(pos[x].compareTo("1") == 0){
                    matrix.get(y).get(x).paint();
                }
            }
        }
    }*/

}
