package cellsociety;

import javafx.scene.paint.Color;

import java.io.File;

public class Configuration {

    File myXML;
    Rules myRules;

    public Configuration(String inputfileName){
        myXML = new File(inputfileName);
        myRules = parseRules(myXML);
    }

    private Rules parseRules(File myXML) {

    }

    public Rules getInitRules(){
        return myRules;
    }

    public Board getInitBoard(){
        Board myBoard = new Board(parseCellWidth(), parseCellHeight());
        /*for(line in myXML){
            Cell c = new Cell(parseState(), parseColor());
            myBoard.insertCell(c, parseRow(), parseCol());
        }*/

    }

    private int parseCol() {

    }

    private int parseRow() {

    }

    private Color parseColor() {

    }

    private int parseState() {

    }

    private int parseCellHeight() {

    }

    private int parseCellWidth() {

    }


}
