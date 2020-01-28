package cellsociety;

import java.io.File;

public class Configuration {

    File myXML;
    Rules myRules;

    public Configuration(File inputfile){
        myXML = inputfile;
        myRules = parseRules(myXML);
    }

    private Rules parseRules(File myXML) {

    }

    public Rules getInitRules(){
        return myRules;
    }

    public Board getInitBoard(){
        Board myBoard = new Board(parseCellWidth(), parseCellHeight());
        for(line in myXML){
            Cell c = new Cell(parseState(), parseColor());
            myBoard.insertCell(c, parseRow(), parseCol());
        }

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
