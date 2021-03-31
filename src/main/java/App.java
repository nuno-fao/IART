import UI.View;
import board.PredefinedProblem;
import board.State;
import board.StateManager;
import graph.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Integer.valueOf;

/**
 * Game entry point
 */
public class App {
    private final List<PredefinedProblem> problems;
    private StateManager stateManager;
    private View view;

    App() {
        problems = new ArrayList<>();
        readProblems(problems);
    }

    /**
     * Main function to run the application.
     */
    public static void main(String[] args) {
        App a = new App();
        String bs;

        int startingProblem=0; //chooses the problem that will be shown when the game opens, value between 0 and 2


        bs = a.problems.get(startingProblem).getBoardString();
        List<Integer> h =  a.problems.get(startingProblem).getH();
        List<Integer> v =  a.problems.get(startingProblem).getV();


        if(args.length != 0){   //choses algorythm that graph uses depending on the args
            switch (args[0]) {
                case "astar" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, new AStar());
                }
                case "breathfirst" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, new BreathFirst());
                }
                case "depthfirst" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, new DepthFirst());
                }
                case "greedy" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, new Greedy());
                }
                case "uniform" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, new UniformCost());
                }
                case "iterative" -> {
                    a.stateManager = new StateManager(h.size(), v.size(), h, v, null);
                }
                default -> a.stateManager = new StateManager(h.size(), v.size(), h, v, new Greedy());
            }
        }else{
            a.stateManager = new StateManager(h.size(),v.size(), h , v,new Greedy());
        }

        State initial = a.stateManager.readBoard(bs);   //creates the initial state from the board string

        a.view = new View(67 * h.size(), 67 * v.size(), a.stateManager, initial, a.problems);   //starts the view
    }


    /**
     * Reads problems from XML file "problems". Text content of tags must have specific formar.
     *              boardString - numbers on lines separated by " "; each line terminating on ";"; each line separated by "\n".
     *              horizontal & vertical - numbers separated by ",".
     * @param problems List of problems that will contain the problems read.
     */
    public static void readProblems(List<PredefinedProblem> problems){
        try{
            File inputFile = new File("src/main/java/problems");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("problem");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                String[] aux;
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    //boardString
                    aux = eElement.getElementsByTagName("boardString").item(0).getTextContent().trim().split("\n");
                    StringBuilder auxString = new StringBuilder();
                    for(String s : aux){
                        auxString.append(s.trim());
                    }
                    String boardString = auxString.toString();


                    //horizontal
                    aux = eElement.getElementsByTagName("horizontal").item(0).getTextContent().trim().split(",");
                    List<Integer> horizontal = new ArrayList<>();
                    for (String s : aux) {
                        horizontal.add(valueOf(s));
                    }

                    //vertical
                    aux = eElement.getElementsByTagName("vertical").item(0).getTextContent().trim().split(",");
                    List<Integer> vertical = new ArrayList<>();
                    for (String s : aux) {
                        vertical.add(valueOf(s));
                    }

                    PredefinedProblem predefinedProblem = new PredefinedProblem(horizontal,vertical,boardString);
                    problems.add(predefinedProblem);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}