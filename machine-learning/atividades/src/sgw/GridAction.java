package sgw;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */

public class GridAction implements Action {

    int xMove = 0;
    int yMove = 0;
    int index = -1;
    String name = null;

    protected GridAction(int xMove, int yMove, int index, String name) {
        this.xMove = xMove;
        this.yMove = yMove;
        this.index = index;
        this.name = name;
    }
    protected GridAction(int xMove, int yMove, int index) {
        this(xMove, yMove, index, null);
    }
    protected GridAction(int xMove, int yMove) {
        this(xMove, yMove, -1, null);
    }
    protected GridAction(int[] move, int index, String name) {
        this(move[0], move[1], index, name);
    }
    protected GridAction(int[] move, int index) {
        this(move[0], move[1], index, null);
    }
    protected GridAction(int[] move) {
        this(move[0], move[1], -1, null);
    }
    protected GridAction() {
    }

    protected int getXMove() {
        return(xMove);
    }

    protected int getYMove() {
        return(yMove);
    }

    protected int[] getMove() {
        return ( new int [] { xMove, yMove } );
    }

    public int getIndex() {
        return(index);
    }

    public String toString() {
        if (null == name)
            return("A: "+xMove+" "+yMove);
        else
            return(name);
    }
}