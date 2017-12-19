package sgw;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */


public class GridState extends State implements Cloneable {

    private int x = 0;
    private int y = 0;
    private GridEnvironment parent;

    public GridState(int x, int y, GridEnvironment parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }
    public GridState(int[] coords, GridEnvironment parent) {
        this(coords[0], coords[1], parent);
    }

    protected void applyAction(Action action) {
        GridAction myAction = (GridAction)action;

        x+=myAction.getXMove();
        y+=myAction.getYMove();
    }

    protected void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    protected void setCoords(int[] coords) {
        setCoords(coords[0], coords[1]);
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected int[] getCoords() {
        return (new int[] { x, y } );
    }

    protected int getX() {
        return(x);
    }

    protected int getY() {
        return(y);
    }

    public boolean same(State state) {
        GridState myState = (GridState) state;
        int [] temp;

        temp = myState.getCoords();

        return ( (temp[0] == x) && (temp[1] == y) );
    }

    public String toString() {
        return(x+" "+y);
    }

    public int getIndex() {
        return(parent.coord2Index(x, y));
    }

    protected Object clone() throws CloneNotSupportedException {
        return (super.clone());
    }

    void setParent(GridEnvironment environ) {
        this.parent = environ;
    }
}