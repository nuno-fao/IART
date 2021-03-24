package board;

import java.util.*;

public class Board {
    private int width,height;
    private List<Integer> horizontalCount,verticalCount;
    private List<List<Square>> matrix;
    private List<Aquarium> aquariums;



    public Board(int width, int height, List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.width = width;
        this.height = height;
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
        matrix = new ArrayList<>();
        aquariums = new ArrayList<>();
        for (int i = 0; i < height ;i++){
            matrix.add(new ArrayList<>());
            for(int j = 0; j < width ; j++){
                matrix.get(i).add(null);
            }
        }


    }

    public List<Integer> getHorizontalCount() {
        return horizontalCount;
    }

    public List<Integer> getVerticalCount() {
        return verticalCount;
    }

    public List<List<Square>> getMatrix() {
        return matrix;
    }

    public void readBoard(String s){

        Map<Integer,Aquarium> aqMap = new HashMap<>();

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

        aquariums = new ArrayList<>(aqMap.values());

        for (Aquarium a:aquariums) {
            a.process();
        }
    }
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
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        Board comp = (Board) obj;
        for(int y = 0;y < this.getMatrix().size(); y++){
            for(int x = 0;x < this.getMatrix().get(0).size(); x++){
                if(matrix.get(y).get(x).getCmdOutput() != comp.getMatrix().get(y).get(x).getCmdOutput())
                {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Aquarium> getAquariums() {
        return aquariums;
    }

    public List<Level> getPossibleSteps(){
        List<Level> out = new ArrayList<>();
        for(Aquarium aquarium:aquariums){
            out.addAll(aquarium.getUnpaintedLevels());
        }
        return out;
    }

    public String getState(){
        StringBuilder out = new StringBuilder();
        for (List<Square> squares : matrix) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (squares.get(j).isPainted()) {
                    out.append("1");
                } else {
                    out.append("0");
                }
                if (j == matrix.get(0).size() - 1) {
                    out.append(";");
                } else {
                    out.append(" ");
                }
            }
        }
        return out.toString();
    }

    public boolean isFinished(){
        return getSquaresLeft()==0;
    }

    //-1 - not respecting restrictions; 0 - finished; anything else - the number of squares left
    public int getSquaresLeft(){

        int out=0, aux;
        for(int i = 0;i<verticalCount.size();i++){
            aux = checkHorizontalLine(verticalCount.get(i),matrix.get(i));
            if(aux==-1){
                return -1;
            }
            else{
                out+=aux;
            }

        }

        for(int i = 0;i<horizontalCount.size();i++){
            aux = checkVerticalLine(horizontalCount.get(i),matrix,i);
            if(aux==-1){
                return -1;
            }
        }

        return out;

    }

    public int checkHorizontalLine(Integer number, List<Square> line){
        Integer i = 0;
        for(Square square:line){
            if(square.isPainted()) i++;
        }
        if(i>number){
            return -1;
        }
        else if(i.equals(number)){
            return 0;
        }
        else{
            return number-i;
        }
    }

    public int checkVerticalLine(Integer number, List<List<Square>> columns, int col){
        Integer i = 0;
        for(List<Square> list:columns){
            if(list.get(col).isPainted()) i++;
        }

        if(i>number){
            return -1;
        }
        else if(i.equals(number)){
            return 0;
        }
        else{
            return number-i;
        }
    }

    public List<Level> getAllUnpaintedLevels(){
        List<Level> out = new ArrayList<>();
        for(Aquarium aquarium:aquariums){
            out.addAll(aquarium.getUnpaintedLevels());
        }
        return out;
    }

    public int getHeuristic(){
        int out=0, nLeft = getSquaresLeft();

        if(nLeft==-1 || nLeft==0){
            return nLeft;
        }
        PriorityQueue<Integer> aux = new PriorityQueue<>(aquariums.size(),Comparator.reverseOrder());

        for(Aquarium aquarium:aquariums){
            aux.add(aquarium.getNotPainted());
        }

        int counter = 0;
        while(counter<nLeft){
            if(aux.peek()!=null) {
                counter += aux.poll();
                out++;
            }
        }

        return out;
    }

}
