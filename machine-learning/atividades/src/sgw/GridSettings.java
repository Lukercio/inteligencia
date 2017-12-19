package sgw;
import mdp.*;
/**
 * Created by ldlopes on 12/19/17.
 */


public class GridSettings extends Settings implements Cloneable {
    final public static GridAction emptyAction = new GridAction(0, 0);
    final public static MoveModifier emptyMod = new MoveModifier() {
        // the default would have the same effect, but this eliminates one function call 8-)
        public GridAction getMod(GridState state) {
            return(emptyAction);
        }
    };

    final static int defaultGridXSize = 10;
    final static int defaultGridYSize = 10;
    final static double defaultSuccessRate = .5;
    final static double defaultReward = 0;
    final static double defaultRewardOnGoal = 1;
    final static MoveModifier defaultMod = emptyMod;
    final static GridAction[] defaultActions = new GridAction[] { new GridAction(1, 0, 0, "RIGHT"), new GridAction(0, 1, 1, "DOWN"), new GridAction(-1, 0, 2, "LEFT"), new GridAction(0,-1, 3, "UP") };

    int gridXSize;
    int gridYSize;
    double successRate;
    double rewardOnGoal;
    GridState startState;
    GridState goalState;
    MoveModifier mod;
    GridAction[] actions;

    double grid[][];
    double r[][][];


    protected void checkSuccessRate() {
        if ((successRate<0) || (successRate>1))
            throw (new IllegalArgumentException("Corrupt successRate passed to Settings"));
    }

    protected void checkR() {
        if ((r.length != gridXSize) || (r[0].length != gridYSize))
            throw (new IllegalArgumentException("Reward array passed does not match X-Y size"));
    }

    protected void checkStartAndGoal() {
        int [] startStateCoord = startState.getCoords();
        int [] goalStateCoord  = goalState.getCoords();
        if ( (startStateCoord[0]<0) || (startStateCoord[0]>=gridXSize) ||
                (startStateCoord[1]<0) || (startStateCoord[1]>=gridYSize) ||
                (goalStateCoord[0]<0)  || (goalStateCoord[0]>=gridXSize)  ||
                (goalStateCoord[1]<0)  || (goalStateCoord[1]>=gridYSize))
            throw (new IllegalArgumentException("Bad start or goal state passed"));
    }

    protected void checkActions() {
        for (int i=0; i<actions.length; i++) {
            if (actions[i].getIndex() != i) {
                throw (new IllegalArgumentException("Corrupt Action indices passed to Settings"));
            }
        }
    }

    protected void checkGrid() {
        if ((grid.length != gridXSize) || (grid[0].length != gridYSize))
            throw (new IllegalArgumentException("Grid array passed does not match X-Y size"));
    }

    protected void checkAll() {
        checkSuccessRate();
        checkR();
        checkStartAndGoal();
        checkActions();
        checkGrid();
    }

    protected void checkSize() {
        if ((gridXSize*gridYSize)<2)
            throw( new IllegalArgumentException("grid too small, must have at least two different states"));
    }

    protected void createDefaultR() {
        this.r = new double[gridXSize][gridYSize][actions.length];
        for (int i = 0; i<gridXSize; i++)
            for (int j = 0; j<gridYSize; j++)
                for (int k = 0; k<actions.length; k++)
                    this.r[i][j][k] = defaultReward;
    }
    protected void createDefaultR(double defaultReward) {
        this.r = new double[gridXSize][gridYSize][actions.length];
        for (int i = 0; i<gridXSize; i++)
            for (int j = 0; j<gridYSize; j++)
                for (int k = 0; k<actions.length; k++)
                    this.r[i][j][k] = defaultReward;
    }

    protected void createDefaultGrid() {
        this.grid = new double[gridXSize][gridYSize];
        for (int i = 0; i<gridXSize; i++)
            for (int j =0; j<gridYSize; j++)
                this.grid[i][j] = 1;
    }

    protected void createDefaultStartState() {
        this.startState = new GridState(0, 0, null);
    }

    protected void createDefaultGoalState() {
        this.goalState = new GridState(gridXSize-1, gridYSize-1, null);
    }

    public GridSettings( int gridXSize,
                         int gridYSize,
                         double successRate,
                         double [][][] r,
                         double rewardOnGoal,
                         GridState startState,
                         GridState goalState,
                         MoveModifier mod,
                         GridAction[] actions,
                         double[][] grid) {

        this.gridXSize = gridXSize;
        this.gridYSize = gridYSize;

        checkSize();

        this.successRate = successRate;

        if (null == actions)
            this.actions = defaultActions;
        else
            this.actions = actions;

        if (null == r)
            createDefaultR();
        else if ( r.length == 1)
            createDefaultR(r[0][0][0]);
        else
            this.r = r;

        this.rewardOnGoal = rewardOnGoal;

        if (null == startState)
            createDefaultStartState();
        else
            this.startState = startState;

        if (null == goalState)
            createDefaultGoalState();
        else
            this.goalState = goalState;

        if (null == mod)
            this.mod = defaultMod;
        else
            this.mod = mod;

        if (null == grid)
            createDefaultGrid();
        else
            this.grid = grid;

        checkAll();
    }
    public GridSettings( int gridXSize,
                         int gridYSize) {
        this(gridXSize, gridYSize, defaultSuccessRate, null, defaultRewardOnGoal, null, null, null, null, null);
    }
    public GridSettings() {
        this(defaultGridXSize, defaultGridYSize, defaultSuccessRate, null, defaultRewardOnGoal, null, null, null, null, null);
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public void setR(double [][][] r) {
        this.r = r;
        checkR();
    }

    public void setStartState(State startState) {
        this.startState = (GridState)startState;
        checkStartAndGoal();
    }

    public void setGoalState(State goalState) {
        this.goalState = (GridState)goalState;
        checkStartAndGoal();
    }

    public void setMod(MoveModifier mod) {
        this.mod = mod;
    }

    public void setActions(GridAction[] actions) {
        this.actions = actions;
        checkActions();
    }

    public void setGrid(double[][] grid) {
        this.grid = grid;
        checkGrid();
    }

    public int getGridXSize() {
        return(gridXSize);
    }

    public int getGridYSize() {
        return(gridYSize);
    }

    public static GridAction[] genActions(int[][] moves, String[] names) {
        GridAction[] temp;
        if (null != names) {
            if (names.length != moves.length)
                throw( new IllegalArgumentException("Number of moves don't match with number of names"));

            temp = new GridAction[moves.length];
            for (int i = 0; i<moves.length; i++)
                temp[i] = new GridAction(moves[i], i, names[i]);

        } else {

            temp = new GridAction[moves.length];
            for (int i = 0; i<moves.length; i++)
                temp[i] = new GridAction(moves[i], i);
        }

        return(temp);
    }
    static GridAction[] genActions(int[][] moves) {
        return(genActions(moves, null));
    }

    public Object clone() throws CloneNotSupportedException {
        return(super.clone());
    }
}