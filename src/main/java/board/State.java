package board;

import javax.swing.undo.AbstractUndoableEdit;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class State implements Serializable {

    private List<List<Square>> matrix;
    private List<Aquarium> aquariums;
    private int depth;
    private int heuristic;
    private String s;
    private String uk = null;
    private Integer squaresLeft = null;

    public int getDepth() {
        return depth;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public State(List<List<Square>> matrix, List<Aquarium> aquariums, int depth) {
        this.matrix = matrix;
        this.aquariums = aquariums;
        this.depth = depth;
        this.heuristic= 999;//default value until it is updated;
    }

    public void setMatrix(List<List<Square>> matrix) {
        this.matrix = matrix;
    }

    public void setAquariums(List<Aquarium> aquariums) {
        this.aquariums = aquariums;
    }

    public List<List<Square>> getMatrix() {
        return matrix;
    }

    public List<Aquarium> getAquariums() {
        return aquariums;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;
        State comp = (State) obj;
        for(int y = 0;y < this.getMatrix().size(); y++){
            for(int x = 0;x < this.getMatrix().get(0).size(); x++){
                if(!matrix.get(y).get(x).getCmdOutput().equals(comp.getMatrix().get(y).get(x).getCmdOutput()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean paint(int aquarium, int level){
        try{
            aquariums.get(aquarium).getLevels().get(level).paint();
            return true;
        }
        catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    //returns a list of all levels yet unpainted (every possible play)
    public List<Level> getPossibleSteps(){
        List<Level> out = new ArrayList<>();
        for(Aquarium aquarium:aquariums){
            out.addAll(aquarium.getUnpaintedLevels());
        }
        return out;
    }


    //Builds and returns board string from the current state
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

    public String getUK(){
        if(uk ==null) {
            StringBuilder out = new StringBuilder();
            for (List<Square> squares : matrix) {
                for (int j = 0; j < matrix.get(0).size(); j++) {
                    if (squares.get(j).isPainted()) {
                        out.append("1");
                    } else {
                        out.append("0");
                    }
                }
            }
            uk = out.toString();
        }
        return uk;
    }

    public boolean[][] getState2(){
        boolean out[][] = new boolean[StateManager.height][StateManager.width];
        int y = 0;
        for (List<Square> squares : matrix) {
            for (int j = 0; j < StateManager.width; j++) {
                out[y][j] = squares.get(j).isPainted();
            }
            y++;
        }
        return out;
    }

    public boolean isFinished(List<Integer> horizontalCount,List<Integer> verticalCount){
        if(squaresLeft == null)
            squaresLeft = getSquaresLeft(horizontalCount,verticalCount);
        return squaresLeft==0;
    }

    //used for checking heuristic and game end. Returns -1 - not respecting restrictions; 0 - finished; anything else - the number of squares left
    public int getSquaresLeft(List<Integer> horizontalCount,List<Integer> verticalCount){
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

    //used for checking heuristic and game end
    private int checkHorizontalLine(Integer number, List<Square> line){
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

    //used for checking heuristic and game end
    private int checkVerticalLine(Integer number, List<List<Square>> columns, int col){
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


    //returns heuristic for the current state. If -1 - invalid, if 0 - finished, anything else - the actual heuristic
    public int updateHeuristic(List<Integer> horizontalCount, List<Integer> verticalCount){
        if(squaresLeft == null)
            squaresLeft = getSquaresLeft(horizontalCount,verticalCount);
        int out=0, nLeft = squaresLeft;
        //return nLeft;
        this.squaresLeft = nLeft;
        if(nLeft==-1 || nLeft==0){
            return nLeft;
        }
        List<Integer> aux = new ArrayList<>();

        for(Aquarium aquarium:aquariums){
            aux.add(aquarium.getNotPainted());
        }
        aux.sort(Comparator.naturalOrder());

        int counter = 0;
        int s = aux.size()-1;
        int m = 0;
        while(counter<nLeft){
            if(s-m >= 0) {
                counter += aux.get(s-m);
                out++;
                m++;
            }
        }
        return out;
    }

    //deep copies the state
    public State copy(){
        State out  = StateManager.restartBoard();
        out.setSol2(getState2(),aquariums);
        out.depth = depth;
        return out;
    }
    public void copy(State s) {
        setSol2(getState2(),s.getAquariums());
        depth = s.depth;
    }

    //only used for testing
    public void setSol(String s){
        String []lines = s.split(";");
        for (int y = 0; y < lines.length; y++) {
            for(int x = 0; x < lines[y].length(); x+=2){
                if(lines[y].getBytes()[x] == '1'){
                    matrix.get(y).get(x/2).paint();
                }
            }
        }
    }
    public void setSol2(boolean[][] sol,List<Aquarium> aquariums){
        for (int y = 0; y < StateManager.height; y++) {
            for(int x = 0; x < StateManager.width; x++){
                if(sol[y][x])
                    matrix.get(y).get(x).paint();
                else
                    matrix.get(y).get(x).unpaint();
            }
        }
        for (int a=0;a< aquariums.size();a++){
            List<Level> local_level = this.aquariums.get(a).getLevels();
            List<Level> copied_level = aquariums.get(a).getLevels();
            for(int l = 0;l<local_level.size();l++){
                if(copied_level.get(l).isPainted())
                    local_level.get(l).paint();
                else{
                    local_level.get(l).unpaint();
                }
            }
        }
    }

    //increases depth and sets heuristic
    public void updateCostAndHeuristic(List<Integer> horizontalCount, List<Integer> verticalCount){
        this.depth++;
        this.heuristic = updateHeuristic(horizontalCount,verticalCount);
    }

    public void updateDepth(List<Integer> horizontalCount, List<Integer> verticalCount) {
        this.depth++;
        if(squaresLeft == null)
            squaresLeft = getSquaresLeft(horizontalCount,verticalCount);
        this.heuristic = squaresLeft;
    }
    public void increaseDepth(){
        this.depth++;
    }

}
